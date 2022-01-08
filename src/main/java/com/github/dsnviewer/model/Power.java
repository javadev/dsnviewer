package com.github.dsnviewer.model;

public class Power {
    private String str;
    private int direction, which;
    private Pos dp;

    public Power() {}

    public void setStr(String s) {
        str = s;
    }

    public void setDirection(String s) {
        direction = Integer.parseInt(s);
    }

    public int getWhich() {
        return which;
    }

    public void setWhich(String s) {
        which = Integer.parseInt(s);
    }

    public void setPos(String s) {
        dp = new Pos(s);
    }

    public String getStr() {
        return str;
    }

    public Pos getDp() {
        return dp;
    }
}
