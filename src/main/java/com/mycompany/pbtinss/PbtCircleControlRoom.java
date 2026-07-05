/*
 * PbtCircleControlRoom.java
 * Circle Head control-room monitoring dashboard for Parabit Systems INSS.
 * Drop into com.mycompany.pbtinss alongside PbtCircleDirector, PBTTollDirector, etc.
 *
 * DB WIRING NOTES (read this before running):
 * ---------------------------------------------------------------------------
 * LIVE FROM parabitinss DB:
 *   - Zones          <- `zone` table (ZNo, Status). No name/density column exists
 *                        in your schema, so zones are labelled "Zone N" and density
 *                        is still simulated (see SIMULATED note below).
 *   - Checkpoints    <- `checkpoint` table (CpNo, CpName, LocationName, Zone,
 *                        NoOfGates, LocationInCharge1, EmpId1FK, Status)
 *   - Staff          <- `empreg` joined to `empdesignation` (EmpDesignationFK -> Sno)
 *                        filtered on Status = 1
 *
 * STILL SIMULATED (no matching table found in what you've shared so far):
 *   - Zone crowd density        needs a live people-count/sensor table
 *   - Entry/Exit flow rate      needs a timestamped log table (e.g. personlog)
 *   - Vehicle mix                needs vehicle registration/count tables
 *   - Persons/Vehicles in Circle totals
 *   - Activity log / alerts     needs an events/incidents table
 *
 * FALLBACK BEHAVIOUR: your `zone` and `circle` tables are currently empty
 * (per your screenshots). If a DB query returns 0 rows, this file falls back
 * to a small demo seed for that section (with a console warning) so the UI
 * isn't blank while you're still populating data. Once real rows exist, it
 * will automatically use them on the next refresh.
 *
 * Checkpoints/Staff are re-pulled from the DB every 12s (DB_REFRESH_MS) so
 * status/roster changes made in phpMyAdmin show up without restarting the app.
 * Zones are loaded once at startup (the gauge layout is fixed at construction).
 *
 * @author Arnav Singh
 */
package com.mycompany.pbtinss;

import com.mycompany.pbtinss.database.ParabitDBC;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PbtCircleControlRoom extends JFrame {

    // ---------------- PALETTE (Amber Operator) ----------------
    static final Color BG          = new Color(0x0E, 0x08, 0x04);
    static final Color PANEL       = new Color(0x16, 0x10, 0x0A);
    static final Color PANEL_2     = new Color(0x1C, 0x13, 0x0B);
    static final Color BORDER_C    = new Color(0x3A, 0x24, 0x10);
    static final Color AMBER       = new Color(0xEF, 0x9F, 0x27);
    static final Color AMBER_DIM   = new Color(0xBA, 0x75, 0x17);
    static final Color AMBER_DEEP  = new Color(0x85, 0x4F, 0x0B);
    static final Color TEXT        = new Color(0xF2, 0xE6, 0xD8);
    static final Color TEXT_MUTED  = new Color(0x9C, 0x7B, 0x52);
    static final Color GREEN       = new Color(0x5B, 0x8C, 0x3E);
    static final Color YELLOW      = new Color(0xD4, 0xA0, 0x17);
    static final Color RED         = new Color(0xC0, 0x39, 0x2B);

    static final Font FONT_TITLE   = new Font("Consolas", Font.BOLD, 20);
    static final Font FONT_HEAD    = new Font("Consolas", Font.BOLD, 12);
    static final Font FONT_MONO    = new Font("Consolas", Font.PLAIN, 12);
    static final Font FONT_MONO_SM = new Font("Consolas", Font.PLAIN, 10);
    static final Font FONT_MONO_B  = new Font("Consolas", Font.BOLD, 12);
    static final Font FONT_STAT    = new Font("Consolas", Font.BOLD, 22);

    private static final int DB_REFRESH_MS = 12000;

    // ---------------- DATA MODELS ----------------
    static class Zone {
        String id, name; double density;
        Zone(String id, String name, double density) { this.id = id; this.name = name; this.density = density; }
    }

    static class Checkpoint {
        String id, name, zoneId, incharge, empId;
        int gates, gatesOpen;
        double inRate, outRate;
        Checkpoint(String id, String name, String zoneId, int gates, int gatesOpen,
                   String incharge, String empId, double inRate, double outRate) {
            this.id = id; this.name = name; this.zoneId = zoneId; this.gates = gates;
            this.gatesOpen = gatesOpen; this.incharge = incharge; this.empId = empId;
            this.inRate = inRate; this.outRate = outRate;
        }
    }

    static class VehicleType {
        String type; int count, max;
        VehicleType(String type, int count, int max) { this.type = type; this.count = count; this.max = max; }
    }

    static class StaffMember {
        String name, role, cp;
        StaffMember(String name, String role, String cp) { this.name = name; this.role = role; this.cp = cp; }
    }

    static class AlertItem {
        String time, severity, message;
        AlertItem(String time, String severity, String message) { this.time = time; this.severity = severity; this.message = message; }
    }

    // ---------------- STATE ----------------
    private final List<Zone> zones = new ArrayList<>();
    private final List<Checkpoint> checkpoints = new ArrayList<>();
    private final List<VehicleType> vehicles = new ArrayList<>();
    private final List<StaffMember> staff = new ArrayList<>();
    private final LinkedList<AlertItem> alerts = new LinkedList<>();
    private final AlertItem[] alertPool;

    // maps zoneId -> baseline density, so re-running loadZones() doesn't reset
    // the simulated density every DB refresh
    private final Map<String, Double> zoneDensityMemory = new HashMap<>();
    // maps checkpoint id -> in/out rate memory, same reasoning
    private final Map<String, double[]> cpFlowMemory = new HashMap<>();
    // designation lookup: Sno -> Designation, loaded once from empdesignation
    private final Map<Integer, String> designationMap = new HashMap<>();

    private long personsInCircle = 84320;
    private long vehiclesInCircle = 6228;

    private final List<Double> trendIn = new ArrayList<>();
    private final List<Double> trendOut = new ArrayList<>();

    private final Random rng = new Random();

    // ---------------- DB ----------------
    private ParabitDBC dbc;

    // ---------------- UI REFS ----------------
    private JLabel clockLabel, dateLabel, statusPillLabel;
    private JPanel statusPillPanel;
    private JLabel[] statValueLabels;
    private JLabel activeAlertsValueLabel;
    private GaugePanel[] gaugePanels;
    private TrendChartPanel trendPanel;
    private DefaultTableModel cpTableModel;
    private JLabel cpMetaLabel;
    private JPanel vehicleBarsPanel;
    private JLabel vehTotalLabel;
    private JPanel staffListPanel;
    private JLabel staffMetaLabel;
    private JPanel alertsListPanel;
    private JLabel footerLabel;

    public PbtCircleControlRoom() {
        super("Parabit Systems // INSS Sentinel Network — Circle Control Room C-04");
        connectDb();
        seedData();
        alertPool = buildAlertPool();
        buildUI();
        startTimers();
    }

    // ---------------- DB CONNECT ----------------
    private void connectDb() {
        try {
            dbc = new ParabitDBC();
            if (dbc.getConnection() == null) {
                System.out.println("[DB] No connection available — dashboard will fall back to demo data.");
            }
        } catch (Exception e) {
            System.out.println("[DB] Failed to initialise ParabitDBC: " + e.getMessage());
            dbc = null;
        }
    }

    private Connection conn() {
        return (dbc != null) ? dbc.getConnection() : null;
    }

    // ---------------- SEED / LOAD DATA ----------------
    private void seedData() {
        loadDesignationMap();
        loadZonesFromDb();
        loadCheckpointsFromDb();
        loadStaffFromDb();

        // ---- SIMULATED: no vehicle table wired up yet ----
        vehicles.add(new VehicleType("Two-Wheeler", 3120, 4000));
        vehicles.add(new VehicleType("Car / SUV", 1840, 2500));
        vehicles.add(new VehicleType("Auto / E-Rickshaw", 960, 1500));
        vehicles.add(new VehicleType("Bus", 214, 400));
        vehicles.add(new VehicleType("Commercial Truck", 88, 200));
        vehicles.add(new VehicleType("VIP Convoy", 6, 20));

        // ---- SIMULATED: no alerts/incidents table wired up yet ----
        alerts.add(new AlertItem("--:--:--", "info", "Circle 04 control room initialized"));
        alerts.add(new AlertItem("--:--:--", "info", dbc != null && conn() != null
                ? "Connected to parabitinss database"
                : "Running in offline demo mode — check DB connection"));

        for (int i = 0; i < 20; i++) {
            trendIn.add(190 + rng.nextDouble() * 40);
            trendOut.add(170 + rng.nextDouble() * 40);
        }
    }

    /** Loads Sno -> Designation from `empdesignation`, once. */
    private void loadDesignationMap() {
        designationMap.clear();
        Connection c = conn();
        if (c == null) return;
        String sql = "SELECT Sno, Designation FROM empdesignation";
        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                designationMap.put(rs.getInt("Sno"), rs.getString("Designation"));
            }
        } catch (SQLException e) {
            System.out.println("[DB] loadDesignationMap failed: " + e.getMessage());
        }
    }

    /** Loads zones from `zone` (ZNo, Status). Density is simulated (remembered across refreshes). */
    private void loadZonesFromDb() {
        List<Zone> loaded = new ArrayList<>();
        Connection c = conn();
        if (c != null) {
            String sql = "SELECT ZNo, Status FROM zone WHERE Status = 1 ORDER BY ZNo";
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    String zNo = rs.getString("ZNo");
                    String id = "Z-" + zNo;
                    double density = zoneDensityMemory.computeIfAbsent(id, k -> 30 + rng.nextDouble() * 40);
                    loaded.add(new Zone(id, "Zone " + zNo, density));
                }
            } catch (SQLException e) {
                System.out.println("[DB] loadZonesFromDb failed: " + e.getMessage());
            }
        }
        if (loaded.isEmpty()) {
            System.out.println("[DB] `zone` table returned 0 rows — using demo zone seed.");
            loaded.add(new Zone("Z-12", "Zone 12 (demo)", zoneDensityMemory.computeIfAbsent("Z-12", k -> 44.0)));
            loaded.add(new Zone("Z-13", "Zone 13 (demo)", zoneDensityMemory.computeIfAbsent("Z-13", k -> 66.0)));
            loaded.add(new Zone("Z-14", "Zone 14 (demo)", zoneDensityMemory.computeIfAbsent("Z-14", k -> 89.0)));
            loaded.add(new Zone("Z-15", "Zone 15 (demo)", zoneDensityMemory.computeIfAbsent("Z-15", k -> 33.0)));
        }
        zones.clear();
        zones.addAll(loaded);
    }

    /**
     * Loads checkpoints from `checkpoint`. Assumes:
     *  - Zone column stores the same business key as zone.ZNo
     *  - LocationInCharge1 holds the in-charge's display name directly (no join needed)
     *  - There's no live "gates currently open" column, so gatesOpen = NoOfGates
     *    when Status = 1 (active) and 0 when Status = 0 (closed). Adjust here if
     *    you add a real live-gate-count column later.
     */
    private void loadCheckpointsFromDb() {
        List<Checkpoint> loaded = new ArrayList<>();
        Connection c = conn();
        if (c != null) {
            String sql = "SELECT CpNo, CpName, Zone, NoOfGates, LocationInCharge1, EmpId1FK, Status "
                    + "FROM checkpoint ORDER BY Zone, CpNo";
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    String cpNo = rs.getString("CpNo");
                    String id = "CP-" + cpNo;
                    String name = rs.getString("CpName");
                    String zoneId = "Z-" + rs.getString("Zone");
                    int gates = rs.getInt("NoOfGates");
                    int status = rs.getInt("Status");
                    int gatesOpen = (status == 1) ? gates : 0;
                    String incharge = rs.getString("LocationInCharge1");
                    if (incharge == null || incharge.trim().isEmpty()) incharge = "Unassigned";
                    String empId = rs.getString("EmpId1FK");
                    if (empId == null) empId = "-";

                    double[] flow = cpFlowMemory.computeIfAbsent(id,
                            k -> new double[]{80 + rng.nextDouble() * 150, 70 + rng.nextDouble() * 130});

                    loaded.add(new Checkpoint(id, name, zoneId, gates, gatesOpen, incharge, empId, flow[0], flow[1]));
                }
            } catch (SQLException e) {
                System.out.println("[DB] loadCheckpointsFromDb failed: " + e.getMessage());
            }
        }
        if (loaded.isEmpty()) {
            System.out.println("[DB] `checkpoint` table returned 0 rows — using demo checkpoint seed.");
            loaded.add(new Checkpoint("CP-114", "Sangam Nose Gate A (demo)", "Z-12", 6, 6, "R. Tiwari", "EMP-2291", 210, 190));
            loaded.add(new Checkpoint("CP-115", "Sangam Nose Gate B (demo)", "Z-12", 4, 4, "S. Yadav", "EMP-2297", 140, 130));
            loaded.add(new Checkpoint("CP-118", "Jhusi Pontoon Bridge (demo)", "Z-13", 4, 3, "A. Mishra", "EMP-2304", 180, 150));
            loaded.add(new Checkpoint("CP-121", "Naini Underpass (demo)", "Z-14", 5, 2, "V. Kushwaha", "EMP-2318", 260, 110));
        }
        checkpoints.clear();
        checkpoints.addAll(loaded);
    }

    /**
     * Loads on-duty staff from `empreg` (Status = 1), joined in-memory to
     * `empdesignation` via EmpDesignationFK -> Sno. Assumes CheckpointFK
     * stores the same business key as checkpoint.CpNo.
     */
    private void loadStaffFromDb() {
        List<StaffMember> loaded = new ArrayList<>();
        Connection c = conn();
        if (c != null) {
            String sql = "SELECT EmpName, EmpDesignationFK, CheckpointFK FROM empreg WHERE Status = 1 ORDER BY CheckpointFK";
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    String name = rs.getString("EmpName");
                    int desigFk = rs.getInt("EmpDesignationFK");
                    String role = designationMap.getOrDefault(desigFk, "Staff");
                    String cpFk = rs.getString("CheckpointFK");
                    String cp = (cpFk == null || cpFk.trim().isEmpty()) ? "Unassigned" : "CP-" + cpFk;
                    loaded.add(new StaffMember(name, role, cp));
                }
            } catch (SQLException e) {
                System.out.println("[DB] loadStaffFromDb failed: " + e.getMessage());
            }
        }
        if (loaded.isEmpty()) {
            System.out.println("[DB] `empreg` table returned 0 active rows — using demo staff seed.");
            loaded.add(new StaffMember("R. Tiwari (demo)", "Circle Inspector", "CP-114"));
            loaded.add(new StaffMember("S. Yadav (demo)", "Gate Marshal", "CP-115"));
            loaded.add(new StaffMember("A. Mishra (demo)", "Gate Marshal", "CP-118"));
        }
        staff.clear();
        staff.addAll(loaded);
    }

    private AlertItem[] buildAlertPool() {
        return new AlertItem[] {
            new AlertItem(null, "warn", "Gate sensor offline — manual count active"),
            new AlertItem(null, "critical", "Zone density crossed RED threshold"),
            new AlertItem(null, "info", "VIP convoy cleared at checkpoint"),
            new AlertItem(null, "info", "Lost-person report resolved"),
            new AlertItem(null, "warn", "Queue exceeding 400m — dispatch additional marshals"),
            new AlertItem(null, "warn", "Wind gust logged near bridge approach"),
            new AlertItem(null, "info", "Shift handover complete"),
            new AlertItem(null, "warn", "Commercial truck entry denied — expired permit"),
            new AlertItem(null, "critical", "Gate count below safe minimum — reinforcements requested"),
            new AlertItem(null, "info", "Density stable for 20 consecutive minutes"),
        };
    }

    // ---------------- BAND HELPERS ----------------
    static Color bandColor(double v) {
        if (v >= 85) return RED;
        if (v >= 60) return YELLOW;
        return GREEN;
    }

    static String bandLabel(double v) {
        if (v >= 85) return "CRITICAL";
        if (v >= 60) return "ELEVATED";
        return "NORMAL";
    }

    // ---------------- UI BUILD ----------------
    private void buildUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BG);
        getContentPane().setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(14, 16, 12, 16));

        getContentPane().add(buildHeader(), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setOpaque(false);
        center.add(buildStatStrip(), BorderLayout.NORTH);
        center.add(buildMainGrid(), BorderLayout.CENTER);
        getContentPane().add(center, BorderLayout.CENTER);

        footerLabel = new JLabel("SYSTEM NOMINAL · LAST SYNC —", SwingConstants.CENTER);
        footerLabel.setFont(FONT_MONO_SM);
        footerLabel.setForeground(TEXT_MUTED);
        footerLabel.setBorder(new CompoundBorder(new MatteBorder(1, 0, 0, 0, BORDER_C), new EmptyBorder(8, 0, 0, 0)));
        getContentPane().add(footerLabel, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(1180, 780));
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, AMBER_DEEP), new EmptyBorder(0, 0, 10, 0)));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("PARABIT SYSTEMS // INSS SENTINEL NETWORK");
        eyebrow.setFont(FONT_MONO_SM);
        eyebrow.setForeground(AMBER_DIM);
        JLabel title = new JLabel("CIRCLE CONTROL ROOM — C-04");
        title.setFont(FONT_TITLE);
        title.setForeground(AMBER);
        JLabel sub = new JLabel("Live from parabitinss DB · Zones / Checkpoints / Staff · Sector: Prayagraj East");
        sub.setFont(FONT_MONO_SM);
        sub.setForeground(TEXT_MUTED);
        left.add(eyebrow); left.add(title); left.add(sub);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        clockLabel = new JLabel("00:00:00");
        clockLabel.setFont(FONT_TITLE);
        clockLabel.setForeground(TEXT);
        clockLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dateLabel = new JLabel("—");
        dateLabel.setFont(FONT_MONO_SM);
        dateLabel.setForeground(TEXT_MUTED);
        dateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        statusPillPanel = new JPanel();
        statusPillPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        statusPillPanel.setOpaque(false);
        statusPillPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        statusPillLabel = new JLabel("NOMINAL");
        statusPillLabel.setFont(FONT_HEAD);
        statusPillLabel.setForeground(GREEN);
        statusPillLabel.setBorder(new CompoundBorder(new LineBorder(GREEN, 1, true), new EmptyBorder(3, 10, 3, 10)));
        statusPillPanel.add(statusPillLabel);

        right.add(clockLabel); right.add(dateLabel); right.add(Box.createVerticalStrut(4)); right.add(statusPillPanel);

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel buildStatStrip() {
        JPanel strip = new JPanel(new GridLayout(1, 5, 8, 0));
        strip.setOpaque(false);
        String[] labels = { "Persons in Circle", "Vehicles in Circle", "Checkpoints Active", "Staff On Duty", "Active Alerts" };
        statValueLabels = new JLabel[5];
        for (int i = 0; i < labels.length; i++) {
            JPanel card = statCard(labels[i]);
            strip.add(card);
        }
        return strip;
    }

    private JPanel statCard(String label) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL);
        card.setBorder(new CompoundBorder(
            new CompoundBorder(new LineBorder(BORDER_C, 1), new MatteBorder(0, 3, 0, 0, AMBER_DEEP)),
            new EmptyBorder(8, 10, 8, 10)));
        JLabel lbl = new JLabel(label.toUpperCase());
        lbl.setFont(FONT_MONO_SM);
        lbl.setForeground(TEXT_MUTED);
        JLabel val = new JLabel("—");
        val.setFont(FONT_STAT);
        val.setForeground(AMBER);
        card.add(lbl); card.add(Box.createVerticalStrut(3)); card.add(val);

        int idx = -1;
        if (label.equals("Persons in Circle")) idx = 0;
        else if (label.equals("Vehicles in Circle")) idx = 1;
        else if (label.equals("Checkpoints Active")) idx = 2;
        else if (label.equals("Staff On Duty")) idx = 3;
        else if (label.equals("Active Alerts")) idx = 4;
        if (idx >= 0) {
            statValueLabels[idx] = val;
            if (idx == 4) activeAlertsValueLabel = val;
        }
        return card;
    }

    private JPanel buildMainGrid() {
        JPanel grid = new JPanel(new BorderLayout(10, 10));
        grid.setOpaque(false);

        // top row: gauges (left, narrower) + trend (right, wider)
        JPanel topRow = new JPanel(new GridBagLayout());
        topRow.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = 0;
        gc.weighty = 1;
        gc.gridx = 0; gc.weightx = 0.42; gc.insets = new Insets(0, 0, 0, 5);
        topRow.add(buildGaugesPanel(), gc);
        gc.gridx = 1; gc.weightx = 0.58; gc.insets = new Insets(0, 5, 0, 0);
        topRow.add(buildTrendPanel(), gc);
        topRow.setPreferredSize(new Dimension(100, 250));

        // bottom row: checkpoints (left, tall) + [vehicles/staff stacked] (middle) + alerts (right, tall)
        JPanel bottomRow = new JPanel(new GridBagLayout());
        bottomRow.setOpaque(false);
        GridBagConstraints bc = new GridBagConstraints();
        bc.fill = GridBagConstraints.BOTH;
        bc.gridy = 0;
        bc.weighty = 1;
        bc.gridx = 0; bc.weightx = 0.38; bc.insets = new Insets(0, 0, 0, 5);
        bottomRow.add(buildCheckpointsPanel(), bc);

        JPanel middleStack = new JPanel();
        middleStack.setOpaque(false);
        middleStack.setLayout(new BoxLayout(middleStack, BoxLayout.Y_AXIS));
        middleStack.add(buildVehiclesPanel());
        middleStack.add(Box.createVerticalStrut(10));
        middleStack.add(buildStaffPanel());
        bc.gridx = 1; bc.weightx = 0.30; bc.insets = new Insets(0, 5, 0, 5);
        bottomRow.add(middleStack, bc);

        bc.gridx = 2; bc.weightx = 0.32; bc.insets = new Insets(0, 5, 0, 0);
        bottomRow.add(buildAlertsPanel(), bc);

        grid.add(topRow, BorderLayout.NORTH);
        grid.add(bottomRow, BorderLayout.CENTER);
        return grid;
    }

    private JPanel panelShell(String title, String metaText, JLabel metaLabelOut) {
        JPanel shell = new JPanel(new BorderLayout());
        shell.setBackground(PANEL);
        shell.setBorder(new LineBorder(BORDER_C, 1));

        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(PANEL_2);
        head.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, BORDER_C), new EmptyBorder(6, 12, 6, 12)));
        JLabel titleLbl = new JLabel(title.toUpperCase());
        titleLbl.setFont(FONT_HEAD);
        titleLbl.setForeground(AMBER);
        head.add(titleLbl, BorderLayout.WEST);
        if (metaLabelOut != null) {
            metaLabelOut.setText(metaText);
            metaLabelOut.setFont(FONT_MONO_SM);
            metaLabelOut.setForeground(TEXT_MUTED);
            head.add(metaLabelOut, BorderLayout.EAST);
        }
        shell.add(head, BorderLayout.NORTH);
        return shell;
    }

    private JPanel buildGaugesPanel() {
        JLabel meta = new JLabel();
        JPanel shell = panelShell("Zone Density", "crowd pressure index (simulated)", meta);
        JPanel body = new JPanel(new GridLayout(1, Math.max(1, zones.size()), 4, 0));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(6, 6, 6, 6));
        gaugePanels = new GaugePanel[zones.size()];
        for (int i = 0; i < zones.size(); i++) {
            Zone z = zones.get(i);
            GaugePanel gp = new GaugePanel(z.id, z.name, z.density);
            gaugePanels[i] = gp;
            body.add(gp);
        }
        shell.add(body, BorderLayout.CENTER);
        return shell;
    }

    private JPanel buildTrendPanel() {
        JLabel meta = new JLabel();
        JPanel shell = panelShell("Entry / Exit Flow — Live", "persons / min (simulated)", meta);
        trendPanel = new TrendChartPanel(meta);
        trendPanel.setBorder(new EmptyBorder(8, 10, 8, 10));
        shell.add(trendPanel, BorderLayout.CENTER);
        return shell;
    }

    private JPanel buildCheckpointsPanel() {
        cpMetaLabel = new JLabel();
        JPanel shell = panelShell("Checkpoints", "", cpMetaLabel);

        String[] cols = { "CP", "Location", "Gates", "In-charge", "Flow", "" };
        cpTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(cpTableModel);
        styleTable(table);
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusDotRenderer());
        table.getColumnModel().getColumn(5).setMaxWidth(30);
        table.getColumnModel().getColumn(2).setMaxWidth(55);

        refreshCheckpointRows();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(0, 0, 0, 0));
        sp.getViewport().setBackground(PANEL);
        shell.add(sp, BorderLayout.CENTER);
        return shell;
    }

    private void styleTable(JTable table) {
        table.setBackground(PANEL);
        table.setForeground(TEXT);
        table.setFont(FONT_MONO_SM);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(PANEL_2);
        table.setSelectionForeground(AMBER);
        table.setGridColor(BORDER_C);
        JTableHeader th = table.getTableHeader();
        th.setBackground(PANEL);
        th.setForeground(TEXT_MUTED);
        th.setFont(FONT_MONO_SM);
        th.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_C));
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                c.setBackground(sel ? PANEL_2 : PANEL);
                c.setForeground(col == 0 ? AMBER_DIM : TEXT);
                c.setBorder(new EmptyBorder(2, 6, 2, 6));
                return c;
            }
        });
    }

    private class StatusDotRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int row, int col) {
            JPanel p = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor((Color) v);
                    g2.fillOval(getWidth() / 2 - 4, getHeight() / 2 - 4, 8, 8);
                    g2.dispose();
                }
            };
            p.setBackground(PANEL);
            p.setOpaque(true);
            return p;
        }
    }

    private void refreshCheckpointRows() {
        cpTableModel.setRowCount(0);
        for (Checkpoint c : checkpoints) {
            Zone z = findZone(c.zoneId);
            boolean gateIssue = c.gatesOpen < c.gates;
            Color dot = gateIssue ? RED : bandColor(z != null ? z.density : 0);
            String location = c.name + "  (" + c.zoneId + " · " + c.empId + ")";
            String flow = "IN " + Math.round(c.inRate) + " · OUT " + Math.round(c.outRate);
            cpTableModel.addRow(new Object[] { c.id, location, c.gatesOpen + "/" + c.gates, c.incharge, flow, dot });
        }
        long fullGate = checkpoints.stream().filter(c -> c.gatesOpen == c.gates).count();
        cpMetaLabel.setText(fullGate + " / " + checkpoints.size() + " at full gates");
    }

    private Zone findZone(String id) {
        for (Zone z : zones) if (z.id.equals(id)) return z;
        return null;
    }

    private JPanel buildVehiclesPanel() {
        vehTotalLabel = new JLabel();
        JPanel shell = panelShell("Vehicle Mix", "simulated — no source table yet", vehTotalLabel);
        vehicleBarsPanel = new JPanel();
        vehicleBarsPanel.setOpaque(false);
        vehicleBarsPanel.setLayout(new BoxLayout(vehicleBarsPanel, BoxLayout.Y_AXIS));
        vehicleBarsPanel.setBorder(new EmptyBorder(10, 12, 10, 12));
        refreshVehicleBars();
        shell.add(vehicleBarsPanel, BorderLayout.CENTER);
        shell.setPreferredSize(new Dimension(100, 230));
        return shell;
    }

    private void refreshVehicleBars() {
        vehicleBarsPanel.removeAll();
        long total = 0;
        for (VehicleType v : vehicles) total += v.count;
        vehTotalLabel.setText(String.format("%,d total", total));
        for (VehicleType v : vehicles) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setBorder(new EmptyBorder(0, 0, 6, 0));
            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            JLabel name = new JLabel(v.type);
            name.setFont(FONT_MONO_SM);
            name.setForeground(TEXT);
            JLabel count = new JLabel(String.format("%,d", v.count));
            count.setFont(FONT_MONO_SM);
            count.setForeground(AMBER);
            top.add(name, BorderLayout.WEST);
            top.add(count, BorderLayout.EAST);
            MiniBar bar = new MiniBar(Math.min(100.0, v.count * 100.0 / v.max));
            row.add(top, BorderLayout.NORTH);
            row.add(bar, BorderLayout.SOUTH);
            vehicleBarsPanel.add(row);
        }
        vehicleBarsPanel.revalidate();
        vehicleBarsPanel.repaint();
    }

    private static class MiniBar extends JPanel {
        double pct;
        MiniBar(double pct) { this.pct = pct; setOpaque(false); setPreferredSize(new Dimension(100, 6)); setMaximumSize(new Dimension(4000, 6)); }
        void setPct(double p) { this.pct = p; repaint(); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(BORDER_C);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 3, 3);
            g2.setColor(AMBER);
            int w = (int) (getWidth() * Math.max(0, Math.min(100, pct)) / 100.0);
            g2.fillRoundRect(0, 0, w, getHeight(), 3, 3);
            g2.dispose();
        }
    }

    private JPanel buildStaffPanel() {
        staffMetaLabel = new JLabel(staff.size() + " assigned");
        JPanel shell = panelShell("Staff On Duty", staff.size() + " assigned", staffMetaLabel);
        staffListPanel = new JPanel();
        staffListPanel.setOpaque(false);
        staffListPanel.setLayout(new BoxLayout(staffListPanel, BoxLayout.Y_AXIS));
        staffListPanel.setBorder(new EmptyBorder(6, 12, 6, 12));
        refreshStaffRows();
        JScrollPane sp = new JScrollPane(staffListPanel);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(PANEL);
        sp.setPreferredSize(new Dimension(100, 150));
        shell.add(sp, BorderLayout.CENTER);
        shell.setPreferredSize(new Dimension(100, 210));
        return shell;
    }

    private void refreshStaffRows() {
        staffListPanel.removeAll();
        for (StaffMember s : staff) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, PANEL_2), new EmptyBorder(4, 0, 4, 0)));
            JPanel nameCol = new JPanel();
            nameCol.setOpaque(false);
            nameCol.setLayout(new BoxLayout(nameCol, BoxLayout.Y_AXIS));
            JLabel name = new JLabel(s.name);
            name.setFont(FONT_MONO_SM);
            name.setForeground(TEXT);
            JLabel role = new JLabel(s.role);
            role.setFont(new Font("Consolas", Font.PLAIN, 9));
            role.setForeground(TEXT_MUTED);
            nameCol.add(name); nameCol.add(role);
            JLabel cp = new JLabel(s.cp);
            cp.setFont(new Font("Consolas", Font.PLAIN, 9));
            cp.setForeground(AMBER_DIM);
            row.add(nameCol, BorderLayout.WEST);
            row.add(cp, BorderLayout.EAST);
            staffListPanel.add(row);
        }
        if (staffMetaLabel != null) staffMetaLabel.setText(staff.size() + " assigned");
        staffListPanel.revalidate();
        staffListPanel.repaint();
    }

    private JPanel buildAlertsPanel() {
        JPanel shell = panelShell("Activity Log", "live feed (simulated)", new JLabel());
        alertsListPanel = new JPanel();
        alertsListPanel.setOpaque(false);
        alertsListPanel.setLayout(new BoxLayout(alertsListPanel, BoxLayout.Y_AXIS));
        alertsListPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        refreshAlerts();
        JScrollPane sp = new JScrollPane(alertsListPanel);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(PANEL);
        shell.add(sp, BorderLayout.CENTER);
        return shell;
    }

    private void refreshAlerts() {
        alertsListPanel.removeAll();
        Iterator<AlertItem> it = alerts.descendingIterator();
        int shown = 0;
        while (it.hasNext() && shown < 12) {
            alertsListPanel.add(alertRow(it.next()));
            alertsListPanel.add(Box.createVerticalStrut(6));
            shown++;
        }
        alertsListPanel.revalidate();
        alertsListPanel.repaint();
    }

    private JPanel alertRow(AlertItem a) {
        Color accent = a.severity.equals("critical") ? RED : a.severity.equals("warn") ? YELLOW : AMBER_DIM;
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setBackground(PANEL_2);
        row.setBorder(new CompoundBorder(new MatteBorder(0, 3, 0, 0, accent), new EmptyBorder(5, 8, 5, 8)));
        JLabel time = new JLabel(a.time);
        time.setFont(new Font("Consolas", Font.PLAIN, 9));
        time.setForeground(TEXT_MUTED);
        JLabel tag = new JLabel(a.severity.toUpperCase() + "  " + a.message);
        tag.setFont(FONT_MONO_SM);
        tag.setForeground(TEXT);
        row.add(time);
        row.add(tag);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, row.getPreferredSize().height));
        return row;
    }

    // ---------------- GAUGE COMPONENT ----------------
    static class GaugePanel extends JPanel {
        String zoneId, zoneName;
        double value;

        GaugePanel(String zoneId, String zoneName, double value) {
            this.zoneId = zoneId; this.zoneName = zoneName; this.value = value;
            setOpaque(false);
            setPreferredSize(new Dimension(140, 150));
        }

        void setValue(double v) { this.value = v; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int radius = Math.min(w / 2 - 16, 46);
            int cx = w / 2;
            int cy = 16 + radius;

            g2.setStroke(new BasicStroke(9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawBand(g2, cx, cy, radius, 0, 60, GREEN);
            drawBand(g2, cx, cy, radius, 60, 85, YELLOW);
            drawBand(g2, cx, cy, radius, 85, 100, RED);

            double angleDeg = 180 - 1.8 * value;
            double rad = Math.toRadians(angleDeg);
            int nx = (int) (cx + (radius - 12) * Math.cos(rad));
            int ny = (int) (cy - (radius - 12) * Math.sin(rad));
            g2.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(TEXT);
            g2.drawLine(cx, cy, nx, ny);
            g2.setColor(AMBER);
            g2.fillOval(cx - 4, cy - 4, 8, 8);

            Color bc = bandColor(value);
            FontMetrics fm;

            g2.setFont(FONT_MONO_B);
            g2.setColor(bc);
            String valStr = String.valueOf(Math.round(value));
            fm = g2.getFontMetrics();
            g2.drawString(valStr, cx - fm.stringWidth(valStr) / 2, cy + 22);

            g2.setFont(FONT_MONO_SM);
            g2.setColor(TEXT);
            fm = g2.getFontMetrics();
            g2.drawString(zoneName, cx - fm.stringWidth(zoneName) / 2, cy + 36);

            g2.setColor(TEXT_MUTED);
            String idLine = zoneId + " · " + bandLabel(value);
            fm = g2.getFontMetrics();
            g2.drawString(idLine, cx - fm.stringWidth(idLine) / 2, cy + 49);

            g2.dispose();
        }

        private void drawBand(Graphics2D g2, int cx, int cy, int r, double v0, double v1, Color c) {
            double startAngle = 180 - 1.8 * v1;
            double extent = 1.8 * (v1 - v0);
            g2.setColor(c);
            g2.draw(new Arc2D.Double(cx - r, cy - r, 2.0 * r, 2.0 * r, startAngle, extent, Arc2D.OPEN));
        }
    }

    // ---------------- TREND CHART COMPONENT ----------------
    private class TrendChartPanel extends JPanel {
        JLabel metaLabel;
        TrendChartPanel(JLabel metaLabel) {
            this.metaLabel = metaLabel;
            setOpaque(false);
            setPreferredSize(new Dimension(300, 170));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), pad = 8;

            g2.setColor(BORDER_C);
            for (int i = 0; i <= 3; i++) {
                int y = pad + i * (h - 2 * pad) / 3;
                g2.drawLine(pad, y, w - pad, y);
            }

            double max = 1;
            for (double d : trendIn) max = Math.max(max, d);
            for (double d : trendOut) max = Math.max(max, d);
            max *= 1.15;

            drawSeries(g2, trendOut, w, h, pad, max, AMBER_DEEP);
            drawSeries(g2, trendIn, w, h, pad, max, AMBER);

            g2.setFont(new Font("Consolas", Font.PLAIN, 10));
            g2.setColor(AMBER);
            g2.fillRect(w - 130, 6, 8, 8);
            g2.drawString("IN", w - 116, 14);
            g2.setColor(AMBER_DEEP);
            g2.fillRect(w - 90, 6, 8, 8);
            g2.drawString("OUT", w - 76, 14);

            g2.dispose();
        }

        private void drawSeries(Graphics2D g2, List<Double> series, int w, int h, int pad, double max, Color c) {
            if (series.size() < 2) return;
            g2.setColor(c);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D path = new Path2D.Double();
            for (int i = 0; i < series.size(); i++) {
                double x = pad + i * (double) (w - 2 * pad) / (series.size() - 1);
                double y = h - pad - (series.get(i) / max) * (h - 2 * pad);
                if (i == 0) path.moveTo(x, y); else path.lineTo(x, y);
            }
            g2.draw(path);
        }

        void refreshMeta() {
            if (metaLabel != null && !trendIn.isEmpty() && !trendOut.isEmpty()) {
                metaLabel.setText("IN " + Math.round(trendIn.get(trendIn.size() - 1)) + "/min · OUT " + Math.round(trendOut.get(trendOut.size() - 1)) + "/min");
            }
        }
    }

    // ---------------- LIVE TICK ----------------
    private void startTimers() {
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFmt = new SimpleDateFormat("EEE, dd MMM yyyy");

        javax.swing.Timer clockTimer = new javax.swing.Timer(1000, e -> {
            Date now = new Date();
            clockLabel.setText(timeFmt.format(now));
            dateLabel.setText(dateFmt.format(now));
        });
        clockTimer.start();

        javax.swing.Timer tickTimer = new javax.swing.Timer(2500, e -> tick());
        tickTimer.start();

        // Periodically re-pull checkpoints/staff from the DB so status/roster
        // edits made in phpMyAdmin appear without restarting the app.
        javax.swing.Timer dbTimer = new javax.swing.Timer(DB_REFRESH_MS, e -> refreshFromDb());
        dbTimer.start();

        Date now = new Date();
        clockLabel.setText(timeFmt.format(now));
        dateLabel.setText(dateFmt.format(now));
        tick();
    }

    /** Re-pulls checkpoints and staff from the DB and refreshes the affected panels. */
    private void refreshFromDb() {
        loadCheckpointsFromDb();
        loadStaffFromDb();
        refreshCheckpointRows();
        refreshStaffRows();
    }

    private void tick() {
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");

        // ---- SIMULATED: zone density (no live sensor table yet) ----
        for (Zone z : zones) {
            z.density = clamp(z.density + (rng.nextDouble() * 10 - 5), 5, 99);
            zoneDensityMemory.put(z.id, z.density);
        }
        for (int i = 0; i < gaugePanels.length; i++) {
            gaugePanels[i].setValue(zones.get(i).density);
        }

        // ---- SIMULATED: entry/exit flow (no timestamped log table yet) ----
        for (Checkpoint c : checkpoints) {
            c.inRate = Math.max(20, c.inRate + (rng.nextDouble() * 30 - 15));
            c.outRate = Math.max(20, c.outRate + (rng.nextDouble() * 30 - 15));
            cpFlowMemory.put(c.id, new double[]{c.inRate, c.outRate});
        }
        refreshCheckpointRows();

        // ---- SIMULATED: circle-wide totals ----
        personsInCircle = Math.max(0, personsInCircle + Math.round(rng.nextDouble() * 160 - 40));
        vehiclesInCircle = Math.max(0, vehiclesInCircle + Math.round(rng.nextDouble() * 28 - 8));

        double avgIn = checkpoints.stream().mapToDouble(c -> c.inRate).average().orElse(0);
        double avgOut = checkpoints.stream().mapToDouble(c -> c.outRate).average().orElse(0);
        trendIn.remove(0); trendIn.add(avgIn);
        trendOut.remove(0); trendOut.add(avgOut);
        trendPanel.repaint();
        trendPanel.refreshMeta();

        // ---- SIMULATED: activity log (no incidents table yet) ----
        if (rng.nextDouble() < 0.35) {
            AlertItem pick = alertPool[rng.nextInt(alertPool.length)];
            alerts.add(new AlertItem(timeFmt.format(new Date()), pick.severity, pick.message));
            while (alerts.size() > 30) alerts.removeFirst();
            refreshAlerts();
        }
        long activeAlertCount = alerts.stream().filter(a -> !a.severity.equals("info")).count();

        double worst = zones.stream().mapToDouble(z -> z.density).max().orElse(0);
        String band = bandLabel(worst);
        Color bandC = bandColor(worst);
        statusPillLabel.setText(band);
        statusPillLabel.setForeground(bandC);
        statusPillLabel.setBorder(new CompoundBorder(new LineBorder(bandC, 1, true), new EmptyBorder(3, 10, 3, 10)));

        long activeCps = checkpoints.stream().filter(c -> c.gatesOpen > 0).count();
        statValueLabels[0].setText(String.format("%,d", personsInCircle));
        statValueLabels[1].setText(String.format("%,d", vehiclesInCircle));
        statValueLabels[2].setText(activeCps + " / " + checkpoints.size());
        statValueLabels[3].setText(String.valueOf(staff.size()));
        statValueLabels[4].setText(String.valueOf(Math.min(9, activeAlertCount)));
        statValueLabels[4].setForeground(activeAlertCount > 0 ? RED : AMBER);

        footerLabel.setText("SYSTEM " + band + " · LAST SYNC " + timeFmt.format(new Date())
                + (conn() != null ? " · DB CONNECTED" : " · DB OFFLINE"));
    }

    private static double clamp(double v, double lo, double hi) { return Math.max(lo, Math.min(hi, v)); }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // fall back to default look and feel
        }
        EventQueue.invokeLater(() -> new PbtCircleControlRoom().setVisible(true));
    }
}