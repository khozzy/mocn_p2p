package com.pwr;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloSemiContVar;
import ilog.cplex.IloCplex;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome. I'm your wizard in the dark lands of P2P networks...");
        System.out.println("State your problem, mortal ...");

        /**
         * Solving the following example
         *
         * Maximize:
         * x_1 + 2x_2 + 3x_3
         *
         * Subject to:
         * -x_1 + x_2 + x_3 <= 20
         * x_1 - 3x_2 + x_3 <= 30
         *
         * With bounds:
         * 0 <= x_1 <= 40
         * 0 <= x_2 <= +inf
         * 0 <= x_3 <= +inf
         */

        try {
            IloCplex cplex = new IloCplex();

            double[] lb = {0.0, 0.0, 0.0};
            double[] ub = {40.0, Double.MAX_VALUE, Double.MAX_VALUE};
            IloNumVar[] x = cplex.numVarArray(3, lb, ub);

            double[] objvals = {1.0, 2.0, 3.0};
            cplex.addMaximize(cplex.scalProd(x, objvals));

            cplex.addLe(cplex.sum(
                    cplex.prod(-1.0, x[0]),
                    cplex.prod(1.0, x[1]),
                    cplex.prod(1.0, x[2])), 20.0);

            cplex.addLe(cplex.sum(
                    cplex.prod(1.0, x[0]),
                    cplex.prod(-3.0, x[1]),
                    cplex.prod(1.0, x[2])), 30.0);

            if (cplex.solve()) {
                cplex.output().println("Solution status: " + cplex.getStatus());
                cplex.output().println("Solution value: " + cplex.getObjValue());

                double[] val = cplex.getValues(x);
                int ncol = cplex.getNcols();

                for (int j = 0; j < ncol; ++j) {
                    cplex.output().println("Column: " + j + " value: " + val[j]);
                }

                cplex.end();
            }

        } catch (IloException ex) {
            System.err.println("Ugh, I got hurt: " + ex);
        }

    }
}
