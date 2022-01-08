package com.github.dsnviewer.model;

import java.util.Comparator;

public class DotYComparator implements Comparator {
    public int compare(Object ob1, Object ob2) {
        Dot dv1 = (Dot) ob1;
        Dot dv2 = (Dot) ob2;
        return Math.round(dv1.getY() - dv2.getY());
    }
}
