package com.mycompany.pbtinss;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TollDirector extends javax.swing.JFrame {

    public TollDirector() {
        initComponents();

        // === Table Setup ===
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Toll Name", "Toll ID", "Alert", "Graph"}
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return JProgressBar.class;
                if (columnIndex == 3) return JButton.class;
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Graph button editable
            }
        };

        t1.setModel(model);
        t1.getColumnModel().getColumn(2).setCellRenderer(new ProgressRenderer());
        t1.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        t1.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));
        t1.setRowHeight(23);

        // === Load Data into Table ===
        ParabitDBC db1 = new ParabitDBC();
        try {
            db1.rs = db1.stm.executeQuery("SELECT LocationName, CPName FROM checkpointtoll;");
            while (db1.rs.next()) {
                model.addRow(new Object[]{db1.rs.getString(1), db1.rs.getString(2), 0, "Graph"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // === Setup JavaFX Chart Panel ===
        JFXPanel jfxPanel = new JFXPanel();
        p1.setLayout(new java.awt.BorderLayout());
        p1.add(jfxPanel, java.awt.BorderLayout.CENTER);

        setSize(1350, 700);
        setLocationRelativeTo(null);

        // === Run JavaFX UI Thread for Charts ===
        Platform.runLater(() -> {
            try {
                // -----------------------------
                // 1️⃣ BAR CHART SECTION
                // -----------------------------
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setLabel("Date");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Vehicle Count");

                BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
                barChart.setTitle("Vehicle Count");

                LocalDate today = LocalDate.now();
                int a = 0, b = 0, c = 0;
                ParabitDBC db = new ParabitDBC();
                try{
                    String sql = "SELECT Time, SUM(APCount + ACCount) AS TotalCount " +
                    "FROM tollpassedveh WHERE TollCpNo = 2 AND Date = ? " +
                    "GROUP BY Time ORDER BY CAST(Time AS UNSIGNED)";
                    
                    java.time.LocalDate currDate = java.time.LocalDate.now();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(currDate);
                    
                    db.ps = db.con.prepareStatement(sql);
                    db.ps.setDate(1, sqlDate);
                    db.rs = db.ps.executeQuery();
                    db.rs = db.stm.executeQuery(sql);
                    if (db.rs.next()) a = db.rs.getInt(1);

                    db.ps = db.con.prepareStatement(sql);
                    db.ps.setDate(1, sqlDate);
                    db.rs = db.ps.executeQuery();
                    db.rs = db.stm.executeQuery(sql);
                    if (db.rs.next()) b = db.rs.getInt(1);

                    db.ps = db.con.prepareStatement(sql);
                    db.ps.setDate(1, sqlDate);
                    db.rs = db.ps.executeQuery();
                    db.rs = db.stm.executeQuery(sql);
                    if (db.rs.next()) c = db.rs.getInt(1);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(TollDirector.class.getName()).log(Level.SEVERE, null, ex);
                }

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Vehicles Registered");
                series.getData().add(new XYChart.Data<>(today.minusDays(1).toString(), a));
                series.getData().add(new XYChart.Data<>(today.toString(), b));
                series.getData().add(new XYChart.Data<>(today.plusDays(1).toString(), c));

                barChart.getData().add(series);

                // -----------------------------
                // 2️⃣ LINE CHART SECTION (from LiveChart class)
                // -----------------------------
                LiveChart liveChart = new LiveChart();
                Scene lineScene = liveChart.initChart();  // assumes initChart() returns a Scene
                Parent lineRoot = lineScene.getRoot();

                // -----------------------------
                // 3️⃣ COMBINE BOTH CHARTS
                // -----------------------------
                Label barLabel = new Label("📊 Daily Vehicle Bar Chart");
                barLabel.setStyle("-fxf-font-size: 15px; -fx-font-weight: bold;");

                Label lineLabel = new Label("📈 Live Crowd Line Chart");
                lineLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

                // --- Bar Chart JFXPanel ---
                JFXPanel barPanel = new JFXPanel();
                barPanel.setPreferredSize(new Dimension(600,350));
                p1.setLayout(new java.awt.BorderLayout());
                p1.add(barPanel, java.awt.BorderLayout.NORTH); // top half

                Platform.runLater(() -> {
                    Scene barScene = new Scene(barChart, 300, 350);
                    barPanel.setScene(barScene);
                });

                // --- Line Chart JFXPanel ---
                JFXPanel linePanel = new JFXPanel();
                linePanel.setPreferredSize(new Dimension(600,350));
                p1.add(linePanel, java.awt.BorderLayout.SOUTH); // bottom half

                Platform.runLater(() -> {
                    LiveChart liveChart1 = new LiveChart();
                    Scene lineScene1;
                    try {
                        lineScene1 = liveChart1.initChart();
                        linePanel.setScene(lineScene1);
                    } catch (Exception ex) {
                        System.getLogger(TollDirector.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }

                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // === Custom Renderers for Table ===
    class ProgressRenderer extends JProgressBar implements TableCellRenderer {
        public ProgressRenderer() {
            super(0, 100);
            setStringPainted(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setValue((int) value);
            return this;
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Button" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private boolean clicked;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "Button" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                Platform.runLater(() -> {
                    try {
                        LiveChart chart = new LiveChart();
                        Stage stage = new Stage();
                        Scene scene = chart.initChart();  // initChart returns Scene
                        stage.setTitle("Live Chart");
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
    clicked = false;
    return label;
}



        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }

    // === Auto-Generated Swing Code ===
    @SuppressWarnings("unchecked")
    private void initComponents() {

        p1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout p1Layout = new javax.swing.GroupLayout(p1);
        p1.setLayout(p1Layout);
        p1Layout.setHorizontalGroup(
                p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 616, Short.MAX_VALUE)
        );
        p1Layout.setVerticalGroup(
                p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 350, Short.MAX_VALUE)
        );

        t1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Toll Name", "Toll ID", "Alert", "Graph"
                }
        ));
        jScrollPane1.setViewportView(t1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TollDirector().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel p1;
    private javax.swing.JTable t1;
}
