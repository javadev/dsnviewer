package com.github.dsnviewer.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class Symbol {
  private int id;
  private Pos pos;
  private int part, rotate, can_scale;
  private float scale_x, scale_y;
  private List<Field2> field2s = new ArrayList<Field2>();

  public int getId() {
    return id;
  }
  public Pos getPos() {
    return pos;
  }
  public List<Field2> getField2s() {
    return field2s;
  }
  public void setCanScale(String s) {
    can_scale = Integer.parseInt(s);
  }            
  public void setId(String s) {
    id = Integer.parseInt(s);
  }            
  public void setPart(String s) {
    can_scale = Integer.parseInt(s);
  }            
  public void setPos(String s) {
    pos = new Pos(s);
  }            
  public void setRotate(String s) {
    rotate = Integer.parseInt(s);
  }            
  public void setScalex(String s) {
    scale_x = Float.parseFloat(s);
  }
  
  public float getScale_x() {
        return scale_x;
    }
    public void setScale_x(float scale_x) {
        this.scale_x = scale_x;
    }
    
    public float getScale_y() {
        return scale_y;
    }
    public void setScale_y(float scale_y) {
        this.scale_y = scale_y;
    }
    public void setScaley(String s) {
    scale_y = Float.parseFloat(s);
  }
  public void addField(Element el) {
// |description|=|Ref| |pos|=|1,-49.6| |show|=|1| |type|=|0| |value|=|D01|
    NamedNodeMap nm = el.getAttributes();
    Field2 dfield2 = new Field2(el);
    //dpin.setStr(el.getTextContent());
    for (int j = 0; j < nm.getLength(); j++) {
      String attrName = nm.item(j).getNodeName();
      if (ModelDSN.debug >= 2) System.out.print(" |"+attrName+"|=|"+el.getAttribute(attrName)+"|");
      if (attrName.equals("description")) {
        dfield2.setDescription(el.getAttribute(attrName));
      } else
      if (attrName.equals("pos")) {
        dfield2.setPos(el.getAttribute(attrName));
      } else
      if (attrName.equals("show")) {
        dfield2.setShow(el.getAttribute(attrName));
      } else
      if (attrName.equals("type")) {
        dfield2.setType(el.getAttribute(attrName));
      } else
      if (attrName.equals("value")) {
        dfield2.setValue(el.getAttribute(attrName));
      }
    }
    field2s.add(dfield2);
  }
}
