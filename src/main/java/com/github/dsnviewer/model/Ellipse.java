package com.github.dsnviewer.model;

public class Ellipse {
    private Dot a;
    private Dot b;
    private int fill, style;

    public Ellipse() {}

    public Ellipse(Dot a, Dot b, int fill, int style) {
        this.a = a;
        this.b = b;
        this.fill = fill;
        this.style = style;
    }

    public Dot getA() {
        return a;
    }

    public void setA(String s) {
        a = new Dot(s);
    }

    public Dot getB() {
        return b;
    }

    public void setB(String s) {
        b = new Dot(s);
    }

    public void setFill(String s) {
        fill = Integer.parseInt(s);
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(String s) {
        style = Integer.parseInt(s);
    }

    public Ellipse(String a, String b, String fill, String style) {
        this(new Dot(a), new Dot(b), Integer.parseInt(fill), Integer.parseInt(style));
    }
}
