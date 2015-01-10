package com.pwr;

import ilog.concert.IloException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, IloException {

        Path inputFile = Paths.get("input.txt");
        Path outputFile = Paths.get("output.txt");
        List<SimulationProperty> properties = Utils.readPropertiesFromFile(inputFile);

        Simulation simulation;

        for (SimulationProperty simConfig : properties) {
            simulation = new Simulation(simConfig);
            simConfig.setAveragedObjectiveValue(simulation.run());
        }

        Utils.storeSimulationResults(outputFile, properties);
    }
}
