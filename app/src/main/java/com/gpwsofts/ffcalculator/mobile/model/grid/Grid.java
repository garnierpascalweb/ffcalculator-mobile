package com.gpwsofts.ffcalculator.mobile.model.grid;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 * Modele pour une grille telles que définies dans le tableau Json
 */
public class Grid implements IGrid {
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

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getLogo() {
        return logo;
    }

    @Override
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String getLibelle() {
        return libelle;
    }

    @Override
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public List<String> getVues() {
        return vues;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getMaxPos() {
        return maxPos;
    }

    @Override
    public List<Integer> getPts() {
        return pts;
    }

    @Override
    public void setPts(List<Integer> pts) {
        this.pts = pts;
    }
    /**
     * @return la valeur du spinner value
     * @since 1.0.0
     */
    @Override
    public String getSpinnerItemValue() {
        return libelle + " (" + code + ")";
    }

    @Override
    public int compareTo(IGrid o) {
        return Integer.compare(priority, o.getPriority());
    }
}
