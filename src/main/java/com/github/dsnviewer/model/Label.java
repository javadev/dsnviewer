package com.github.dsnviewer.model;

public class Label extends Text {
  private int style;
  public void setStyle(String s) {
    style = Integer.parseInt(s);
  }
  public int getStyle() {
    return style;
  }
}
