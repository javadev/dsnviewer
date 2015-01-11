package com.github.dsnviewer.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Module {
  private Dot a = new Dot();
  private Dot b = new Dot();
  private String str = null;
  private List<Field2> allFields = new ArrayList<Field2>();

  public Module() {
  }
  public Module(Dot a, Dot b) {
    this.a = a;
    this.b = b;
  }
  public Module(Dot a, Dot b, String str, List<Field2> visibleFields) {
    this.a = a;
    this.b = b;
    this.str = str;
    this.allFields = visibleFields; 
  }
  public Dot getA() {
    return a;
  }
  public void setA(Dot a) {
    this.a = a;
  }
  public Dot getB() {
    return b;
  }
  public void setB(Dot b) {
    this.b = b;
  }
  public void setB(String s) {
    b = new Dot(s);
  }
  public String getStr() {
    return str;
  }
  public void setStr(String s) {
    str = s;
  }
  public List<Field2> getAllFields() {
    return allFields;
  }
  public void paintBorders(Graphics g, float alignxy) {
    // нарисовать рамки вокруг объектов
    Color savecol = g.getColor();
    g.setColor(new Color(0x939393));
    g.drawRect(
        Math.round((getA().getX() - 1) * alignxy),
        Math.round((getA().getY() - 1) * alignxy),
        Math.round((getB().getX() + 1) * alignxy) - Math.round((getA().getX() - 1) * alignxy),
        Math.round((getB().getY() + 1) * alignxy) - Math.round((getA().getY() - 1) * alignxy)
      );
    g.setColor(savecol);
  }

}
