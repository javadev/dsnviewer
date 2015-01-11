package com.github.dsnviewer.model;

public class Guides {
  private int horiz = 1, vert = 1;
  public int getHoriz() {
    return horiz;
  }
  public void setHoriz(String s) {
    this.horiz = Integer.parseInt(s);
  }
  public int getVert() {
    return vert;
  }
  public void setVert(String s) {
    this.vert = Integer.parseInt(s);
  }
}
