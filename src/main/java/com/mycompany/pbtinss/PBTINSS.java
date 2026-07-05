package com.mycompany.pbtinss;

import com.mycompany.pbtinss.analytics.AnalyticalBackgroundWorker;
import com.mycompany.pbtinss.analytics.CrowdPredictionEngine;
import com.mycompany.pbtinss.database.CrowdPredictionDAO;
import com.mycompany.pbtinss.database.ParabitDBC;
import com.mycompany.pbtinss.ParabitCrowdOrchestrator;
import com.mycompany.pbtinss.registration.PbtEmpLog;
import java.util.HashMap;
import java.util.Map;

/**
 * Main Architectural Entry Point for the ParabitINSS Hybrid Framework.
 * Coordinates system memory startup, database connectivity, and UI kickoff.
 */
public class PBTINSS {

    // Thread-safe global references for real-time tracking across dashboards
    private static final Map<String, CrowdPredictionEngine> sectorEngines = new HashMap<>();
    private static CrowdPredictionDAO predictionDAO;
    private static SectorCapacityRegistry capacityRegistry;
    private static ParabitCrowdOrchestrator crowdOrchestrator;
    private static AnalyticalBackgroundWorker backgroundWorker;

    public static void main(String[] args) {
        System.out.println("[SYSTEM START] Initializing ParabitINSS Hybrid Core...");
        
        // 1. Initialize DB Connectivity Context
        ParabitDBC db = new ParabitDBC();
        
        // 2. Setup Analytics Processing Layers
        predictionDAO = new CrowdPredictionDAO(db.getConnection());
        capacityRegistry = new SectorCapacityRegistry();

        // 3. Register Deployment Sectors & Safety Threshold Limits
        capacityRegistry.registerSectorCapacity("SECTOR_ALPHA", 5000);
        sectorEngines.put("SECTOR_ALPHA", new CrowdPredictionEngine("SECTOR_ALPHA", 12));

        // 4. ===== CORE COHERENCE HOOK: INITIALIZE ORCHESTRATION & BACKGROUND TASKS =====
        // Instantiates the logic coordinating variance checking with DB storage
        crowdOrchestrator = new ParabitCrowdOrchestrator(predictionDAO, capacityRegistry, 12);
        
        // Assign the background scheduler to evaluate SECTOR_ALPHA automatically every 5 seconds
        backgroundWorker = new AnalyticalBackgroundWorker(crowdOrchestrator, "SECTOR_ALPHA", 5);
        backgroundWorker.startContinuousAnalysis();

        System.out.println("[SYSTEM ONLINE] Automated Asynchronous Analytics Engine online.");

        // 5. Safely Kick off Login Dialog Window on the AWT Event Dispatch Thread
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Pass an unanchored parent Frame since PbtEmpLog behaves as a modal JDialog
                javax.swing.JFrame parentAnchor = new javax.swing.JFrame();
                PbtEmpLog loginDialog = new PbtEmpLog(parentAnchor, true);
                
                System.out.println("[UI LAUNCH] Presenting Employee Login Terminal...");
                loginDialog.setVisible(true);
            }
        });
    }

    // --- Global Accessors to share engine context with your JFrames and JDialogs ---
    
    public static Map<String, CrowdPredictionEngine> getSectorEngines() {
        return sectorEngines;
    }
    
    public static CrowdPredictionDAO getPredictionDAO() {
        return predictionDAO;
    }

    public static SectorCapacityRegistry getCapacityRegistry() {
        return capacityRegistry;
    }

    public static ParabitCrowdOrchestrator getCrowdOrchestrator() {
        return crowdOrchestrator;
    }
}