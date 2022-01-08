package com.github.dsnviewer.model;

public class Text {
    private String str;
    private int color, direction, font;
    private Pos dp;

    public Text() {}

    public int getFont() {
        return font;
    }

    public int getColor() {
        return color;
    }

    public void setStr(String s) {
        str = s;
    }

    public void setColor(String s) {
        color = Integer.parseInt(s, 16);
        color = ((color & 0xFF) << 16) | (color & 0xFF00) | ((color & 0xFF0000) >> 16);
    }

    public void setDirection(String s) {
        direction = Integer.parseInt(s);
    }

    public void setFont(String s) {
        font = Integer.parseInt(s);
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
