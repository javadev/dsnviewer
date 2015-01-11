package com.github.dsnviewer.model;

public class Font {
  private int id, height, width, weigth; 
  private boolean italic, underline, strikeout;
  private int charset;
  private String facename;
  public int getId() {
    return id;
  }
  public void setId(String s) {
    this.id = Integer.parseInt(s);
  }
  public int getHeight() {
    return height;
  }
  public void setHeigth(String h) {
    height = Integer.parseInt(h);
  }
  public void setWidth(String w) {
    width = Integer.parseInt(w);
  }
  public void setWeight(String w) {
    weigth = Integer.parseInt(w);
  }
  public void setItalic(String i) {
    italic = Integer.parseInt(i) > 0;
  }
  public void setUnderline(String u) {
    underline = Integer.parseInt(u) > 0;
  }
  public void setStrikeout(String s) {
    strikeout = Integer.parseInt(s) > 0;
  }
  public void setCharset(String c) {
    charset = Integer.parseInt(c);
  }
  public void setFacename(String f) {
    facename = f;
  }
}
