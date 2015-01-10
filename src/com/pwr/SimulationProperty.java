package com.pwr;

public class SimulationProperty {

    private final String type; // either cost or time optimization
    private final int nodes;
    private final int blocksToTransfer;
    private final int maxTime;
    private final int m;

    private double averagedObjectiveValue; // averaged result of the simulation

    public SimulationProperty(String type, int nodes, int blocksToTransfer, int maxTime, int m) {
        this.type = type;
        this.nodes = nodes;
        this.blocksToTransfer = blocksToTransfer;
        this.maxTime = maxTime;
        this.m = m;
    }

    public String getType() {
        return type;
    }

    public int getNodes() {
        return nodes;
    }

    public int getBlocksToTransfer() {
        return blocksToTransfer;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getM() {
        return m;
    }

    public double getAveragedObjectiveValue() {
        return averagedObjectiveValue;
    }

    public void setAveragedObjectiveValue(double averagedObjectiveValue) {
        this.averagedObjectiveValue = averagedObjectiveValue;
    }
}
