/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pbtinss;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages safety thresholds for venue sectors and tracks structural capacities.
 */
public class SectorCapacityRegistry {

    private final Map<String, Integer> sectorMaxCapacities = new HashMap<>();

    /**
     * Registers a sector capacity (e.g., Sector Alpha inside Circle 1 can hold 10,000 people).
     * @param sectorId
     * @param maxCapacity
     */
    public void registerSectorCapacity(String sectorId, int maxCapacity) {
        sectorMaxCapacities.put(sectorId, maxCapacity);
    }

    /**
     * Analyzes headcount against thresholds to determine safety risk status.
     * @param sectorId
     * @param currentEstimate
     */
    public RiskLevel evaluateSafetyRisk(String sectorId, int currentEstimate) {
        Integer maxCap = sectorMaxCapacities.get(sectorId);
        if (maxCap == null) return RiskLevel.UNKNOWN;

        double occupancyRate = (double) currentEstimate / maxCap;

        if (occupancyRate >= 0.95) return RiskLevel.CRITICAL_OVERCROWDING; // Trigger gate shutdown
        if (occupancyRate >= 0.80) return RiskLevel.WARNING_HIGH_DENSITY;  // Slow down entry traffic
        return RiskLevel.SAFE;
    }

    public enum RiskLevel {
        SAFE,
        WARNING_HIGH_DENSITY,
        CRITICAL_OVERCROWDING,
        UNKNOWN
    }
}