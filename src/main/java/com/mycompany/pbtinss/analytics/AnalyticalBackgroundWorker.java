package com.mycompany.pbtinss.analytics;

import com.mycompany.pbtinss.ParabitCrowdOrchestrator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Background worker thread pool that ensures crowd predictions and safety rule validations
 * execute systematically on a fixed clock cycle independent of manual UI clicks.
 */
public class AnalyticalBackgroundWorker {

    private final ScheduledExecutorService schedulerExecutor;
    private final ParabitCrowdOrchestrator orchestratorRef;
    private final String targetSectorId;
    private final long processingIntervalSeconds;
    private boolean isRunning;

    public AnalyticalBackgroundWorker(ParabitCrowdOrchestrator orchestrator, String sectorId, long intervalSeconds) {
        this.orchestratorRef = orchestrator;
        this.targetSectorId = sectorId;
        this.processingIntervalSeconds = intervalSeconds;
        this.schedulerExecutor = Executors.newSingleThreadScheduledExecutor();
        this.isRunning = false;
    }

    /**
     * Spins up an asynchronous daemon worker background loop.
     */
    public synchronized void startContinuousAnalysis() {
        if (isRunning) return;
        
        isRunning = true;
        System.out.printf("[BACKGROUND WORKER] Initializing analytical background loops for %s every %d seconds.\n", 
                targetSectorId, processingIntervalSeconds);

        // Schedule a fixed background cycle execution task
        schedulerExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    // Triggers mathematical variance consolidation, DB storage, and safety rules asynchronously
                    orchestratorRef.runAnalyticalUpdateCycle(targetSectorId);
                } catch (Exception ex) {
                    System.err.println("[WORKER CRITICAL EXCEPTION] Evaluation cycle faulted for sector: " + targetSectorId);
                    ex.printStackTrace();
                }
            }
        }, 1, processingIntervalSeconds, TimeUnit.SECONDS);
    }

    /**
     * Gracious system tear-down (Call when closing application layout contexts)
     */
    public synchronized void stopAnalysis() {
        if (!isRunning) return;
        
        System.out.printf("[BACKGROUND WORKER] Stopping execution threads for %s...\n", targetSectorId);
        schedulerExecutor.shutdown();
        isRunning = false;
    }
}