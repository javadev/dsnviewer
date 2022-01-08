package com.github.dsnviewer.gui;

import com.github.dsnviewer.model.ModelDSN;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CursorPos extends JLabel implements ChangeListener {
    private ModelDSN md;

    public CursorPos() {
        // Create the slider
        super();
    }

    public void setMd(ModelDSN md) {
        this.md = md;
    }

    public void stateChanged(ChangeEvent e) {
        ModelDSN md = (ModelDSN) e.getSource();
        setText(
                ""
                        + Math.round(md.getPos().getX() / md.getAlignxy())
                        + ", "
                        + Math.round(md.getPos().getY() / md.getAlignxy()));
    }
}
