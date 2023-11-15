package com.gpwsofts.ffcalculator.mobile.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Result {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public String idClasse;
    public String place;
    public String logo;
    public int pos;
    public int prts;
    public double pts;
    public String libelle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(String idClasse) {
        this.idClasse = idClasse;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
