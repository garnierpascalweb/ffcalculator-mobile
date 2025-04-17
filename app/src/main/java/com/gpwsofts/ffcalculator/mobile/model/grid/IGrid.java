package com.gpwsofts.ffcalculator.mobile.model.grid;

import java.util.List;

/**
 * Interface grille
 * @since 1.0.0
 */
public interface IGrid {
    public String getCode();
    public int getPriority();
    public String getLogo();
    public void setLogo(String logo);
    public String getLibelle();
    public void setLibelle(String libelle);
    public List<String> getVues();
    public String getType();
    public void setType(String type);
    public int getMaxPos();
    public List<Integer> getPts();
    public void setPts(List<Integer> pts);
    public String getSpinnerItemValue();
}
