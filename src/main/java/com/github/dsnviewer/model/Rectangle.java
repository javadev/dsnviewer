package com.github.dsnviewer.model;

public class Rectangle {
    private Dot a = new Dot();
    private Dot b = new Dot();
    private int fill, style;

    public Rectangle() {}

    public Rectangle(Dot a, Dot b, int fill, int style) {
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

    public void setA(Dot a) {
        this.a = a;
    }

    public void setB(Dot b) {
        this.b = b;
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

    public void setStyle(String s) {
        style = Integer.parseInt(s);
    }

    public int getStyle() {
        return style;
    }

    public Rectangle(String a, String b, String fill, String style) {
        this(new Dot(a), new Dot(b), Integer.parseInt(fill), Integer.parseInt(style));
    }
}
