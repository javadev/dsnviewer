package com.github.dsnviewer.model;

public class Grid {
  private int spacing = 1, snap = 1;
  public int getSnap() {
    return snap;
  }
  public void setSnap(String s) {
    this.snap = Integer.parseInt(s);
  }
  public int getSpacing() {
    return spacing;
  }
  public void setSpacing(String s) {
    this.spacing = Integer.parseInt(s);
  }
}
