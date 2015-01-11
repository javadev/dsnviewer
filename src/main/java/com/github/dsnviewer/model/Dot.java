package com.github.dsnviewer.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dot {
  private float x;
  private float y;
  public Dot() {
    x = y = 0;
  }
  public Dot(float x, float y) {
    this.x = x;
    this.y = y;
  }
  public Dot(String str) {
    Pattern compiledRegex;
    Matcher regexMatcher;
    compiledRegex = Pattern.compile("(.*),(.*)");
            //"([-+]?(\\d+(\\.\\d*)?|\\d*\\.\\d+)([eE][-+]?\\d+)?),([-+]?(\\d+(\\.\\d*)?|\\d*\\.\\d+)([eE][-+]?\\d+)?)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    regexMatcher = compiledRegex.matcher(str);
    if (regexMatcher.find()) {
      x = Float.parseFloat(regexMatcher.group(1));
      y = Float.parseFloat(regexMatcher.group(2));
      if (ModelDSN.debug >= 2) System.out.println("1 - "+regexMatcher.group(1)+ " 2 - "+regexMatcher.group(2));
    }
  }
  public float getX() {
    return x;
  }
  public float getY() {
    return y;
  }
  public String toString() {
    return " x - "+getX()+" y - "+getY();
  }
  public int compare(Dot dd) {
    return Math.round((dd.getX() - getX())*(dd.getY() - getY()));
  }
  public int compare(Dot dd1, Dot dd2) {
    return Math.round((dd1.getX()+dd2.getX() - getX())*(dd1.getY()+dd2.getY() - getY()));
  }
}
