package com.github.dsnviewer.model;

public class Options {
    private int font,
            style,
            grid,
            units,
            color_wire,
            color_bus,
            color_junction,
            color_noconnect,
            color_label,
            color_power,
            color_pin,
            color_hidden_pin;

    public int getColor_pin() {
        return color_pin;
    }

    public int getColor_label() {
        return color_label;
    }

    public int getColor_wire() {
        return color_wire;
    }

    public int getColor_bus() {
        return color_bus;
    }

    public void setFont(String s) {
        font = Integer.parseInt(s);
    }

    public void setStyle(String s) {
        style = Integer.parseInt(s);
    }

    public void setGrid(String s) {
        grid = Integer.parseInt(s);
    }

    public void setUnits(String s) {
        units = Integer.parseInt(s);
    }

    public void setColorWire(String s) {
        color_wire = Integer.parseInt(s, 16);
        color_wire =
                ((color_wire & 0xFF) << 16)
                        | (color_wire & 0xFF00)
                        | ((color_wire & 0xFF0000) >> 16);
    }

    public void setColorbus(String s) {
        color_bus = Integer.parseInt(s, 16);
        color_bus =
                ((color_bus & 0xFF) << 16) | (color_bus & 0xFF00) | ((color_bus & 0xFF0000) >> 16);
    }

    public void setColorJunction(String s) {
        color_junction = Integer.parseInt(s, 16);
        color_junction =
                ((color_junction & 0xFF) << 16)
                        | (color_junction & 0xFF00)
                        | ((color_junction & 0xFF0000) >> 16);
    }

    public void setColorNoconnect(String s) {
        color_noconnect = Integer.parseInt(s, 16);
        color_noconnect =
                ((color_noconnect & 0xFF) << 16)
                        | (color_noconnect & 0xFF00)
                        | ((color_noconnect & 0xFF0000) >> 16);
    }

    public void setColorLabel(String s) {
        color_label = Integer.parseInt(s, 16);
        color_label =
                ((color_label & 0xFF) << 16)
                        | (color_label & 0xFF00)
                        | ((color_label & 0xFF0000) >> 16);
    }

    public void setColorPower(String s) {
        color_power = Integer.parseInt(s, 16);
        color_power =
                ((color_power & 0xFF) << 16)
                        | (color_power & 0xFF00)
                        | ((color_power & 0xFF0000) >> 16);
    }

    public void setColorPin(String s) {
        color_pin = Integer.parseInt(s, 16);
        color_pin =
                ((color_pin & 0xFF) << 16) | (color_pin & 0xFF00) | ((color_pin & 0xFF0000) >> 16);
    }

    public void setColorHiddenPin(String s) {
        color_hidden_pin = Integer.parseInt(s, 16);
        color_hidden_pin =
                ((color_hidden_pin & 0xFF) << 16)
                        | (color_hidden_pin & 0xFF00)
                        | ((color_hidden_pin & 0xFF0000) >> 16);
    }
}
