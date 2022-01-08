package com.github.dsnviewer.model;

public class Grid {
    private int snap = 1;
    private float spacing = 1;

    public int getSnap() {
        return snap;
    }

    public void setSnap(String s) {
        this.snap = Integer.parseInt(s);
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(String s) {
        this.spacing = Float.valueOf(s);
    }
}
