package com.example.animalproject;

public class Setup {
    private static final boolean IS_MULTI_THREAD = true;
    private static final int X = 10;
    private static final int Y = 5;
    final static int CORES = Runtime.getRuntime().availableProcessors();

    public static boolean isIS_MULTI_THREAD() {
        return IS_MULTI_THREAD;
    }

    public static int getX() {
        return X;
    }

    public static int getY() {
        return Y;
    }

    public static int getCORES() {
        return CORES;
    }


}
