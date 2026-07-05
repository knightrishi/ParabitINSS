package com.mycompany.pbtinss.analytics;

/**
 * Immutable report payload delivered directly into the ParabitINSS architecture loop.
 */
public final class PredictionReport {
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