package com.github.dsnviewer.gui;

import com.github.dsnviewer.model.ModelDSN;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TagName extends JLabel implements ChangeListener {
    private ModelDSN md;

    public TagName() {
        super();
    }

    public void setMd(ModelDSN md) {
        this.md = md;
    }

    public void stateChanged(ChangeEvent e) {
        ModelDSN md = (ModelDSN) e.getSource();
        setText(md.getTagName() != null ? md.getTagName() : "");
    }
}
