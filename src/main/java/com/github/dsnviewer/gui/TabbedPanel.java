package com.github.dsnviewer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.dsnviewer.model.ModelDSN;

public class TabbedPanel extends JPanel implements ActionListener, ChangeListener {
  public static final String version = "1.0";
  public static final String APP_NAME = "DSN Viewer, version " + TabbedPanel.version;
  private JFrame m_frame;
  JFileChooser chooser = new JFileChooser();
  ModelDSN model;
  StatusBar status;
  CursorPos curpos;
  TagName tagname;
  PropertyPanel propertyPanel;
  JTabbedPane tabbedpane = new JTabbedPane();
  private String filename;
  private File dsnDir;
  private JMenuItem save1;
  private JMenuItem save2;

  class LogFilter extends javax.swing.filechooser.FileFilter {
    public String getDescription() {
      return "DSN files (*.dsn)";
   }
    public boolean accept(File file) {
      if(file.isDirectory())
        return true;
      String name=file.getName();
      name = name.toLowerCase();
      if(name.endsWith(".dsn")
        )
        return true;
      else
        return false;
    }
  }

  /**
   * Конструктор класса
   */
  public TabbedPanel() {
    init();
  }

  public TabbedPanel(JDialog dlg) {
    dlg.setJMenuBar(createMenuBar());
    init();
  }

  public TabbedPanel(JFrame frame) {
    m_frame = frame;
    frame.setJMenuBar(createMenuBar());
    init();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if (!promptToSave())
           return;
        System.exit(0);
      }
    });
  }

  private boolean promptToSave() {
    if (!model.isChanged())
       return true;
    int result = JOptionPane.showConfirmDialog(this,
       "Save changes to " + model.getFname() + "?",
       APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION,
       JOptionPane.INFORMATION_MESSAGE);
    switch (result) {
    case JOptionPane.YES_OPTION:
       if (!model.save()) {
          return false;
       }
       return true;
    case JOptionPane.NO_OPTION:
       return true;
    case JOptionPane.CANCEL_OPTION:
       return false;
    }
    return true;
 }

  private void init() {
    setLayout(new BorderLayout());
    chooser.setFileFilter(new LogFilter());
    model = new ModelDSN();
    status = new StatusBar();
    status.setMd(model);
    curpos = new CursorPos();
    curpos.setMd(model);
    tagname = new TagName();
    tagname.setMd(model);
    JPanel jp = new JPanel(new BorderLayout());
    jp.add(status, BorderLayout.EAST);
    add(jp, BorderLayout.SOUTH);

    jp = new JPanel(new FlowLayout());
    jp.add(new JLabel("Tag name: "));
    jp.add(tagname);
    jp.add(new JLabel("Pos: "));
    jp.add(curpos);
    JPanel jp2 = new JPanel(new BorderLayout());
    jp2.add(jp, BorderLayout.EAST);
    add(jp2, BorderLayout.NORTH);
    add(tabbedpane, BorderLayout.CENTER);
    propertyPanel = new PropertyPanel();
    jp2 = new JPanel(new BorderLayout());
    jp2.add(propertyPanel, BorderLayout.NORTH);
    add(jp2, BorderLayout.EAST);

    model.addChangeListener(status);
    model.addChangeListener(curpos);
    model.addChangeListener(tagname);
    model.addChangeListener(this);
    model.addClickChangeListener(propertyPanel);
  }

  public ModelDSN getModel() {
    return model;
  }

  public void stateChanged(ChangeEvent e) {
    ModelDSN md=(ModelDSN)e.getSource();
    if (m_frame != null) {
      m_frame.setTitle(APP_NAME + " " +
          (md.isChanged() ? "* " : "") + md.getFname());
    }
    if (md.getDoc() == null) {
      save1.setEnabled(false);
      save2.setEnabled(false);
    } else {
      save1.setEnabled(true);
      save2.setEnabled(true);
    }
    status.setMd(md);
    status.getSlider().setValue(Math.round(md.getAlignxy()*10));
    curpos.setMd(md);
    updateUI();
  }

  public JMenuBar createMenuBar() {
    JMenuBar jmenubar = new JMenuBar();
    JMenu jmenu = new JMenu("File", true);
    jmenu.setMnemonic('F');

    JMenuItem jmenuitem = new JMenuItem("Open", 120);
    jmenuitem.setActionCommand("Open");
    jmenuitem.setMnemonic('O');
    jmenuitem.addActionListener(this);
    jmenu.add(jmenuitem);

    save1 = new JMenuItem("Save", 120);
    save1.setActionCommand("Save");
    save1.setMnemonic('s');
    save1.addActionListener(this);
    jmenu.add(save1);

    save2 = new JMenuItem("Save as...", 120);
    save2.setActionCommand("Saveas");
    save2.setMnemonic('a');
    save2.addActionListener(this);
    jmenu.add(save2);

    jmenuitem = new JMenuItem("Exit", 120);
    jmenuitem.setActionCommand("Exit");
    jmenuitem.addActionListener(this);
    jmenu.addSeparator();
    jmenu.add(jmenuitem);

    jmenubar.add(jmenu);

    jmenu = new JMenu("View", true);
    jmenu.setMnemonic('V');

    jmenuitem = new JMenuItem("Список PIN", 120);
    jmenuitem.setActionCommand("PINList");
    jmenuitem.addActionListener(this);
    jmenu.add(jmenuitem);
    jmenubar.add(jmenu);

    jmenuitem = new JMenuItem("Список Rectangle", 120);
    jmenuitem.setActionCommand("RECList");
    jmenuitem.addActionListener(this);
    jmenu.add(jmenuitem);
    jmenubar.add(jmenu);

    jmenu = new JMenu("Help", true);
    jmenu.setMnemonic('H');

    jmenuitem = new JMenuItem("About", 120);
    jmenuitem.setActionCommand("About");
    jmenuitem.addActionListener(this);
    jmenu.add(jmenuitem);
    jmenubar.add(jmenu);

    return jmenubar;
  }

  public void openDocument() {
  }

  public File getDsnFolder() {
    if (filename != null) {
      String dir = filename.substring(0, filename.lastIndexOf(File.separator));
      File df = new File(dir);
      if (df.exists()) {
        return df;
      }
    }
    return null;
  }

  public void setDsnFolder(File dir) {
    dsnDir = dir;
  }

  /**
  * Обработчик для элементов окна
  */
  public void actionPerformed(ActionEvent actionevent) {
    String s = actionevent.getActionCommand();
    if(s.equals("Open")) {
      filename = null;
      if (!promptToSave()) {
        return;
      }
      int answer = chooser.showOpenDialog(this);
      if (answer != JFileChooser.APPROVE_OPTION) {
        return;
      }
      if (dsnDir != null && dsnDir.exists()) {
        chooser.setCurrentDirectory(dsnDir);
      }
      filename = chooser.getSelectedFile().getPath();
      model = new ModelDSN();
      model.addChangeListener(status);
      model.addChangeListener(curpos);
      model.addChangeListener(tagname);
      model.addChangeListener(this);
      model.addClickChangeListener(propertyPanel);
      model.setFname(filename);
      model.load();
      tabbedpane.removeAll();
      for (int i=0;i<model.getTinyCads().size();i++) {
        Panel panel0 = new Panel(model, model.getTinyCads().get(i));
        model.addChangeListener(panel0);
        tabbedpane.add(model.getTinyCads().get(i).getName(), new JScrollPane(panel0));
      }
    } else if(s.equals("Save")) {
      model.save();
    } else if(s.equals("Saveas")) {
      filename = null;
      int answer = chooser.showSaveDialog(this);
      if (answer != JFileChooser.APPROVE_OPTION) {
        return;
      }
      if (dsnDir != null && dsnDir.exists()) {
        chooser.setCurrentDirectory(dsnDir);
      }
      filename = chooser.getSelectedFile().getPath();
      model.setFname(filename);
      model.save();
    } else if (s.equals("PINList")) {
      JOptionPane.showMessageDialog(this,
        model.getPinList());
    } else if (s.equals("RECList")) {
      JOptionPane.showMessageDialog(this,
        model.getRecList2());
    } else if (s.equals("Exit")) {
      if (promptToSave()) {
        if (m_frame != null) {
          m_frame.dispose();
        }
        System.exit(0);
      }
    } else if (s.equals("About")) {
        JOptionPane.showMessageDialog(this,
          "The DSN Viewer, version "+version+"\n");
    }
  }

  public static Dimension HGAP5 = new Dimension(5,1);
  public static Dimension VGAP5 = new Dimension(1,5);
  public static Dimension HGAP10 = new Dimension(10,1);
  public static Dimension VGAP10 = new Dimension(1,10);

}
