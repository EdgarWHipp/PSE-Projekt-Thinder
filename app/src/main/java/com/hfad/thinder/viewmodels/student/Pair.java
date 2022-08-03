package com.hfad.thinder.viewmodels.student;

public class Pair<X,Y> {//Todo ins Model verschieben
    private X key;
    private Y value;
    public Pair(X key, Y value) {
        this.key = key;
        this.value = value;
    }

    public X getKey() {
        return key;
    }

    public void setKey(X key) {
        this.key = key;
    }

    public Y getValue() {
        return value;
    }

    public void setValue(Y value) {
        this.value = value;
    }
}
