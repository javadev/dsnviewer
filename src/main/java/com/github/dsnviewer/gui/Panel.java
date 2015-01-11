package com.github.dsnviewer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.dsnviewer.model.Field2;
import com.github.dsnviewer.model.ModelDSN;
import com.github.dsnviewer.model.Module;
import com.github.dsnviewer.model.Point;
import com.github.dsnviewer.model.Polygon;
import com.github.dsnviewer.model.Pos;
import com.github.dsnviewer.model.TinyCad;

public class Panel extends JPanel implements ChangeListener, MouseMotionListener {
  private ModelDSN md;
  private TinyCad tc;
  private float axy;

  public Panel(ModelDSN md1, TinyCad dtc1) {
    setLayout(new BorderLayout());
    this.md = md1;
    this.tc = dtc1;
    setAlignmentX(5);
    setAlignmentY(5);
    this.setBackground(Color.WHITE);
    this.addMouseMotionListener(this);
    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
          String tagname = null;
          for (int i=0;i<tc.getModules().size();i++) {
            if (md.getPos().getX() >= (tc.getModules().get(i).getA().getX()-1)*tc.getAlignxy() &&
                md.getPos().getX() <= (tc.getModules().get(i).getB().getX()+1)*tc.getAlignxy() &&
                md.getPos().getY() >= (tc.getModules().get(i).getA().getY()-1)*tc.getAlignxy() &&
                md.getPos().getY() <= (tc.getModules().get(i).getB().getY()+1)*tc.getAlignxy()
            ) {
              tagname = tc.getModules().get(i).getStr();
              md.setModelFields(tc.getModules().get(i).getAllFields());
              md.setTagName(tagname);
              return;
            }
          }
          md.setModelFields(new ArrayList<Field2>());
      }
    });
  }

  public void setModel(ModelDSN md) {
    this.md = md;
  }

  public void mouseMoved(MouseEvent e) {
    md.setPos(new Pos(e.getX(),e.getY()));

    String tagname = null;
    for (int i=0;i<tc.getModules().size();i++) {
      if (md.getPos().getX() >= (tc.getModules().get(i).getA().getX()-1)*tc.getAlignxy() &&
          md.getPos().getX() <= (tc.getModules().get(i).getB().getX()+1)*tc.getAlignxy() &&
          md.getPos().getY() >= (tc.getModules().get(i).getA().getY()-1)*tc.getAlignxy() &&
          md.getPos().getY() <= (tc.getModules().get(i).getB().getY()+1)*tc.getAlignxy()
      ) {
        tagname = tc.getModules().get(i).getStr();
        break;
      }
    }
    md.setTagName(tagname);
  }
  public void mouseDragged(MouseEvent e)  {
  }
  public void stateChanged(ChangeEvent e) {
    ModelDSN md=(ModelDSN)e.getSource();
    this.md = md;
    tc.setAlignxy(md.getAlignxy());
    float axy = md.getAlignxy();
    setPreferredSize(new Dimension(Math.round(tc.getDetails().getDsize().getWidth()/5*axy),
            Math.round(tc.getDetails().getDsize().getHeight()/5*axy)));
    updateUI();
  }

  private void paintBorders(Graphics g) {
    // нарисовать рамки вокруг объектов
    for (Module module : tc.getModules()) {
      module.paintBorders(g, tc.getAlignxy());
    }
  }

  private void paintSelectedBorders(Graphics g) {
    // нарисовать рамки вокруг объектов при наведении курсора мыши
   Color savecol = g.getColor();
    g.setColor(Color.RED);
    for (int i=0;i<tc.getModules().size();i++) {
      if (md.getPos().getX() >= (tc.getModules().get(i).getA().getX()-1)*tc.getAlignxy() &&
          md.getPos().getX() <= (tc.getModules().get(i).getB().getX()+1)*tc.getAlignxy() &&
          md.getPos().getY() >= (tc.getModules().get(i).getA().getY()-1)*tc.getAlignxy() &&
          md.getPos().getY() <= (tc.getModules().get(i).getB().getY()+1)*tc.getAlignxy()
      ) {
        g.drawRect(
          Math.round((tc.getModules().get(i).getA().getX()-1)*tc.getAlignxy()),
          Math.round((tc.getModules().get(i).getA().getY()-1)*tc.getAlignxy()),
          Math.round((tc.getModules().get(i).getB().getX()+1)*tc.getAlignxy()) - Math.round((tc.getModules().get(i).getA().getX()-1)*tc.getAlignxy()),
          Math.round((tc.getModules().get(i).getB().getY()+1)*tc.getAlignxy()) - Math.round((tc.getModules().get(i).getA().getY()-1)*tc.getAlignxy())
        );
        break;
      }
    }
    g.setColor(savecol);
  }

  private void paintWires(Graphics g) {
    // Нарисовать проволки Wire
    //  System.out.println("1.size() - "+dtc.getWires().size());
    Color savecol = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_wire()));
    for (int i=0;i<tc.getWires().size();i++) {
      g.drawLine(Math.round(tc.getWires().get(i).getA().getX()*axy),
        Math.round(tc.getWires().get(i).getA().getY()*axy),
        Math.round(tc.getWires().get(i).getB().getX()*axy),
        Math.round(tc.getWires().get(i).getB().getY()*axy));
    }
    g.setColor(savecol);
  }
  
  private void paintBuses(Graphics g) {
    // Нарисовать шины Bus
    Color savecol = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_bus()));
    for (int i=0;i<tc.getBuses().size();i++) {
      int x1 = Math.round(tc.getBuses().get(i).getA().getX()*axy);
      int y1 = Math.round(tc.getBuses().get(i).getA().getY()*axy);
      int x2 = Math.round(tc.getBuses().get(i).getB().getX()*axy);
      int y2 = Math.round(tc.getBuses().get(i).getB().getY()*axy);
      g.fillRect(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2)+3,Math.abs(y1-y2)+3);
    }
    g.setColor(savecol);
  }
  
  private void paintEllipses(Graphics g) {
   // Нарисовать круги
    Color savecol = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_wire()));
    for (int i=0;i<tc.getEllipses().size();i++) {
      for (int j=0;j<tc.getStyles().size();j++) {
        if (tc.getStyles().get(j).getId() == tc.getEllipses().get(i).getStyle()) {
          g.setColor(new Color(tc.getStyles().get(j).getColor()));
        }
      }
      int x1 = Math.round(tc.getEllipses().get(i).getA().getX()*axy);
      int y1 = Math.round(tc.getEllipses().get(i).getA().getY()*axy);
      int x2 = Math.round(tc.getEllipses().get(i).getB().getX()*axy);
      int y2 = Math.round(tc.getEllipses().get(i).getB().getY()*axy);
      g.drawOval(
         Math.min(x1, x2),
         Math.min(y1, y2),
         Math.max(x1,x2) - Math.min(x1, x2),
         Math.max(y1,y2) - Math.min(y1, y2)
      );
    }
    g.setColor(savecol);
  }
  
  private void paintPolygons(Graphics g) {
    // Нарисовать POLYGON линии
    Color savecol = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_wire()));
    for (int i=0;i<tc.getPolygons().size();i++) {
      for (int j=0;j<tc.getStyles().size();j++) {
        if (tc.getStyles().get(j).getId() == tc.getPolygons().get(i).getStyle()) {
          g.setColor(new Color(tc.getStyles().get(j).getColor()));
        }
      }
      for (Polygon polygon : tc.getPolygons()) {
        Pos dp = null;
        Pos dpfirst = null;
        Pos dpoply = polygon.getDp();
        int index = 0;
        for (Point point : polygon.getDpointv()) {
          if (index == 0) {
            dpfirst = point.getDp();
          } else {
            g.drawLine(
                Math.round((dpoply.getX() + dp.getX())*axy),
                Math.round((dpoply.getY() + dp.getY())*axy),
                Math.round((dpoply.getX() + point.getDp().getX())*axy),
                Math.round((dpoply.getY() + point.getDp().getY())*axy)
              );
          }
          dp = point.getDp();
          index += 1;
        }
      }
    }
    g.setColor(savecol);
  }
  
  private void paintTextLabels(Graphics g) {
    // Нарисовать текстовые метки
    Font savefont = g.getFont();
    for (int i=0;i<tc.getTexts().size();i++) {
      for (int j=0;j<tc.getFonts().size();j++) {
        if (tc.getFonts().get(j).getId() == tc.getTexts().get(i).getFont()) {
          g.setFont(new Font( "arial",
             Font.PLAIN, Math.abs(Math.round(tc.getFonts().get(j).getHeight()*axy/5) )));
          break;
        }
      }
      g.drawString(tc.getTexts().get(i).getStr(),
        Math.round(tc.getTexts().get(i).getDp().getX()*axy),
        Math.round(tc.getTexts().get(i).getDp().getY()*axy)
      );
    }
    g.setFont(savefont);
  }
  
  private void paintLabels(Graphics g) {
    // Нарисовать label метки
    Font savefont = g.getFont();
    Color savecol = g.getColor();
    for (int i=0;i<tc.getLabels().size();i++) {
      g.setColor(new Color(tc.getLabels().get(i).getColor()));
      for (int j=0;j<tc.getFonts().size();j++) {
        if (tc.getFonts().get(j).getId() == tc.getLabels().get(i).getFont()) {
          g.setFont(new Font( "arial",
             Font.PLAIN, Math.abs(Math.round(tc.getFonts().get(j).getHeight()*axy/5) )));
          break;
        }
      }
      g.drawString(tc.getLabels().get(i).getStr(),
        Math.round(tc.getLabels().get(i).getDp().getX()*axy),
        Math.round((tc.getLabels().get(i).getDp().getY()-0.5f)*axy)
      );
      g.setColor(Color.RED);
      g.drawRect(
            Math.round(tc.getLabels().get(i).getDp().getX()*axy),
            Math.round((tc.getLabels().get(i).getDp().getY()-0.5f)*axy),
              Math.round(0.8f*tc.getAlignxy()),
              Math.round(0.8f*tc.getAlignxy())
            );
    }
    g.setFont(savefont);
    g.setColor(savecol);
  }

  private void paintPowerLabels(Graphics g) {
    // Нарисовать power метки
    Font savefont = g.getFont();
    for (int i=0;i<tc.getPowers().size();i++) {
      g.drawString(tc.getPowers().get(i).getStr(),
        Math.round(tc.getPowers().get(i).getDp().getX()*axy),
        Math.round(tc.getPowers().get(i).getDp().getY()*axy)
      );
    }
    g.setFont(savefont);
   
  }
  
  private void paintOvals(Graphics g) {
    // Нарисовать кружочки
    for (int i=0;i<tc.getJunctions().size();i++) {
      g.fillOval(
        Math.round(tc.getJunctions().get(i).getX()*axy-(axy+2)/2),
        Math.round(tc.getJunctions().get(i).getY()*axy-(axy+2)/2),
        Math.round(axy+2),
        Math.round(axy+2)
      );
    }
  }

  private void paintSymbolTexts(Graphics g, int i) {
    // Нарисовать строки из полей Field
    Color savecol = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_pin()));
    for (int j=0;j<tc.getSymbols().get(i).getField2s().size();j++) {
      if (tc.getSymbols().get(i).getField2s().get(j).getShow() == 1) {
        g.setFont(new Font( "arial",
                 Font.PLAIN, Math.abs(Math.round(10*axy/5) )));
        g.drawString(tc.getSymbols().get(i).getField2s().get(j).getValue(),
          Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbols().get(i).getField2s().get(j).getPos().getX())*axy),
          Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbols().get(i).getField2s().get(j).getPos().getY())*axy)
        );
      }
    }
    g.setColor(savecol);
  }
  
  private void paintSymbolRectangles(Graphics g, int i, int j) {
    // Нарисовать прямоугольники
    for (int k=0;k<tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().size();k++) {
      int x1 = Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().get(k).getA().getX())*axy);
      int y1 = Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().get(k).getA().getY())*axy);
      int x2 = Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().get(k).getB().getX())*axy);
      int y2 = Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getRectangles().get(k).getB().getY())*axy);
      g.drawRect(
         Math.min(x1, x2),
         Math.min(y1, y2),
         Math.max(x1,x2) - Math.min(x1, x2),
         Math.max(y1,y2) - Math.min(y1, y2)
      );
    }
  }
  
  private void paintSymbolOvals(Graphics g, int i, int j) {
   // Нарисовать круги
    Color savecol = g.getColor();
    for (int k=0;k<tc.getSymbolDefs().get(j).getTinyCadSymbol().getEllipses().size();k++) {
      for (int l=0;l<tc.getStyles().size();l++) {
        if (tc.getStyles().get(l).getId() == tc.getSymbolDefs().get(j).getTinyCadSymbol().getEllipses().get(k).getStyle()) {
          g.setColor(new Color(tc.getStyles().get(l).getColor()));
        }
      }
      int x1 = Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getEllipses().get(k).getA().getX())*axy);
      int y1 = Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getEllipses().get(k).getA().getY())*axy);
      int x2 = Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getEllipses().get(k).getB().getX())*axy);
      int y2 = Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getEllipses().get(k).getB().getY())*axy);
      g.drawOval(
         Math.min(x1, x2),
         Math.min(y1, y2),
         Math.max(x1,x2) - Math.min(x1, x2),
         Math.max(y1,y2) - Math.min(y1, y2)
      );
    }
    g.setColor(savecol);
  }
  
  private void paintSymbolTexts(Graphics g, int i, int j) {
    // Наристовать строки
    for (int k=0;k<tc.getSymbolDefs().get(j).getTinyCadSymbol().getTexts().size();k++) {
      for (int l=0;l<tc.getFonts().size();l++) {
        if (tc.getFonts().get(l).getId() == tc.getSymbolDefs().get(j).getTinyCadSymbol().getTexts().get(k).getFont()) {
          g.setFont(new Font( "arial",
                  Font.PLAIN, Math.abs(Math.round(tc.getFonts().get(l).getHeight()*axy/5) )));
          break;
        }
      }
      Color savecolor = g.getColor();
      g.setColor(new Color(tc.getSymbolDefs().get(j).getTinyCadSymbol().getTexts().get(k).getColor()));
      g.drawString(tc.getSymbolDefs().get(j).getTinyCadSymbol().getTexts().get(k).getStr(),
        Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getTexts().get(k).getDp().getX())*axy),
        Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getTexts().get(k).getDp().getY())*axy)
      );
      g.setColor(savecolor);
    }
  }
  
  private void paintSymbolLabels(Graphics g, int i, int j) {
   // Наристовать метки
    for (int k=0;k<tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().size();k++) {
      for (int l=0;l<tc.getFonts().size();l++) {
        if (tc.getFonts().get(l).getId() == tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().get(k).getFont()) {
          g.setFont(new Font( "arial",
                  Font.PLAIN, Math.abs(Math.round(tc.getFonts().get(l).getHeight()*axy/5) )));
          break;
        }
      }
      Color savecolor = g.getColor();
      g.setColor(new Color(tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().get(k).getColor()));
      g.drawString(tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().get(k).getStr(),
        Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().get(k).getDp().getX())*axy),
        Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().get(k).getDp().getY())*axy)
      );
      // Нарисовать квадратик возле метки
      g.setColor(Color.RED);
      g.drawRect(
              Math.round(((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().get(k).getDp().getX()))*tc.getAlignxy()),
              Math.round(((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getLabels().get(k).getDp().getY()))*tc.getAlignxy()),
              Math.round(0.8f*tc.getAlignxy()),
              Math.round(0.8f*tc.getAlignxy())
            );
      g.setColor(savecolor);
    }
  }
  
  private int[] paintSymbolPinCalc(int j, int k) {
    int deltax1 = 0, deltax2 = 0, deltay1 = 0, deltay2 = 0;
    int deltafontx = 0, deltafonty = 0;
    int pinlen = tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getLength()/5;
    switch( tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getDirection()) {
      case 0: deltay1 = -pinlen; deltay2 = 0;
        deltafonty = -1;
        break;
      case 1: deltay1 = 0; deltay2 = pinlen;
        deltafonty = 0;
        break;
      case 2: deltax1 = -pinlen; deltax2 = 0;
        deltafontx = -1;
        break;
      case 3: deltax1 = 0; deltax2 = pinlen;
        deltafontx = 0;
        break;
    }
    return new int[] {deltax1, deltax2, deltay1, deltay2, deltafontx, deltafonty, pinlen};
  }
  
  private void paintSymbolPinTexts1(Graphics g, int j, int k, int x1, int y1, int x2, int deltafontx) {
    if (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getStr() != null &&
        (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getShow() == 2 ||
        tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getShow() == 3)) {
//      if (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getWhich() != 0) {
    g.drawString(
        tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getStr(),
        (deltafontx >= 0 ? Math.max(x1, x2) : Math.min(x1, x2)) + 
        Math.round(2*axy/5) + deltafontx*Math.round(tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getStr().length()*axy*1.5f),
        Math.round(4*axy/5) + y1
      );
//      }
    }
  }
  
  private void paintSymbolPinWhich1(Graphics g, int j, int k, int x1, int y1, int x2, int y2, int deltafontx, int deltafonty) {
    Color savecolor = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_pin()));
    if (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getWhich() == 1) {
      g.drawOval(
          Math.round((deltafontx >= 0 ? Math.max(x1, x2)-Math.round(axy*0.6) : Math.min(x1, x2)-Math.round(axy*0.6))),
          Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*0.6) : Math.min(y1, y2)-Math.round(axy*0.6))),
          Math.round(axy*1.2f),
          Math.round(axy*1.2f)
       );
    }
    g.setColor(savecolor);
  }

  private void paintSymbolPinWhich2(Graphics g, int j, int k, int x1, int y1, int x2, int y2, int deltafontx, int deltafonty) {
    Color savecolor = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_pin()));
    if (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getWhich() == 2) {
      int x11 = Math.round((deltafontx >= 0 ? Math.max(x1, x2)-Math.round(axy*0.6) : Math.min(x1, x2)-Math.round(axy*0.6)));
      int y11 = Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*0) : Math.min(y1, y2)-Math.round(axy*0)));
      int x12 = Math.round((deltafontx >= 0 ? Math.max(x1, x2)+Math.round(axy*0.6) : Math.min(x1, x2)+Math.round(axy*0.6)));
      int y12 = Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*0) : Math.min(y1, y2)-Math.round(axy*0)));
      int x13 = Math.round((deltafontx >= 0 ? Math.max(x1, x2)+Math.round(axy*0) : Math.min(x1, x2)+Math.round(axy*0)));
      int y13 = Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*0.9) : Math.min(y1, y2)-Math.round(axy*0.9)));
      
      g.drawLine(
            x11,
            y11,
            x12,
            y12
         );
      g.drawLine(
            x12,
            y12,
            x13,
            y13
         );
      g.drawLine(
            x13,
            y13,
            x11,
            y11
         );
    }
    g.setColor(savecolor);
  }

  private void paintSymbolPinWhich3(Graphics g, int j, int k, int x1, int y1, int x2, int y2, int deltafontx, int deltafonty) {
    Color savecolor = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_pin()));
    if (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getWhich() == 3) {
      g.drawOval(
          Math.round((deltafontx >= 0 ? Math.max(x1, x2)-Math.round(axy*0.6) : Math.min(x1, x2)-Math.round(axy*0.6))),
          Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*0.6) : Math.min(y1, y2)-Math.round(axy*0.6))),
          Math.round(axy*1.2f),
          Math.round(axy*1.2f)
       );
      int x11 = Math.round((deltafontx >= 0 ? Math.max(x1, x2)-Math.round(axy*0.6) : Math.min(x1, x2)-Math.round(axy*0.6)));
      int y11 = Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*0.7) : Math.min(y1, y2)-Math.round(axy*0.7)));
      int x12 = Math.round((deltafontx >= 0 ? Math.max(x1, x2)+Math.round(axy*0.6) : Math.min(x1, x2)+Math.round(axy*0.6)));
      int y12 = Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*0.7) : Math.min(y1, y2)-Math.round(axy*0.7)));
      int x13 = Math.round((deltafontx >= 0 ? Math.max(x1, x2)+Math.round(axy*0) : Math.min(x1, x2)+Math.round(axy*0)));
      int y13 = Math.round((deltafonty >= 0 ? Math.max(y1, y2)-Math.round(axy*1.6) : Math.min(y1, y2)-Math.round(axy*1.6)));
      
      g.drawLine(
            x11,
            y11,
            x12,
            y12
         );
      g.drawLine(
            x12,
            y12,
            x13,
            y13
         );
      g.drawLine(
            x13,
            y13,
            x11,
            y11
         );
    }
    g.setColor(savecolor);
  }

  private void paintSymbolPinTexts2(Graphics g, int j, int k, int x1, int y1, int x2, int y2, int deltafontx, int deltafonty) {
    if (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getNumber() != null &&
        (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getShow() == 2 ||
        tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getShow() == 3)) {
    g.drawString(
        tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getNumber(),
        Math.min(x1, x2) + Math.abs(x2 - x1)/2 - Math.round(tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getNumber().length()*axy*5f/12),
        Math.min(y1, y2) - Math.round(2*axy/5)
      );
    }
    Color savecolor = g.getColor();
    g.setColor(new Color(tc.getOptions().getColor_pin()));
    g.drawLine(
        x1,
        y1,
        x2,
        y2
    );
    g.setColor(savecolor);
    paintSymbolPinWhich1(g, j, k, x1, y1, x2, y2, deltafontx, deltafonty);
    paintSymbolPinWhich2(g, j, k, x1, y1, x2, y2, deltafontx, deltafonty);
    paintSymbolPinWhich3(g, j, k, x1, y1, x2, y2, deltafontx, deltafonty);
  }
  
  private void paintSymbolPins(Graphics g, int i, int j) {
    // Нарисовать PIN черточки
    Font savefont = g.getFont();
    for (int k=0;k<tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().size();k++) {
      g.setFont(new Font( "courier new", Font.PLAIN, Math.round(12*axy/5) ));
      
      int[] data = paintSymbolPinCalc(j, k);
      int deltax1 = data[0], deltax2 = data[1], deltay1 = data[2], deltay2 = data[3];
      int deltafontx = data[4], deltafonty = data[5];
      int pinlen = data[6];
      int x1 = Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getPos().getX()+deltax1)*axy);
      int y1 = Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getPos().getY()+deltay1)*axy);
      int x2 = Math.round((tc.getSymbols().get(i).getPos().getX() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getPos().getX()+deltax2)*axy);
      int y2 = Math.round((tc.getSymbols().get(i).getPos().getY() + tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getPos().getY()+deltay2)*axy);
      if (tc.getSymbolDefs().get(j).getTinyCadSymbol().getPins().get(k).getWhich() < 4) {
         paintSymbolPinTexts1(g, j, k, x1, y1, x2, deltafontx);
         paintSymbolPinTexts2(g, j, k, x1, y1, x2, y2, deltafontx, deltafonty);
      }
    }
    g.setFont(savefont);
  }
  
  private void paintSymbolPolygons(Graphics g, int i, int j) {
    // Нарисовать POLYGON линии
    for (Polygon polygon : tc.getSymbolDefs().get(j).getTinyCadSymbol().getPolygons()) {
      Pos dp = null;
      Pos dpfirst = null;
      Pos dpoply = polygon.getDp();
      int index = 0;
      for (Point point : polygon.getDpointv()) {
        if (index == 0) {
          dpfirst = point.getDp();
        } else {
          g.drawLine(
              Math.round((tc.getSymbols().get(i).getPos().getX() + dpoply.getX() + dp.getX())*axy),
              Math.round((tc.getSymbols().get(i).getPos().getY() + dpoply.getY() + dp.getY())*axy),
              Math.round((tc.getSymbols().get(i).getPos().getX() + dpoply.getX() + point.getDp().getX())*axy),
              Math.round((tc.getSymbols().get(i).getPos().getY() + dpoply.getY() + point.getDp().getY())*axy)
            );
        }
        dp = point.getDp();
        index += 1;
      }
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    axy = tc.getAlignxy();
    // Нарисовать сетку
    /*
    for (float i=0;i<dtc.getDdet().getDsize().getWidth()/5*axy;i+=2*axy) {
      for (float j=0;j<dtc.getDdet().getDsize().getHeight()/5*axy;j+=2*axy) {
        g.fillRect(Math.round(i),Math.round(j),1,1);
      }
    }#939393
    */
    paintBorders(g);
    paintSelectedBorders(g);
    paintWires(g);
    paintBuses(g);
    paintEllipses(g);
    paintPolygons(g);
    paintTextLabels(g);
    paintLabels(g);
    paintPowerLabels(g);
    paintOvals(g);

    // Нарисовать элементы Symbol
    for (int i=0;i<tc.getSymbols().size();i++) {
      paintSymbolTexts(g, i);
      for (int j=0;j<tc.getSymbolDefs().size();j++) {
        // Проверка совпадения id для элемента из Symbol и элемента Symboldef
        // Отрисовка Symboldef
        if (tc.getSymbols().get(i).getId() == tc.getSymbolDefs().get(j).getId()) {
         paintSymbolRectangles(g, i, j);
         paintSymbolOvals(g, i, j);
          paintSymbolTexts(g, i, j);
          paintSymbolLabels(g, i, j);
          paintSymbolPins(g, i, j);
          paintSymbolPolygons(g, i, j);
        }
      }
    }
  }

}
