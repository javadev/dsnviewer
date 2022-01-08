package com.github.dsnviewer.model;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class Details {
    private Size dsize = new Size();
    private Guides dguid = new Guides();
    private String title, author, revision, docnumber, organisation, sheets, shows, date;
    private Grid dgrid = new Grid();

    public Size getDsize() {
        return dsize;
    }

    public void setSize(Element el) {
        // |height|=|1050| |width|=|1485|
        NamedNodeMap nm = el.getAttributes();
        dsize = new Size();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("height")) {
                dsize.setHeight(el.getAttribute(attrName));
            } else if (attrName.equals("width")) {
                dsize.setWidth(el.getAttribute(attrName));
            }
        }
    }

    public void setGuides(Element el) {
        // |horiz|=|7| |vert|=|5|
        NamedNodeMap nm = el.getAttributes();
        dguid = new Guides();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("horiz")) {
                dguid.setHoriz(el.getAttribute(attrName));
            } else if (attrName.equals("vert")) {
                dguid.setVert(el.getAttribute(attrName));
            }
        }
    }

    public void setGrid(Element el) {
        // |snap|=|1| |spacing|=|100|
        NamedNodeMap nm = el.getAttributes();
        dgrid = new Grid();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("snap")) {
                dgrid.setSnap(el.getAttribute(attrName));
            } else if (attrName.equals("spacing")) {
                dgrid.setSpacing(el.getAttribute(attrName));
            }
        }
    }

    public Grid getDgrid() {
        return dgrid;
    }

    public Guides getDguid() {
        return dguid;
    }
}
