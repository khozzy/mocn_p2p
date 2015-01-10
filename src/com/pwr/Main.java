package com.pwr;

import ilog.concert.IloException;

public class Main {

    public static void main(String[] args) {

        SimulationProperty property = new SimulationProperty(5, 2, 5, 9);
        Simulation simulation = new Simulation(property);

        try {
            System.out.println("Calculated objective value: " + simulation.run());
        } catch (IloException e) {
            System.out.println("Something went wrong... " + e);
        }

    }
}
