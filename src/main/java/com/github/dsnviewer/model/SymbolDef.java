package com.github.dsnviewer.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SymbolDef {
  private int id;
  private Name dname;
  private Ref dref;
  private String description;
  private int ppp;
  private Field dfield;
  private TinyCadSymbol tinyCadSymbol;

  public int getId() {
    return id;
  }
  public void setId(String s) {
    id = Integer.parseInt(s);
  }
  public TinyCadSymbol getTinyCadSymbol() {
    return tinyCadSymbol;
  }
  public void setName(String s) {
    dname = new Name(s);
  }
  public void setRef(String s) {
    dref = new Ref(s);
  }
  public void setDescription(String d) {
    description = d;
  }
  public void setPpp(String p) {
    ppp = Integer.parseInt(p);
  }
  public void setField(NodeList nl) {
      dfield = new Field();
      for (int i = 0; i < nl.getLength(); i++) {
        Node n = nl.item(i);
        if (n instanceof Element) {
          Element el = (Element) n;
          if (el.getNodeName().equals("NAME")) {
            dfield.setName(el.getTextContent());
          } else
          if (el.getNodeName().equals("DEFAULT")) {
            dfield.set_default(el.getTextContent());
          } else
          if (el.getNodeName().equals("FT")) {
            dfield.setFt(el.getTextContent());
          } else
          if (el.getNodeName().equals("VALUE")) {
            dfield.setValue(el.getTextContent());
          } else
          if (el.getNodeName().equals("POS")) {
            dfield.setPos(el.getTextContent());
          }
        }
      }
  }
  public void setTinycad(NodeList nl) {
      tinyCadSymbol = new TinyCadSymbol();
      for (int i = 0; i < nl.getLength(); i++) {
        Node n = nl.item(i);
        if (n instanceof Element) {
          Element el = (Element) n;
          if (el.getNodeName().equals("TEXT")) {
            tinyCadSymbol.addText(el);
          } else if (el.getNodeName().equals("LABEL")) {
            tinyCadSymbol.addLabel(el);
          } else if (el.getNodeName().equals("RECTANGLE")) {
            tinyCadSymbol.addRectangle(el);
          } else if (el.getNodeName().equals("POLYGON")) {
            tinyCadSymbol.addPolygon(el);
          } else if (el.getNodeName().equals("PIN")) {
            tinyCadSymbol.addPin(el);
          } else if (el.getNodeName().equals("ELLIPSE")) {
            tinyCadSymbol.addEllipse(el);
          }
        }
      }
  }
}
