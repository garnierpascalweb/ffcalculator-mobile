package com.gpwsofts.ffcalculator.mobile.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Result {
    @PrimaryKey
    public int id;
    public String idClasse;
    public String place;
    public String logo;
    public int pos;
    public int prts;
    public double pts;
    public String libelle;
}
