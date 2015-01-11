package com.github.dsnviewer.model;

public class Wire {
  private Dot a;
  private Dot b;
  public Wire() {
  }
  public Wire(Dot a, Dot b) {
    this.a = a;
    this.b = b;
  }
  public Dot getA() {
    return a;
  }
  public Dot getB() {
    return b;
  }
  public void setA(String s) {
    a = new Dot(s);
  }
  public void setB(String s) {
    b = new Dot(s);
  }
}
