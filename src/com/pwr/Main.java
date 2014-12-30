package com.pwr;

import ilog.concert.IloException;
import ilog.cplex.IloCplex;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome. I'm your wizard in the dark lands of P2P networks...");
        System.out.println("State your problem, mortal ...");

        try {
            IloCplex cplex = new IloCplex();
            // create model and solve it
        } catch (IloException ex) {
            System.err.println("Ugh, I got hurt: " + ex);
        }

    }
}
