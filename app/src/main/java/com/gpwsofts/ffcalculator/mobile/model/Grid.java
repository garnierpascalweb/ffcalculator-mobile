package com.gpwsofts.ffcalculator.mobile.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 * Modele pour une grille telles que définies dans le tableau Json
 */
public class Grid implements Comparable {
    private String code;
    private int priority;
    private String logo;
    private String libelle;
    private List<String> vues;
    private String type;
    private String cal;
    private int maxPos;
    private int totalpts;
    private List<Integer> pts;

    public Grid() {
        vues = new ArrayList<>();
        pts = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public int getPriority() {
        return priority;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<String> getVues() {
        return vues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxPos() {
        return maxPos;
    }

    public List<Integer> getPts() {
        return pts;
    }

    public void setPts(List<Integer> pts) {
        this.pts = pts;
    }

    @Override
    public int compareTo(Object other) {
        return Integer.compare(priority, ((Grid) other).getPriority());
    }

    /**
     * @return la valeur du spinner value
     * @since 1.0.0
     */
    public String getSpinnerItemValue() {
        return libelle + " (" + code + ")";
    }
}
