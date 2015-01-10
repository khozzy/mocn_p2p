package com.pwr;

import java.util.Collections;
import java.util.Random;
import java.util.Set;

public class Utils {

    private static final long SEED = System.currentTimeMillis();
    private static Random random = new Random(SEED);

    public static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static Set<SimulationProperty> readPropertiesFromFile() {
        return Collections.emptySet();
    }
}
