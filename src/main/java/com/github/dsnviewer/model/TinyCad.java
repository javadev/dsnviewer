package com.github.dsnviewer.model;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TinyCad {
    private String name;
    private Details details = new Details();
    private List<Font> fonts = new ArrayList<Font>();
    private List<Style> styles = new ArrayList<Style>();
    private Fill fill;
    private List<SymbolDef> symbolDefs = new ArrayList<SymbolDef>();
    private Options options = new Options();
    private List<Symbol> symbols = new ArrayList<Symbol>();
    private List<Wire> wires = new ArrayList<Wire>();
    private List<Ellipse> ellipses = new ArrayList<Ellipse>();
    private List<Bus> buses = new ArrayList<Bus>();
    private List<Text> texts = new ArrayList<Text>();
    private List<Junction> junctions = new ArrayList<Junction>();
    private List<Power> powers = new ArrayList<Power>();
    private List<Label> labels = new ArrayList<Label>();
    private List<Polygon> polygons = new ArrayList<Polygon>();
    private List<Module> modules = new ArrayList<Module>();
    private List<BusSlash> busSlashes = new ArrayList<BusSlash>();
    private float alignxy = 5f;
    private Pos pos = new Pos();

    public void setName(String name) {
        this.name = name;
    }

    public List<Wire> getWires() {
        return wires;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Ellipse> getEllipses() {
        return ellipses;
    }

    public List<SymbolDef> getSymbolDefs() {
        return symbolDefs;
    }

    public float getAlignxy() {
        return alignxy;
    }

    public void setAlignxy(float alignxy) {
        this.alignxy = alignxy;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public Details getDetails() {
        return details;
    }

    public List<Font> getFonts() {
        return fonts;
    }

    public Options getOptions() {
        return options;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Junction> getJunctions() {
        return junctions;
    }

    public List<Power> getPowers() {
        return powers;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<BusSlash> getBusSlashes() {
        return busSlashes;
    }

    public String getName() {
        return name;
    }

    public void addBus(Element el) {
        // |a|=|66,20| |b|=|120,20|
        NamedNodeMap nm = el.getAttributes();
        Bus dbus = new Bus();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("a")) {
                dbus.setA(el.getAttribute(attrName));
            }
            if (attrName.equals("b")) {
                dbus.setB(el.getAttribute(attrName));
            }
        }
        buses.add(dbus);
    }

    public void addEllipse(Element el) {
        //  a='24,10' b='44,64' style='0' fill='0'
        NamedNodeMap nm = el.getAttributes();
        Ellipse dellipse = new Ellipse();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("a")) {
                dellipse.setA(el.getAttribute(attrName));
            } else if (attrName.equals("b")) {
                dellipse.setB(el.getAttribute(attrName));
            } else if (attrName.equals("style")) {
                dellipse.setStyle(el.getAttribute(attrName));
            } else if (attrName.equals("fill")) {
                dellipse.setFill(el.getAttribute(attrName));
            }
        }
        ellipses.add(dellipse);
    }

    public void addText(Element el) {
        // pos='128,52' direction='0' font='7' color='0080FF'
        NamedNodeMap nm = el.getAttributes();
        Text dtext = new Text();
        dtext.setStr(el.getTextContent());
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("pos")) {
                dtext.setPos(el.getAttribute(attrName));
            } else if (attrName.equals("direction")) {
                dtext.setDirection(el.getAttribute(attrName));
            } else if (attrName.equals("font")) {
                dtext.setFont(el.getAttribute(attrName));
            } else if (attrName.equals("color")) {
                dtext.setColor(el.getAttribute(attrName));
            }
        }
        texts.add(dtext);
    }

    public void addWire(Element el) {
        // |a|=|66,20| |b|=|120,20|
        NamedNodeMap nm = el.getAttributes();
        Wire dwire = new Wire();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("a")) {
                dwire.setA(el.getAttribute(attrName));
            }
            if (attrName.equals("b")) {
                dwire.setB(el.getAttribute(attrName));
            }
        }
        wires.add(dwire);
    }

    public void addJunction(Element el) {
        // |a|=|66,20| |b|=|120,20|
        NamedNodeMap nm = el.getAttributes();
        Junction djun = new Junction();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("pos")) {
                djun = new Junction(el.getAttribute(attrName));
            }
        }
        junctions.add(djun);
    }

    public void addPower(Element el) {
        //  <POWER pos='46,76' which='1' direction='1'>0v</POWER>
        NamedNodeMap nm = el.getAttributes();
        Power dpower = new Power();
        dpower.setStr(el.getTextContent());
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("pos")) {
                dpower.setPos(el.getAttribute(attrName));
            } else if (attrName.equals("direction")) {
                dpower.setDirection(el.getAttribute(attrName));
            } else if (attrName.equals("which")) {
                dpower.setWhich(el.getAttribute(attrName));
            }
        }
        powers.add(dpower);
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
            } else if (attrName.equals("style")) {
                dlabel.setStyle(el.getAttribute(attrName));
            } else if (attrName.equals("pos")) {
                dlabel.setPos(el.getAttribute(attrName));
            }
        }
        labels.add(dlabel);
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

    public void addSymbol(Element el) {
        // |can_scale|=|0| |id|=|1| |part|=|0| |pos|=|56,86| |rotate|=|0| |scale_x|=|1|
        // |scale_y|=|1|
        NamedNodeMap nm = el.getAttributes();
        Symbol dsymb = new Symbol();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("can_scale")) {
                dsymb.setCanScale(el.getAttribute(attrName));
            } else if (attrName.equals("id")) {
                dsymb.setId(el.getAttribute(attrName));
            } else if (attrName.equals("part")) {
                dsymb.setPart(el.getAttribute(attrName));
            } else if (attrName.equals("pos")) {
                dsymb.setPos(el.getAttribute(attrName));
            } else if (attrName.equals("rotate")) {
                dsymb.setRotate(el.getAttribute(attrName));
            } else if (attrName.equals("scale_x")) {
                dsymb.setScalex(el.getAttribute(attrName));
            } else if (attrName.equals("scale_y")) {
                dsymb.setScaley(el.getAttribute(attrName));
            }
        }
        NodeList nl = el.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                el = (Element) n;
                if (el.getNodeName().equals("FIELD")) {
                    dsymb.addField(el);
                }
            }
        }
        symbols.add(dsymb);
    }

    public void addBusSlash(Element el) {
        NamedNodeMap nm = el.getAttributes();
        BusSlash busSlash = new BusSlash();
        for (int j = 0; j < nm.getLength(); j++) {
            String attrName = nm.item(j).getNodeName();
            if (attrName.equals("pos")) {
                busSlash.setPos(el.getAttribute(attrName));
            } else if (attrName.equals("direction")) {
                busSlash.setDirection(el.getAttribute(attrName));
            }
        }
        busSlashes.add(busSlash);
    }

    public void setDetails(NodeList nl) {
        details = new Details();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                Element el = (Element) n;
                if (ModelDSN.debug >= 2)
                    System.out.println(
                            "!!!Element: ("
                                    + el.getChildNodes().getLength()
                                    + ") "
                                    + el.toString());
                if (el.getNodeName().equals("Size")) {
                    details.setSize(el);
                } else if (el.getNodeName().equals("GUIDES")) {
                    details.setGuides(el);
                } else if (el.getNodeName().equals("GRID")) {
                    details.setGrid(el);
                }
            }
        }
    }

    public void setOptions(NodeList nl) {
        options = new Options();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                Element el = (Element) n;
                if (el.getNodeName().equals("FONT")) {
                    options.setFont(el.getTextContent());
                }
                if (el.getNodeName().equals("STYLE")) {
                    options.setStyle(el.getTextContent());
                }
                if (el.getNodeName().equals("GRID")) {
                    options.setGrid(el.getTextContent());
                }
                if (el.getNodeName().equals("UNITS")) {
                    options.setUnits(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_WIRE")) {
                    options.setColorWire(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_BUS")) {
                    options.setColorbus(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_JUNCTION")) {
                    options.setColorJunction(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_NOCONNECT")) {
                    options.setColorNoconnect(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_LABEL")) {
                    options.setColorLabel(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_POWER")) {
                    options.setColorPower(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_PIN")) {
                    options.setColorPin(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR_HIDDEN_PIN")) {
                    options.setColorHiddenPin(el.getTextContent());
                }
            }
        }
    }

    public void addSymboldef(Element el) {
        SymbolDef symbolDef = new SymbolDef();
        if (el.hasAttributes()) {
            NamedNodeMap nm = el.getAttributes();
            for (int j = 0; j < nm.getLength(); j++) {
                String attrName = nm.item(j).getNodeName();
                if (attrName.equals("id")) {
                    symbolDef.setId(el.getAttribute(attrName));
                }
            }
        }
        NodeList nl = el.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                el = (Element) n;
                if (ModelDSN.debug >= 2)
                    System.out.println(
                            "!!!Element: ("
                                    + el.getChildNodes().getLength()
                                    + ") "
                                    + el.toString());
                if (el.getNodeName().equals("NAME")) {
                    symbolDef.setName(el.getTextContent());
                }
                if (el.getNodeName().equals("REF")) {
                    symbolDef.setRef(el.getTextContent());
                }
                if (el.getNodeName().equals("DESCRIPTION")) {
                    symbolDef.setDescription(el.getTextContent());
                }
                if (el.getNodeName().equals("PPP")) {
                    symbolDef.setPpp(el.getTextContent());
                }
            } else {
                if (ModelDSN.debug >= 2) System.out.println("|" + el.getNodeName() + "|");
                if (el.getNodeName().equals("FIELD")) {
                    symbolDef.setField(el.getChildNodes());
                }
                if (el.getNodeName().equals("TinyCAD")) {
                    symbolDef.setTinycad(el.getChildNodes());
                }
            }
        }
        symbolDefs.add(symbolDef);
    }

    public void addStyle(Element el) {
        Style dstyle = new Style();
        if (el.hasAttributes()) {
            NamedNodeMap nm = el.getAttributes();
            for (int j = 0; j < nm.getLength(); j++) {
                String attrName = nm.item(j).getNodeName();
                if (attrName.equals("id")) {
                    dstyle.setId(el.getAttribute(attrName));
                }
            }
        }
        NodeList nl = el.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                el = (Element) n;
                if (ModelDSN.debug >= 2)
                    System.out.println(
                            "!!!Element: ("
                                    + el.getChildNodes().getLength()
                                    + ") "
                                    + el.toString());
                if (el.getNodeName().equals("STYLE")) {
                    dstyle.setStyle(el.getTextContent());
                }
                if (el.getNodeName().equals("COLOR")) {
                    dstyle.setColor(el.getTextContent());
                }
                if (el.getNodeName().equals("THICKNESS")) {
                    dstyle.setThickness(el.getTextContent());
                }
            }
        }
        styles.add(dstyle);
    }

    public void addFont(Element el) {
        Font dfont = new Font();
        if (el.hasAttributes()) {
            NamedNodeMap nm = el.getAttributes();
            for (int j = 0; j < nm.getLength(); j++) {
                String attrName = nm.item(j).getNodeName();
                if (attrName.equals("id")) {
                    dfont.setId(el.getAttribute(attrName));
                }
            }
        }
        NodeList nl = el.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                el = (Element) n;
                if (ModelDSN.debug >= 2)
                    System.out.println(
                            "!!!Element: ("
                                    + el.getChildNodes().getLength()
                                    + ") "
                                    + el.toString());
                if (el.getNodeName().equals("HEIGHT")) {
                    dfont.setHeigth(el.getTextContent());
                }
                if (el.getNodeName().equals("WIDTH")) {
                    dfont.setWidth(el.getTextContent());
                }
                if (el.getNodeName().equals("WEIGHT")) {
                    dfont.setWeight(el.getTextContent());
                }
                if (el.getNodeName().equals("ITALIC")) {
                    dfont.setItalic(el.getTextContent());
                }
                if (el.getNodeName().equals("UNDERLINE")) {
                    dfont.setUnderline(el.getTextContent());
                }
                if (el.getNodeName().equals("STRIKEOUT")) {
                    dfont.setStrikeout(el.getTextContent());
                }
                if (el.getNodeName().equals("FACENAME")) {
                    dfont.setFacename(el.getTextContent());
                }
            }
        }
        fonts.add(dfont);
    }
}
