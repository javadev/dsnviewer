package com.github.dsnviewer.model;

public class Pin {
  private int direction, elec, length, number_pos, part, show, which;
  private String number;
  private String str;
  private Pos pos;
  public Pin() {
  }
  public String getNumber() {
    return number;
  }
  public Pos getPos() {
    return pos;
  }
  public int getDirection() {
    return direction;
  }
  public int getLength() {
    return length;
  }
  public int getWhich() {
    return which;
  }
  public void setWhich(int which) {
    this.which = which;
  }
  public void setDirection(String s) {
    this.direction = Integer.parseInt(s);
  }
  public void setElec(String s) {
    this.elec = Integer.parseInt(s);
  }
  public void setLength(String s) {
    this.length = Integer.parseInt(s);
  }
  public void setNumber(String number) {
    this.number = number;
  }
  public void setNumber_pos(String s) {
    this.number_pos = Integer.parseInt(s);
  }
  public void setPart(String s) {
    this.part = Integer.parseInt(s);
  }
  public void setPos(String s) {
    this.pos = new Pos(s);
  }
  public void setShow(String s) {
    this.show = Integer.parseInt(s);
  }
  public int getShow() {
    return show;
  }
  public void setWhich(String s) {
    this.which = Integer.parseInt(s);
  }
  public void setStr(String s) {
    str = s;
  }
  public String getStr() {
    return str;
  }
}
