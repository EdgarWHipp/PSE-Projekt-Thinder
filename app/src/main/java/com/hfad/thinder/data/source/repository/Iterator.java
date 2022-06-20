package com.hfad.thinder.data.source.repository;

public class Iterator {
    private static Iterator INSTANCE;
    private int counter;

    private Iterator() {

    }

    public static Iterator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Iterator();
        }
        return INSTANCE;
    }

    public int getIdCounter() {
        return counter;
    }
}
