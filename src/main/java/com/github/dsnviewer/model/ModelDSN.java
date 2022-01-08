package com.github.dsnviewer.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ModelDSN {
    private String fname;
    private List<TinyCad> tinyCads = new ArrayList<TinyCad>();
    private TinyCad tc = new TinyCad();
    private ArrayList<ChangeListener> views = new ArrayList<ChangeListener>();
    private ArrayList<ChangeListener> tagviews = new ArrayList<ChangeListener>();
    private ArrayList<ChangeListener> clickviews = new ArrayList<ChangeListener>();
    private Pos pos = new Pos();
    private List<Field2> modelFields = new ArrayList<Field2>();
    private boolean changed = false;

    private float alignxy = 5f;
    public static final int debug = 0;
    private String tagName = null;
    private Document doc = null;

    public ModelDSN() {
        fname = "";
    }

    public ModelDSN(String fname) {
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
        fireChangedEvent();
    }

    public float getAlignxy() {
        return alignxy;
    }

    public void setAlignxy(float axy) {
        alignxy = axy;
        fireChangedEvent();
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
        fireChangedEvent();
    }

    public void setModelFields(List<Field2> modelFields) {
        this.modelFields = modelFields;
        fireClickChangedEvent();
    }

    public List<Field2> getModelFields() {
        return modelFields;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
        fireTagChangedEvent();
    }

    public void addChangeListener(ChangeListener l) {
        views.add(l);
        l.stateChanged(new ChangeEvent(this));
    }

    public void fireChangedEvent() {
        for (Iterator it = views.iterator(); it.hasNext(); ) {
            ChangeListener l = (ChangeListener) it.next();
            l.stateChanged(new ChangeEvent(this));
        }
    }

    public void addTagChangeListener(ChangeListener l) {
        tagviews.add(l);
        l.stateChanged(new ChangeEvent(this));
    }

    public void fireTagChangedEvent() {
        for (Iterator it = tagviews.iterator(); it.hasNext(); ) {
            ChangeListener l = (ChangeListener) it.next();
            l.stateChanged(new ChangeEvent(this));
        }
    }

    public void addClickChangeListener(ChangeListener l) {
        clickviews.add(l);
        l.stateChanged(new ChangeEvent(this));
    }

    public void fireClickChangedEvent() {
        for (Iterator it = clickviews.iterator(); it.hasNext(); ) {
            ChangeListener l = (ChangeListener) it.next();
            l.stateChanged(new ChangeEvent(this));
        }
    }

    public String getPinList() {
        String result = "<html>";
        // Список символов
        result += "<table><tr><td>Num</td><td>Module name</td><td>msg</td><td>PIN</td></tr>";
        for (int i = 0; i < tc.getSymbols().size(); i++) {
            // Список определенных символов
            result += "<tr>";
            result += "<td>" + (i + 1) + "</td>";
            boolean flagfound = false;
            for (int j = 0; j < tc.getSymbols().get(i).getField2s().size(); j++) {
                if (tc.getSymbols().get(i).getField2s().get(j).getDescription().equals("Name")) {
                    result +=
                            "<td>"
                                    + tc.getSymbols().get(i).getField2s().get(j).getValue()
                                    + "</td>";
                    // Список элементов внутри определенных символов
                    flagfound = true;
                }
            }
            if (!flagfound) result += "<td>" + (i + 1) + "</td>";
            flagfound = false;
            for (int j = 0; j < tc.getSymbols().get(i).getField2s().size(); j++) {
                if (tc.getSymbols().get(i).getField2s().get(j).getDescription().equals("msg")) {
                    result +=
                            "<td>"
                                    + tc.getSymbols().get(i).getField2s().get(j).getValue()
                                    + "</td>";
                    // Список элементов внутри определенных символов
                    flagfound = true;
                }
            }
            if (!flagfound) result += "<td></td>";
            result += "<td>";
            for (int j = 0; j < tc.getSymbolDefs().size(); j++) {
                if (tc.getSymbols().get(i).getId() == tc.getSymbolDefs().get(j).getId()) {
                    for (int k = 0;
                            k < tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().size();
                            k++) {
                        result +=
                                ""
                                        + tc.getSymbolDefs()
                                                .get(j)
                                                .getTinyCadSymbol()
                                                .getPins()
                                                .get(k)
                                                .getNumber()
                                        + "&nbsp;&nbsp;";
                    }
                }
            }
            result += "</td>";
            result += "</tr>";
        }
        result += "</table>";
        result += "</html>";
        return result;
    }

    public String getRecList() {
        String result = "<html>";
        // Список символов
        result +=
                "<table><tr><td>Num</td><td>Module name</td><td>Rectangle a</td><td>Rectangle b</td></tr>";
        for (int i = 0; i < tc.getSymbols().size(); i++) {
            // Список определенных символов
            result += "<tr>";
            for (int j = 0; j < tc.getSymbols().get(i).getField2s().size(); j++) {
                if (tc.getSymbols().get(i).getField2s().get(j).getDescription().equals("Name")) {
                    result +=
                            "<td>"
                                    + (i + 1)
                                    + "</td><td>"
                                    + tc.getSymbols().get(i).getField2s().get(j).getValue()
                                    + "</td>";
                    // Список элементов внутри определенных символов
                }
            }
            result += "<td>";
            for (int j = 0; j < tc.getSymbolDefs().size(); j++) {
                if (tc.getSymbols().get(i).getId() == tc.getSymbolDefs().get(j).getId()) {
                    for (int k = 0;
                            k < tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().size();
                            k++) {
                        result +=
                                ""
                                        + tc.getSymbolDefs()
                                                .get(j)
                                                .getTinyCadSymbol()
                                                .getRectangles()
                                                .get(k)
                                                .getA()
                                        + "&nbsp;&nbsp;";
                    }
                }
            }
            result += "</td>";
            result += "<td>";
            for (int j = 0; j < tc.getSymbolDefs().size(); j++) {
                if (tc.getSymbols().get(i).getId() == tc.getSymbolDefs().get(j).getId()) {
                    for (int k = 0;
                            k < tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().size();
                            k++) {
                        result +=
                                ""
                                        + tc.getSymbolDefs()
                                                .get(j)
                                                .getTinyCadSymbol()
                                                .getRectangles()
                                                .get(k)
                                                .getB()
                                        + "&nbsp;&nbsp;";
                    }
                }
            }
            result += "</td>";
            result += "</tr>";
        }
        result += "</table>";
        result += "</html>";
        return result;
    }

    public String getRecList2() {
        String result = "<html>";
        // Список символов
        result += "<table><tr><td>Num</td><td>Coordinate</td></tr>";
        for (int i = 0; i < tc.getModules().size(); i++) {
            // Список определенных символов
            result += "<tr>";
            result += "<td>";
            result += "" + (i + 1);
            result += "</td>";
            result += "<td>";
            result +=
                    "("
                            + tc.getModules().get(i).getA().getX()
                            + ","
                            + tc.getModules().get(i).getA().getY()
                            + ") - "
                            + "("
                            + tc.getModules().get(i).getB().getX()
                            + ","
                            + tc.getModules().get(i).getB().getY()
                            + ")";
            result += "</td>";
            result += "</tr>";
        }
        result += "</table>";
        result += "</html>";
        return result;
    }

    private void parseRect() {
        // Список символов
        List<List<Dot>> dotvv = new ArrayList<List<Dot>>();
        for (int i = 0; i < tc.getSymbols().size(); i++) {
            List<Dot> dotv = new ArrayList<Dot>();
            // Список определений символов
            // Нарисовать элементы Symbol
            // Нарисовать строки из полей Field
            for (int j = 0; j < tc.getSymbols().get(i).getField2s().size(); j++) {
                if (tc.getSymbols().get(i).getField2s().get(j).getShow() == 1) {
                    Dot dd1 =
                            new Dot(
                                    (tc.getSymbols().get(i).getPos().getX()
                                            + tc.getSymbols()
                                                    .get(i)
                                                    .getField2s()
                                                    .get(j)
                                                    .getPos()
                                                    .getX()),
                                    (tc.getSymbols().get(i).getPos().getY()
                                            + tc.getSymbols()
                                                    .get(i)
                                                    .getField2s()
                                                    .get(j)
                                                    .getPos()
                                                    .getY()));
                    dotv.add(dd1);
                    if (ModelDSN.debug >= 2)
                        System.out.println("0.Added x-" + dd1.getX() + ", y-" + dd1.getY());
                }
            }
            for (int j = 0; j < tc.getSymbolDefs().size(); j++) {
                if (tc.getSymbols().get(i).getId() == tc.getSymbolDefs().get(j).getId()) {
                    // Координаты правого нижнего угла
                    for (int k = 0;
                            k < tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().size();
                            k++) {
                        Dot dd1 =
                                new Dot(
                                        tc.getSymbols().get(i).getPos().getX()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getRectangles()
                                                        .get(k)
                                                        .getA()
                                                        .getX(),
                                        tc.getSymbols().get(i).getPos().getY()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getRectangles()
                                                        .get(k)
                                                        .getA()
                                                        .getY());
                        Dot dd2 =
                                new Dot(
                                        tc.getSymbols().get(i).getPos().getX()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getRectangles()
                                                        .get(k)
                                                        .getB()
                                                        .getX(),
                                        tc.getSymbols().get(i).getPos().getY()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getRectangles()
                                                        .get(k)
                                                        .getB()
                                                        .getY());
                        dotv.add(dd1);
                        dotv.add(dd2);
                    }
                    // Наристовать строки
                    for (int k = 0;
                            k < tc.getSymbolDefs().get(j).getTinyCadSymbol().getTexts().size();
                            k++) {
                        Dot dd1 =
                                new Dot(
                                        tc.getSymbols().get(i).getPos().getX()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getTexts()
                                                        .get(k)
                                                        .getDp()
                                                        .getX(),
                                        tc.getSymbols().get(i).getPos().getY()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getTexts()
                                                        .get(k)
                                                        .getDp()
                                                        .getY());
                        Dot dd2 =
                                new Dot(
                                        tc.getSymbols().get(i).getPos().getX()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getTexts()
                                                        .get(k)
                                                        .getDp()
                                                        .getX()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getTexts()
                                                        .get(k)
                                                        .getStr()
                                                        .length(),
                                        tc.getSymbols().get(i).getPos().getY()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getTexts()
                                                        .get(k)
                                                        .getDp()
                                                        .getY()
                                                + 3);
                        if (ModelDSN.debug >= 2)
                            System.out.println("1.Added x-" + dd1.getX() + ", y-" + dd1.getY());
                        if (ModelDSN.debug >= 2)
                            System.out.println("2.Added x-" + dd2.getX() + ", y-" + dd2.getY());
                        if (ModelDSN.debug >= 1)
                            System.out.println(
                                    "Str - "
                                            + tc.getSymbolDefs()
                                                    .get(j)
                                                    .getTinyCadSymbol()
                                                    .getTexts()
                                                    .get(k)
                                                    .getStr());
                        dotv.add(dd1);
                        dotv.add(dd2);
                    }
                    // Нарисовать круги
                    for (int k = 0;
                            k < tc.getSymbolDefs().get(j).getTinyCadSymbol().getEllipses().size();
                            k++) {
                        int x1 =
                                Math.round(
                                        (tc.getSymbols().get(i).getPos().getX()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getEllipses()
                                                        .get(k)
                                                        .getA()
                                                        .getX()));
                        int y1 =
                                Math.round(
                                        (tc.getSymbols().get(i).getPos().getY()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getEllipses()
                                                        .get(k)
                                                        .getA()
                                                        .getY()));
                        int x2 =
                                Math.round(
                                        (tc.getSymbols().get(i).getPos().getX()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getEllipses()
                                                        .get(k)
                                                        .getB()
                                                        .getX()));
                        int y2 =
                                Math.round(
                                        (tc.getSymbols().get(i).getPos().getY()
                                                + tc.getSymbolDefs()
                                                        .get(j)
                                                        .getTinyCadSymbol()
                                                        .getEllipses()
                                                        .get(k)
                                                        .getB()
                                                        .getY()));
                        Dot dd1 = new Dot(Math.min(x1, x2), Math.min(y1, y2));
                        Dot dd2 =
                                new Dot(
                                        Math.min(x1, x2) + (Math.max(x1, x2) - Math.min(x1, x2)),
                                        Math.min(y1, y2) + (Math.max(y1, y2) - Math.min(y1, y2)));
                        dotv.add(dd1);
                        dotv.add(dd2);
                    }
                    // Нарисовать POLYGON линии
                    for (int k = 0;
                            k < tc.getSymbolDefs().get(j).getTinyCadSymbol().getPolygons().size();
                            k++) {
                        Pos dp =
                                tc.getSymbolDefs()
                                        .get(j)
                                        .getTinyCadSymbol()
                                        .getPolygons()
                                        .get(k)
                                        .getDp();
                        for (int l = 0;
                                l
                                        < tc.getSymbolDefs()
                                                .get(j)
                                                .getTinyCadSymbol()
                                                .getPolygons()
                                                .get(k)
                                                .getDpointv()
                                                .size();
                                l++) {
                            Dot dd1 =
                                    new Dot(
                                            tc.getSymbols().get(i).getPos().getX() + dp.getX(),
                                            tc.getSymbols().get(i).getPos().getY() + dp.getY());
                            Dot dd2 =
                                    new Dot(
                                            tc.getSymbols().get(i).getPos().getX()
                                                    + dp.getX()
                                                    + tc.getSymbolDefs()
                                                            .get(j)
                                                            .getTinyCadSymbol()
                                                            .getPolygons()
                                                            .get(k)
                                                            .getDpointv()
                                                            .get(l)
                                                            .getDp()
                                                            .getX(),
                                            tc.getSymbols().get(i).getPos().getY()
                                                    + dp.getY()
                                                    + tc.getSymbolDefs()
                                                            .get(j)
                                                            .getTinyCadSymbol()
                                                            .getPolygons()
                                                            .get(k)
                                                            .getDpointv()
                                                            .get(l)
                                                            .getDp()
                                                            .getY());
                            dotv.add(dd1);
                            dotv.add(dd2);
                        }
                    }
                }
            }
            dotvv.add(dotv);
        }
        // Отсортировать список dotvv по координатам x
        for (int i = 0; i < dotvv.size(); i++) {
            Collections.sort(dotvv.get(i), new DotXComparator());
            tc.getModules()
                    .add(
                            new Module(
                                    new Dot(dotvv.get(i).get(0).getX(), 0),
                                    new Dot(dotvv.get(i).get(dotvv.get(i).size() - 1).getX(), 0)));
        }
        // Отсортировать список dotvv по координатам y
        for (int i = 0; i < dotvv.size(); i++) {
            Collections.sort(dotvv.get(i), new DotYComparator());
            String val = "No name";
            List<Field2> allFields = new ArrayList<Field2>();
            for (int j = 0; j < tc.getSymbols().get(i).getField2s().size(); j++) {
                if (tc.getSymbols().get(i).getField2s().get(j).getDescription().equals("Ref")) {
                    val = tc.getSymbols().get(i).getField2s().get(j).getValue();
                }
                allFields.add(tc.getSymbols().get(i).getField2s().get(j));
            }
            tc.getModules()
                    .set(
                            i,
                            new Module(
                                    new Dot(
                                            tc.getModules().get(i).getA().getX(),
                                            dotvv.get(i).get(0).getY()),
                                    new Dot(
                                            tc.getModules().get(i).getB().getX(),
                                            dotvv.get(i).get(dotvv.get(i).size() - 1).getY()),
                                    val,
                                    allFields));
            if (ModelDSN.debug >= 2) {
                System.out.println(
                        "X1-"
                                + tc.getModules().get(i).getA().getX()
                                + ", Y1-"
                                + tc.getModules().get(i).getA().getX()
                                + ", X2-"
                                + tc.getModules().get(i).getB().getX()
                                + ", Y2-"
                                + tc.getModules().get(i).getB().getY());
            }
        }
    }

    public boolean save() {
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(fname));
            String encoding = "UTF-8";
            Document doc = this.doc;
            emitDocument(doc, os, encoding);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        setChanged(false);
        return true;
    }

    private void emitDocument(Document doc, OutputStream os, String encoding) throws IOException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = null;
        try {
            t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty(OutputKeys.ENCODING, encoding);
        } catch (TransformerConfigurationException tce) {
            assert (false);
        }
        DOMSource doms = new DOMSource(doc);
        StreamResult sr = new StreamResult(os);
        try {
            t.transform(doms, sr);
        } catch (TransformerException te) {
            IOException ioe = new IOException();
            ioe.initCause(te);
            throw ioe;
        }
    }

    public void load() {
        try {
            tinyCads = new ArrayList<TinyCad>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            try {
                doc = db.parse(new File(fname));
            } catch (org.xml.sax.SAXParseException ex) {
                doc =
                        db.parse(
                                new InputSource(
                                        new FileReader(fname) {
                                            public String getEncoding() {
                                                return "Windows-1251";
                                            }
                                        }));
            }
            Element root = doc.getDocumentElement();
            for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                if (ModelDSN.debug >= 2)
                    System.out.println("Root name: " + root.getChildNodes().item(i).getNodeName());
                if (root.getChildNodes().item(i).getNodeName().equals("TinyCAD")) {
                    tc = new TinyCad();
                    NodeList nl = root.getChildNodes().item(i).getChildNodes();
                    getNames(nl);
                    parseRect();
                    tinyCads.add(tc);
                }
            }
            fireChangedEvent();
            setChanged(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNames(NodeList nl) {
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                Element el = (Element) n;
                if (ModelDSN.debug >= 2) {
                    System.out.println(
                            "!!!Element: ("
                                    + el.getChildNodes().getLength()
                                    + ") "
                                    + el.toString());
                }
                if (el.getNodeName().equals("NAME")) {
                    tc.setName(el.getTextContent());
                } else if (el.getNodeName().equals("TEXT")) {
                    tc.addText(el);
                } else if (el.getNodeName().equals("WIRE")) {
                    tc.addWire(el);
                } else if (el.getNodeName().equals("BUS")) {
                    tc.addBus(el);
                } else if (el.getNodeName().equals("ELLIPSE")) {
                    tc.addEllipse(el);
                } else if (el.getNodeName().equals("FONT")) {
                    tc.addFont(el);
                } else if (el.getNodeName().equals("STYLE")) {
                    tc.addStyle(el);
                } else if (el.getNodeName().equals("SYMBOLDEF")) {
                    tc.addSymboldef(el);
                } else if (el.getNodeName().equals("SYMBOL")) {
                    tc.addSymbol(el);
                } else if (el.getNodeName().equals("JUNCTION")) {
                    tc.addJunction(el);
                } else if (el.getNodeName().equals("POWER")) {
                    tc.addPower(el);
                } else if (el.getNodeName().equals("LABEL")) {
                    tc.addLabel(el);
                } else if (el.getNodeName().equals("POLYGON")) {
                    tc.addPolygon(el);
                } else if (el.getNodeName().equals("BUSSLASH")) {
                    tc.addBusSlash(el);
                } else if (el.getNodeName().equals("DETAILS")) {
                    tc.setDetails(el.getChildNodes());
                } else if (el.getNodeName().equals("OPTIONS")) {
                    tc.setOptions(el.getChildNodes());
                }
            }
        }
    }

    public List<TinyCad> getTinyCads() {
        return tinyCads;
    }

    public static void main(String[] args) {
        ModelDSN md = new ModelDSN(args[0]);
        md.load();
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
        fireChangedEvent();
    }
}
