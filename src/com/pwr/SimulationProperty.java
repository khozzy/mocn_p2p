package com.pwr;

public class SimulationProperty {

    private final int nodes;
    private final int blocksToTransfer;
    private final int maxTime;
    private final int m;

    public SimulationProperty(int nodes, int blocksToTransfer, int maxTime, int m) {
        this.nodes = nodes;
        this.blocksToTransfer = blocksToTransfer;
        this.maxTime = maxTime;
        this.m = m;
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
}
