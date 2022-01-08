package com.github.dsnviewer.model;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TinyCadSymbol {
    private List<Rectangle> rectangles = new ArrayList<Rectangle>();
    private List<Polygon> polygons = new ArrayList<Polygon>();
    private List<Text> texts = new ArrayList<Text>();
    private List<Label> labels = new ArrayList<Label>();
    private List<Pin> pins = new ArrayList<Pin>();
    private List<Ellipse> ellipses = new ArrayList<Ellipse>();

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public List<Pin> getPins() {
        return pins;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public List<Ellipse> getEllipses() {
        return ellipses;
    }

    public void addRectangle(Element el) {
        // |a|=|-32,-54| |b|=|-4,-1| |fill|=|0| |style|=|3|
        NamedNodeMap nm = el.getAttributes();
        Rectangle drec = new Rectangle();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("a")) {
                drec.setA(el.getAttribute(attrName));
            }
            if (attrName.equals("b")) {
                drec.setB(el.getAttribute(attrName));
            }
            if (attrName.equals("fill")) {
                drec.setFill(el.getAttribute(attrName));
            }
            if (attrName.equals("style")) {
                drec.setStyle(el.getAttribute(attrName));
            }
        }
        rectangles.add(drec);
    }

    public void addPolygon(Element el) {
        // |fill|=|0| |pos|=|-32,-42| |style|=|0|
        NamedNodeMap nm = el.getAttributes();
        Polygon dpol = new Polygon();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("fill")) {
                dpol.setFill(el.getAttribute(attrName));
            } else if (attrName.equals("pos")) {
                dpol.setDp(el.getAttribute(attrName));
            } else if (attrName.equals("style")) {
                dpol.setStyle(el.getAttribute(attrName));
            }
        }
        NodeList nl = el.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                el = (Element) n;
                if (el.getNodeName().equals("POINT")) {
                    dpol.addPoint(el);
                }
            }
        }
        polygons.add(dpol);
    }

    public void addText(Element el) {
        // |color|=|000000| |direction|=|3| |font|=|3| |pos|=|-31,-47|
        NamedNodeMap nm = el.getAttributes();
        Text dtext = new Text();
        dtext.setStr(el.getTextContent());
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("color")) {
                dtext.setColor(el.getAttribute(attrName));
            } else if (attrName.equals("direction")) {
                dtext.setDirection(el.getAttribute(attrName));
            } else if (attrName.equals("font")) {
                dtext.setFont(el.getAttribute(attrName));
            } else if (attrName.equals("pos")) {
                dtext.setPos(el.getAttribute(attrName));
            }
        }
        texts.add(dtext);
    }

    public void addLabel(Element el) {
        //  |color|=|000000| |direction|=|3| |font|=|3| |pos|=|-31,-47|
        NamedNodeMap nm = el.getAttributes();
        Label dlabel = new Label();
        dlabel.setStr(el.getTextContent());
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("color")) {
                dlabel.setColor(el.getAttribute(attrName));
            } else if (attrName.equals("direction")) {
                dlabel.setDirection(el.getAttribute(attrName));
            } else if (attrName.equals("font")) {
                dlabel.setFont(el.getAttribute(attrName));
            } else if (attrName.equals("pos")) {
                dlabel.setPos(el.getAttribute(attrName));
            }
        }
        labels.add(dlabel);
    }

    public void addPin(Element el) {
        // |direction|=|2| |elec|=|1| |length|=|20| |number|=|y1| |number_pos|=|0| |part|=|0|
        // |pos|=|0,-34| |show|=|2| |which|=|0|
        NamedNodeMap nm = el.getAttributes();
        Pin dpin = new Pin();
        dpin.setStr(el.getTextContent());
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (ModelDSN.debug >= 2)
                System.out.print(" |" + attrName + "|=|" + el.getAttribute(attrName) + "|");
            if (attrName.equals("direction")) {
                dpin.setDirection(el.getAttribute(attrName));
            } else if (attrName.equals("elec")) {
                dpin.setElec(el.getAttribute(attrName));
            } else if (attrName.equals("length")) {
                dpin.setLength(el.getAttribute(attrName));
            } else if (attrName.equals("number")) {
                dpin.setNumber(el.getAttribute(attrName));
            } else if (attrName.equals("number_pos")) {
                dpin.setNumber_pos(el.getAttribute(attrName));
            } else if (attrName.equals("part")) {
                dpin.setPart(el.getAttribute(attrName));
            } else if (attrName.equals("pos")) {
                dpin.setPos(el.getAttribute(attrName));
            } else if (attrName.equals("show")) {
                dpin.setShow(el.getAttribute(attrName));
            } else if (attrName.equals("which")) {
                dpin.setWhich(el.getAttribute(attrName));
            }
        }
        pins.add(dpin);
    }

    public void addEllipse(Element el) {
        ////  a='-3,-4' b='-1,-6' style='6' fill='2'/>
        NamedNodeMap nm = el.getAttributes();
        Ellipse dell = new Ellipse();
        // dpin.setStr(el.getTextContent());
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (ModelDSN.debug >= 2)
                System.out.print(" |" + attrName + "|=|" + el.getAttribute(attrName) + "|");
            if (attrName.equals("a")) {
                dell.setA(el.getAttribute(attrName));
            } else if (attrName.equals("b")) {
                dell.setB(el.getAttribute(attrName));
            } else if (attrName.equals("style")) {
                dell.setStyle(el.getAttribute(attrName));
            } else if (attrName.equals("fill")) {
                dell.setFill(el.getAttribute(attrName));
            }
        }
        ellipses.add(dell);
    }
}
