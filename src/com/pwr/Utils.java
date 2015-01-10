package com.pwr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private static final long SEED = System.currentTimeMillis();
    private static Random random = new Random(SEED);

    public static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * File has the following format:
     * nodes_number;blocks_to_transfer;max_time;large_number
     *
     * Example:
     * 5;1;5;9
     * 5;2;5;9
     */
    public static List<SimulationProperty> readPropertiesFromFile(Path inputFile) throws IOException {
        List<SimulationProperty> properties = new ArrayList<>();

        Files
                .lines(inputFile)
                .forEach(line -> {
                    String params[] = line.split(";");

                    SimulationProperty property = new SimulationProperty(
                            Integer.valueOf(params[0]),
                            Integer.valueOf(params[1]),
                            Integer.valueOf(params[2]),
                            Integer.valueOf(params[3]));

                    properties.add(property);
                });

        return properties;
    }

    /**
     * File has the following format:
     * nodes_number;blocks_to_transfer;max_time;large_number;averaged_result
     *
     * Example:
     * 5;1;5;9;6.7
     * 5;2;5;9;11.8
     */
    public static void storeSimulationResults(Path outputFile, List<SimulationProperty> results) throws IOException {
        StringBuilder content = new StringBuilder();

        results.stream().forEach(property -> {
            content.append(property.getNodes());
            content.append(";");
            content.append(property.getBlocksToTransfer());
            content.append(";");
            content.append(property.getMaxTime());
            content.append(";");
            content.append(property.getM());
            content.append(";");
            content.append(property.getAveragedObjectiveValue());
            content.append(System.getProperty("line.separator"));
        });

        Files.write(outputFile, content.toString().getBytes(), StandardOpenOption.CREATE);
    }
}
