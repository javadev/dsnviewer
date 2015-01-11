package com.github.dsnviewer.gui;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.dsnviewer.model.ModelDSN;

public class StatusBar extends JPanel implements ChangeListener {
  static final int FPS_MIN = 40;
  static final int FPS_MAX = 120;
  static final int FPS_INIT = 50;    //initial frames per second
  private ModelDSN md;
  private JSlider slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
  private JLabel labelSize = new JLabel();
  public StatusBar() {
    super(new FlowLayout());
    slider.setMajorTickSpacing(0);
    slider.setPaintTicks(true);
    slider.addChangeListener(this);
    labelSize.setText("100% ");
    add(labelSize);
    add(new JLabel(createImageIcon("minus.png")));
    add(slider);
    add(new JLabel(createImageIcon("plus.png")));
  }
  public void setMd(ModelDSN md) {
    this.md = md;
  }
  public void stateChanged(ChangeEvent e) {
    if (e.getSource() instanceof JSlider ) {
      JSlider source = (JSlider)e.getSource();
      int fps = (int)source.getValue();
      labelSize.setText("" + Math.round(fps*2) + "% ");
      md.setAlignxy(fps/10f);
    }
  }
  public ImageIcon createImageIcon(String filename) {
    String path = "images/" + filename;
      return new ImageIcon(getClass().getResource(path)); 
  }
  public JSlider getSlider() {
    return slider;
  }
}
