package com.pwr;

import ilog.concert.IloException;

public class Main {

    public static void main(String[] args) {

        Simulation simulation = new Simulation();

        try {
            System.out.println("Calculated objective value: " + simulation.run());
        } catch (IloException e) {
            System.out.println("Something went wrong... " + e);
        }

    }
}
