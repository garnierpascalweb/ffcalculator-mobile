package com.gpwsofts.ffcalculator.mobile.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 * Modele pour une grille telles que définies dans le tableau Json
 */
public class Grid implements Comparable{
    public String code;
    public int priority;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public void setVues(List<String> vues) {
        this.vues = vues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public int getMaxPos() {
        return maxPos;
    }

    public void setMaxPos(int maxPos) {
        this.maxPos = maxPos;
    }

    public int getTotalpts() {
        return totalpts;
    }

    public void setTotalpts(int totalpts) {
        this.totalpts = totalpts;
    }

    public List<Integer> getPts() {
        return pts;
    }

    public void setPts(List<Integer> pts) {
        this.pts = pts;
    }

    @Override
    public int compareTo(Object other) {
        return Integer.compare(priority,((Grid)other).getPriority());
    }

    /**
     * @since 1.0.0
     * @return la valeur du spinner value
     */
    public String getSpinnerItemValue(){
        return new StringBuilder(libelle).append(" (").append(code).append(")").toString();
    }
}
