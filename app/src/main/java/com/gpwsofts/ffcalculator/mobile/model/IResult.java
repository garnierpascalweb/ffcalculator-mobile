package com.gpwsofts.ffcalculator.mobile.model;

/**
 * Une interface pour le modele Result
 * @since 1.0.0
 */
public interface IResult {
    public String getPlace();

    public void setPlace(String place) ;
    public String getLogo() ;
    public void setLogo(String logo);
    public int getPos() ;

    public void setPos(int pos) ;

    public int getPrts() ;

    public void setPrts(int prts) ;

    public double getPts() ;

    public void setPts(double pts) ;

    public String getIdClasse() ;

    public void setIdClasse(String idClasse) ;

    public String getLibelle() ;

    public void setLibelle(String libelle) ;
}
