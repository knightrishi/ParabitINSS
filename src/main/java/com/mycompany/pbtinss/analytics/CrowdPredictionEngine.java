package com.mycompany.pbtinss.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pure Java SE Crowd Prediction Engine.
 * Tailored for centralized command server deployments.
 * Processes raw wireless telemetry from static hardware nodes using RF Variance Analytics.
 */
public class CrowdPredictionEngine {

    private final String sectorId;
    
    // Tracks a rolling history of RSSI values per fixed sensor node: Node ID -> List of RSSI values
    private final Map<String, List<Integer>> signalHistoryMap;
    private final int windowSize;

    // Environmental Constants calibrated for dense outdoor/indoor events
    private static final double EMPTY_ROOM_VARIANCE_THRESHOLD = 1.5; // Smooth baseline signal
    private static final double VARIANCE_PER_PERSON_FACTOR = 0.85;  // Statistical scaling multiplier

    /**
     * @param sectorId   The deployment sector this engine instance is analyzing.
     * @param windowSize The number of rolling telemetry packets to hold for variance calculations.
     */
    public CrowdPredictionEngine(String sectorId, int windowSize) {
        this.sectorId = sectorId;
        this.windowSize = windowSize;
        this.signalHistoryMap = new HashMap<>();
    }

    /**
     * Ingests a raw signal reading sent from a localized physical sensor node.
     * @param nodeId        The ID of the fixed router/node (e.g., "NODE_SEC4_ALPHA")
     * @param observedRssi  The raw RSSI value captured from environmental packets (e.g., -72)
     */
    public synchronized void ingestTelemetry(String nodeId, int observedRssi) {
        signalHistoryMap.putIfAbsent(nodeId, new ArrayList<>());
        List<Integer> history = signalHistoryMap.get(nodeId);

        history.add(observedRssi);

        // Evict oldest metrics to maintain a strict sliding window size
        if (history.size() > windowSize) {
            history.remove(0);
        }
    }

    /**
     * Mathematical Core: Computes Statistical Variance (σ²) of the RF signal environment.
     * Highly volatile signals mean dense, moving human bodies are scattering the wave.
     */
    private double calculateVariance(List<Integer> history) {
        if (history == null || history.size() < 2) {
            return 0.0;
        }

        // 1. Find the Mean (μ)
        double sum = 0.0;
        for (int value : history) {
            sum += value;
        }
        double mean = sum / history.size();

        // 2. Find sum of squared differences from the mean
        double squaredDifferencesSum = 0.0;
        for (int value : history) {
            squaredDifferencesSum += Math.pow(value - mean, 2);
        }

        // 3. Return Variance (σ²)
        return squaredDifferencesSum / (history.size() - 1);
    }

    /**
     * Compiles data across all operational nodes in the sector to output the crowd prediction.
     */
    public synchronized PredictionReport generatePrediction() {
        int totalNodesEvaluated = 0;
        double combinedVariance = 0.0;

        for (Map.Entry<String, List<Integer>> entry : signalHistoryMap.entrySet()) {
            List<Integer> history = entry.getValue();
            if (history.size() >= windowSize / 2) { // Ensure the node has sufficient window data
                combinedVariance += calculateVariance(history);
                totalNodesEvaluated++;
            }
        }

        int predictedHeadcount = 0;
        float confidenceScore = 0.0f;

        if (totalNodesEvaluated > 0) {
            double averageVariance = combinedVariance / totalNodesEvaluated;
            
            // If variance is above baseline noise floor, calculate crowd density
            if (averageVariance > EMPTY_ROOM_VARIANCE_THRESHOLD) {
                double deltaVariance = averageVariance - EMPTY_ROOM_VARIANCE_THRESHOLD;
                predictedHeadcount = (int) (deltaVariance / VARIANCE_PER_PERSON_FACTOR);
            }
            
            // Confidence scales with how many active nodes are feeding telemetry into the window
            confidenceScore = Math.min(1.0f, (float) totalNodesEvaluated / 3.0f); 
        }

        return new PredictionReport(this.sectorId, predictedHeadcount, confidenceScore);
    }

    // --- Clean Architecture Output Payload ---
    public static final class PredictionReport {
        private final String sectorId;
        private final int predictedCount;
        private final float confidence;
        private final long timestamp;

        public PredictionReport(String sectorId, int predictedCount, float confidence) {
            this.sectorId = sectorId;
            this.predictedCount = predictedCount;
            this.confidence = confidence;
            this.timestamp = System.currentTimeMillis();
        }

        public String getSectorId() { return sectorId; }
        public int getPredictedCount() { return predictedCount; }
        public float getConfidence() { return confidence; }
        public long getTimestamp() { return timestamp; }
    }
}