package com.github.dsnviewer.model;

public class BusSlash {
  private Pos pos;
  private Integer direction;

  public void setPos(String s) {
    this.pos = new Pos(s);
  }

  public Pos getPos() {
    return pos;
  }

  public void setDirection(String direction) {
    this.direction = Integer.parseInt(direction);
  }

  public Integer getDirection() {
    return direction;
  }
}
