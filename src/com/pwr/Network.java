package com.pwr;

public class Network {

    // Total network nodes
    private int V;

    // Maximum upload and download rate of each node
    private int[] uploadRate;
    private int[] downloadRate;

    // Cost of transferring a block from w -> v
    private double[][] cost;

    public Network(int nodes) {
        V = nodes;

        uploadRate = new int[nodes];
        downloadRate = new int[nodes];
        cost = new double[nodes][nodes];

        // Put random values as upload and download rates
        for (int i = 0; i < V; i++) {
            uploadRate[i] = Utils.randInt(1,5);
            downloadRate[i] = Utils.randInt(1,10);
        }

        // Put random values as cost of transfer
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                cost[i][j] = Utils.randInt(1,5);
            }
        }
    }

    public int getNodesNumber() {
        return V;
    }

    public int[] getUploadRates() {
        return uploadRate;
    }

    public int[] getDownloadRates() {
        return downloadRate;
    }

    public double[][] getCosts() {
        return cost;
    }

    public int getUploadRate(int node) {
        return uploadRate[node];
    }

    public int getDownloadRate(int node) {
        return downloadRate[node];
    }

    public double getCost(int node_W, int node_V) {
        return cost[node_W][node_V];
    }

    public void displayNodeInfo() {
        for (int node = 0; node < V; node++) {
            System.out.println(String.format("Node: %d\tUpload: %d\tDownload: %d",
                    node, getUploadRate(node), getDownloadRate(node)));
        }
    }
}
