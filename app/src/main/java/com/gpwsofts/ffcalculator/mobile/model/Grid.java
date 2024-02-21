package com.gpwsofts.ffcalculator.mobile.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 * Modele pour une grille telles que définies dans le tableau Json
 */
public class Grid {
    public String code;
    public String logo;
    public String libelle;
    public List<String> vues;
    public String type;
    public String cal;
    public int maxPos;
    public int totalpts;
    public List<Integer> pts;

    public Grid() {
        vues = new ArrayList<String>();
        pts = new ArrayList<Integer>();
    }
}
