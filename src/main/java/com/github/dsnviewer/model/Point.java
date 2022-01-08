package com.github.dsnviewer.model;

public class Point {
    private int arc;
    private Pos dp;

    public Point() {}

    public Point(int arc, Pos dp) {
        this.arc = arc;
        this.dp = dp;
    }

    public Point(String arc, String dp) {
        this(Integer.parseInt(arc), new Pos(dp));
    }

    public Pos getDp() {
        return dp;
    }

    public int getArc() {
        return arc;
    }

    public void setArc(String s) {
        arc = Integer.parseInt(s);
    }

    public void setPos(String s) {
        dp = new Pos(s);
    }
}
