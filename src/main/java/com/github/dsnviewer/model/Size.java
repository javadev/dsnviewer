package com.github.dsnviewer.model;

public class Size extends Dot {
  private int width, height;
  public int getHeight() {
    return height;
  }
  public void setHeight(String s) {
    this.height = Integer.parseInt(s);
  }
  public int getWidth() {
    return width;
  }
  public void setWidth(String s) {
    this.width = Integer.parseInt(s);
  }
}
