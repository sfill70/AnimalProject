package com.example.animalproject;

public class Setup {
    private static final boolean IS_MULTI_THREAD = true;
    private static final int X = 40;
    private static final int Y = 20;
    private final static int CORES = Runtime.getRuntime().availableProcessors();
    private final static int TIME_OUT = 4000;

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

    public static int getTIME_OUT(){
        return TIME_OUT;
    }




}
