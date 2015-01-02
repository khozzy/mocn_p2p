package com.pwr;

public class Simulation {

    private final int NODES = 10;
    private final int BLOCKS_TO_TRANSFER = 1;
    private final int MAX_TIME = 20;

    private Network network = new Network(NODES);

    // g_bv if block b is located on node v before transfer starts
    private boolean[][] nodeHasBlockBeforeTransfer = new boolean[BLOCKS_TO_TRANSFER][NODES];

    // y_bwvt block b transferred from w -> v in time t
    private boolean[][][][] blockTransferred = new boolean[BLOCKS_TO_TRANSFER][NODES][NODES][MAX_TIME];

    public void start() {
        wipeAllInformations();

        System.out.println("[*] Network details");
        network.displayNodeInfo();

        System.out.println("[*] One node has a block of data");
        int initNode = Utils.randInt(0, NODES - 1);
        nodeHasBlockBeforeTransfer[0][initNode] = true;

        System.out.println("[*] Node with number " + initNode + " chosen");
        System.out.println("[*] Beginning the simulation");

        for (int time = 0; time < MAX_TIME; time++) {
            System.out.println("\tt = " + time);
            // ...
        }

    }

    private void wipeAllInformations() {
        System.out.println("[*] Wiping g_bv");
        for (int block = 0; block < BLOCKS_TO_TRANSFER; block++) {
            for (int node = 0; node < NODES; node++) {
                nodeHasBlockBeforeTransfer[block][node] = false;
            }
        }

        System.out.println("[*] Wiping y_bwvt");
        for (int block = 0; block < BLOCKS_TO_TRANSFER; block++) {
            for (int node_W = 0; node_W < NODES; node_W++) {
                for (int node_V = 0; node_V < NODES; node_V++) {
                    for (int time = 0; time < MAX_TIME; time++) {
                        blockTransferred[block][node_W][node_V][time] = false;
                    }
                }
            }
        }
    }
}
