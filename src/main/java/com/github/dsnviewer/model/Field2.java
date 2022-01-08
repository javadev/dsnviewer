package com.github.dsnviewer.model;

import org.w3c.dom.Element;

public class Field2 {
    private String description;
    private Pos pos;
    private int show, type;
    private String value;
    private Element element;

    public Field2(Element element) {
        super();
        this.element = element;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(String s) {
        this.pos = new Pos(s);
    }

    public int getShow() {
        return show;
    }

    public void setShow(String s) {
        this.show = Integer.parseInt(s);
    }

    public void setType(String s) {
        this.type = Integer.parseInt(s);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Element getElement() {
        return element;
    }

    public String toString() {
        return description + " = " + value;
    }
}
