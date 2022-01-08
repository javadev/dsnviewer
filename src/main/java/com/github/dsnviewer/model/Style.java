package com.github.dsnviewer.model;

public class Style {
    private int id, style, color, thickness;

    public void setStyle(String s) {
        style = Integer.parseInt(s);
    }

    public int getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public int getThickness() {
        return thickness;
    }

    public void setId(String s) {
        this.id = Integer.parseInt(s);
    }

    public void setColor(String c) {
        color = Integer.parseInt(c, 16);
        color = ((color & 0xFF) << 16) | (color & 0xFF00) | ((color & 0xFF0000) >> 16);
    }

    public void setThickness(String t) {
        thickness = Integer.parseInt(t);
    }
}
