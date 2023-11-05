package com.gpwsofts.ffcalculator.mobile.model;

/**
 * Une implementation de Base de resultat a une course dans les points
 * @since 1.0.0
 */
public class Result implements IResult {
    protected String place;
    protected int pos;
    protected int prts;
    protected double pts;
    protected String idClasse;
    protected String libelle;

    public Result() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPrts() {
        return prts;
    }

    public void setPrts(int prts) {
        this.prts = prts;
    }

    public double getPts() {
        return pts;
    }

    public void setPts(double pts) {
        this.pts = pts;
    }

    public String getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(String idClasse) {
        this.idClasse = idClasse;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
