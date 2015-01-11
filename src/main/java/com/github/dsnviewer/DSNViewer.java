package com.github.dsnviewer;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.github.dsnviewer.gui.TabbedPanel;

/**
 * @author Valentyn Kolesnikov
 */
public class DSNViewer {

     public static void main(String[] args) {
       try {
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
       } catch (Exception e) {
         e.printStackTrace();
       }
       JFrame frame = new JFrame(TabbedPanel.APP_NAME);
       frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     
       frame.getContentPane().add(new TabbedPanel(frame), null);
       frame.pack();
       frame.setSize(780,580);
       frame.setResizable(true);
       Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
       frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
       frame.setVisible(true);
     }
}
