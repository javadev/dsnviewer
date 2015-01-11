package com.github.dsnviewer.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class Polygon {
  private int fill;
  private Pos dp;
  private int style;
  private List<Point> dpointv = new ArrayList<Point>();
  public Polygon() {
  }
  public Polygon(int fill, Pos dp, int style) {
    this.fill = fill;
    this.dp = dp;
    this.style = style;
  }
  public Polygon(String fill, String dp, String style) {
    this(Integer.parseInt(fill), new Pos(dp), Integer.parseInt(style));
  }
  public void setDp(String s) {
    this.dp = new Pos(s);
  }
  public void setFill(String s) {
    this.fill = Integer.parseInt(s);
  }
  public void setStyle(String s) {
    this.style = Integer.parseInt(s);
  }
  public List<Point> getDpointv() {
    return dpointv;
  }
  public Pos getDp() {
    return dp;
  }
  public void addPoint(Element el) {
 //|arc|=|0| |pos|=|28,0|
    NamedNodeMap nm = el.getAttributes();
    Point dpoint = new Point();
    for (int j = 0; j < nm.getLength(); j++) {
      String attrName = nm.item(j).getNodeName();
      if (attrName.equals("arc")) {
        dpoint.setArc(el.getAttribute(attrName));
      }
      if (attrName.equals("pos")) {
        dpoint.setPos(el.getAttribute(attrName));
      }
    }
  dpointv.add(dpoint);
  }
  public int getFill() {
    return fill;
  }
  public Pos getDsnPos() {
    return dp;
  }
  public int getStyle() {
    return style;
  }
}
