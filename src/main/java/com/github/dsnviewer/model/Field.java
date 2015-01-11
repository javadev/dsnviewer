package com.github.dsnviewer.model;

public class Field {
  private String name, _default, ft, value, pos;

    public void setName(String name) {
        this.name = name;
    }
    public void setFt(String ft) {
        this.ft = ft;
    }
    public void setPos(String pos) {
        this.pos = pos;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public void set_default(String _default) {
        this._default = _default;
    }
}
