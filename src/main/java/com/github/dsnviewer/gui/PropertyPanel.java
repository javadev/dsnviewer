package com.github.dsnviewer.gui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.dsnviewer.model.Field2;
import com.github.dsnviewer.model.ModelDSN;

public class PropertyPanel extends JPanel implements ChangeListener {
  private static final int LABEL_MAX_WIDTH = 50;
  private static final int EDIT_MAX_WIDTH = 150;
  private List<JTextField> textFields = new ArrayList<JTextField>();
  List<Field2> field2s = new ArrayList<Field2>();
  ModelDSN md;
  
  /**
   */
  public PropertyPanel() {
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
  }
  private void addElements(List<Field2> field2s) {
    this.field2s = field2s;
    textFields.clear();
    this.removeAll();
    for(Field2 field2 : field2s) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
      panel.setBorder(new EmptyBorder(5,10,5,10));
      JLabel label = new JLabel(field2.getDescription());
      label.setPreferredSize(new Dimension(LABEL_MAX_WIDTH, label.getPreferredSize().height));
      label.setToolTipText(field2.getDescription());
      panel.add(label);
      panel.add(new JLabel(" "));
      JTextField textField = new JTextField();
      if (field2.getShow() != 1) {
        textField.setEditable(false);
      }
      textField.setPreferredSize(new Dimension(EDIT_MAX_WIDTH, textField.getPreferredSize().height));
      textField.setText(field2.getValue());
      textField.setToolTipText(field2.getValue());
      textField.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent keyevent) {
          if(keyevent.getKeyCode() == KeyEvent.VK_ENTER) {
            saveData();
          }
        }
      });

      textFields.add(textField);
      panel.add(textField);
      add(panel);
    }
    if (field2s.size() > 0) {
      setVisible(true);
    } else {
      setVisible(false);
    }
    updateUI();
  }
  
  public void stateChanged(ChangeEvent e) {
    md=(ModelDSN)e.getSource();
    addElements(md.getModelFields());
  }
  public void saveData() {
    int index = 0;
    for(Field2 field2 : field2s) {
      field2.setValue(textFields.get(index).getText());
      field2.getElement().setAttribute("value", textFields.get(index).getText());
      md.setChanged(true);
      index += 1;
    }
  }
}
