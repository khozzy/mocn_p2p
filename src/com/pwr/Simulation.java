package com.pwr;

import ilog.concert.IloException;
import ilog.concert.IloIntExpr;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class Simulation {

    public double run() throws IloException {

        final int NODES = 5;
        final int BLOCKS_TO_TRANSFER = 1;
        final int MAX_TIME = 2;
        final int M = 6;

        double objValue = 0.0;
        final double[][][][] solution = new double[BLOCKS_TO_TRANSFER][NODES][NODES][MAX_TIME];

        //g_bv - czy node posiada blok przed startem czy nie
        boolean[][] g = new boolean[BLOCKS_TO_TRANSFER][NODES];
        //w naszym wypadku każdy blok ma jednego initial seeda
        int initNode[] = new int[BLOCKS_TO_TRANSFER];
        for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
            initNode[b] = Utils.randInt(0, NODES - 1);
            g[b][initNode[b]] = true;
        }

        IloCplex cplex = new IloCplex();

        Network network = new Network(NODES);

        //y_bwvt variable
        IloNumVar[][][][] y = new IloNumVar[BLOCKS_TO_TRANSFER][NODES][NODES][MAX_TIME];
        for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
            for (int w = 0; w < NODES; w++) {
                for (int v = 0; v < NODES; v++) {
                    y[b][w][v] = cplex.boolVarArray(MAX_TIME);
                }
            }
        }

        //Objective function
        IloLinearNumExpr objectiveFunction = cplex.linearNumExpr();
        for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
            for (int w = 0; w < NODES; w++) {
                for (int v = 0; v < NODES; v++) {
                    for (int t = 0; t < MAX_TIME; t++) {
                        //suma iloczynów kosztu transferu z node w do v i zmiennej y okreslajacej czy
                        //w danej iteracji byl transfer danego bloku z node w do v czy nie
                        objectiveFunction.addTerm(network.getCost(w, v), y[b][w][v][t]);
                    }
                }
            }
        }
        cplex.addMinimize(objectiveFunction);

        //warunek 1 - wszystkie node'y muszą otrzymać pakiet - chyba dobrze
        for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
            for (int v = 0; v < NODES; v++) {
                IloLinearNumExpr sumOfY_bwvtOver_wt = cplex.linearNumExpr();
                for (int w = 0; w < NODES; w++) {
                    for (int t = 0; t < MAX_TIME; t++) {
                        sumOfY_bwvtOver_wt.addTerm(1, y[b][w][v][t]);
                    }
                }
                int myInt = (g[b][v]) ? 1 : 0;
                IloIntExpr g_bv = cplex.constant(myInt);
                cplex.addEq(cplex.sum(sumOfY_bwvtOver_wt, g_bv), 1);
            }
        }


        //warunek 2 - upload constraint, chyba dobziu
        for (int w = 0; w < NODES; w++) {
            for (int t = 0; t < MAX_TIME; t++) {
                IloLinearNumExpr sumOfY_bwvtOver_bv = cplex.linearNumExpr();
                for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
                    for (int v = 0; v < NODES; v++) {
                        sumOfY_bwvtOver_bv.addTerm(1, y[b][w][v][t]);
                    }
                }
                cplex.addLe(sumOfY_bwvtOver_bv, network.getUploadRate(w));
            }
        }

        //warunek 3 - download constraint
        for (int v = 0; v < NODES; v++) {
            for (int t = 0; t < MAX_TIME; t++) {
                IloLinearNumExpr sumOfY_bwvtOver_bw = cplex.linearNumExpr();
                for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
                    for (int w = 0; w < NODES; w++) {
                        sumOfY_bwvtOver_bw.addTerm(1, y[b][w][v][t]);
                    }
                }
                cplex.addLe(sumOfY_bwvtOver_bw, network.getDownloadRate(v));
            }
        }

        //warunek 4 - blok moze zostac wyslany tylko jesli node go posiada
        for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
            for (int w = 0; w < NODES; w++) {
                for (int t = 0; t < MAX_TIME; t++) {
                    IloLinearNumExpr sumOfY_bwvtOver_v = cplex.linearNumExpr();
                    for (int v = 0; v < NODES; v++) {
                        sumOfY_bwvtOver_v.addTerm(1, y[b][w][v][t]);
                    }

                    IloLinearNumExpr sumOfY_bswiOver_is = cplex.linearNumExpr();

                    for (int i = 0; i < t; i++) {
                        for (int s = 0; s < NODES; s++) {
                            sumOfY_bswiOver_is.addTerm(1, y[b][s][w][i]);
                        }
                    }

                    int myInt = (g[b][w]) ? 1 : 0;
                    IloIntExpr g_bw = cplex.constant(myInt);

                    cplex.addLe(sumOfY_bwvtOver_v, cplex.prod(M, cplex.sum(sumOfY_bswiOver_is, g_bw)));

                }
            }
        }

        //no i rozwiązujemy
        if (cplex.solve()) {

            objValue = cplex.getObjValue();

            cplex.output().println("Solution status: " + cplex.getStatus());
            cplex.output().println("Solution value: " + objValue);


            for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
                for (int w = 0; w < NODES; w++) {
                    for (int v = 0; v < NODES; v++) {
                        solution[b][w][v] = cplex.getValues(y[b][w][v]);
                    }
                }
            }

            network.displayNodeInfo();
            for (int b = 0; b < BLOCKS_TO_TRANSFER; b++) {
                cplex.output().println("Block=" + b + " initially in node: " + initNode[b]);
                for (int t = 0; t < MAX_TIME; t++) {
                    for (int w = 0; w < NODES; w++) {
                        for (int v = 0; v < NODES; v++) {
                            if (Math.round(solution[b][w][v][t]) == 1) {
                                cplex.output().println("Block=" + b + " in iteration=" + t + " transferred from node w="
                                        + w + " to node v=" + v);
                            }
                        }
                    }
                }
            }

            cplex.end();
        }

        return objValue;
    }

}
