/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pbtinss;
import com.mycompany.pbtinss.analytics.CrowdPredictionEngine;
import com.mycompany.pbtinss.analytics.CrowdPredictionEngine.PredictionReport;
import com.mycompany.pbtinss.database.CrowdPredictionDAO;
import com.mycompany.pbtinss.SectorCapacityRegistry;
import com.mycompany.pbtinss.SectorCapacityRegistry.RiskLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * The central controller that coordinates data ingestion, execution of the prediction math engine,
 * database updates, and safety threshold compliance monitoring.
 */
public class ParabitCrowdOrchestrator {

    private final Map<String, CrowdPredictionEngine> enginesMap = new HashMap<>();
    private final CrowdPredictionDAO dataAccessLayer;
    private final SectorCapacityRegistry capacityRegistry;
    private final int globalRollingWindowSize;

    public ParabitCrowdOrchestrator(CrowdPredictionDAO dao, SectorCapacityRegistry registry, int windowSize) {
        this.dataAccessLayer = dao;
        this.capacityRegistry = registry;
        this.globalRollingWindowSize = windowSize;
    }

    /**
     * Entry Point: Call this when a physical node captures radio telemetry from its sector environment.
     */
    public synchronized void handleIncomingNodeTelemetry(String sectorId, String nodeId, int rssi) {
        // Ensure a dedicated analytical engine exists for this target sector
        enginesMap.putIfAbsent(sectorId, new CrowdPredictionEngine(sectorId, globalRollingWindowSize));
        
        CrowdPredictionEngine engine = enginesMap.get(sectorId);
        engine.ingestTelemetry(nodeId, rssi);
    }

    /**
     * Periodic Execution Loop: Runs analytics updates, writes to the DB, and checks safety policies.
     */
    public synchronized void runAnalyticalUpdateCycle(String sectorId) {
        CrowdPredictionEngine engine = enginesMap.get(sectorId);
        if (engine == null) return;

        // 1. Generate standard-deviation/variance prediction
        PredictionReport report = engine.generatePrediction();

        // 2. Persist metrics seamlessly into the database
        boolean dbWriteSuccess = dataAccessLayer.savePredictionReport(report);
        if (!dbWriteSuccess) {
            System.err.println("[CRITICAL ALERT] Analytical state generated but DB logging failed. System degrading gracefully.");
        }

        // 3. Evaluate safety risk profiles and trigger alarms if limits are crossed
        RiskLevel safetyRisk = capacityRegistry.evaluateSafetyRisk(sectorId, report.getPredictedCount());
        dispatchCommandActions(report, safetyRisk);
    }

    /**
     * Bridges analytical data back into real-world crowd management controls.
     */
    private void dispatchCommandActions(PredictionReport report, RiskLevel risk) {
        switch (risk) {
            case CRITICAL_OVERCROWDING:
                System.out.printf("[COMMAND ACTION - CRITICAL] Sector %s reached headcount %d! AUTOMATICALLY CLOSING PRE-ENTRY CHECKPOINTS.\n", 
                        report.getSectorId(), report.getPredictedCount());
                break;
                
            case WARNING_HIGH_DENSITY:
                System.out.printf("[COMMAND ACTION - WARNING] Sector %s density is high (%d). Rerouting visitor traffic to adjacent zones.\n", 
                        report.getSectorId(), report.getPredictedCount());
                break;
                
            case SAFE:
                // Normal background execution loop telemetry logged
                break;
                
            default:
                System.out.println("[INFO] Sector monitoring normal. Data stored safely.");
                break;
        }
    }
}
