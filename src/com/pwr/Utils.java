package com.pwr;

import java.util.Random;

public class Utils {

    private static final long SEED = 12345;
    private static Random random = new Random(SEED);

    public static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
