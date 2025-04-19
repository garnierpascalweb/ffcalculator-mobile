package com.gpwsofts.ffcalculator.mobile.model.grid;

import java.util.List;

/**
 * Interface grille
 * @since 1.0.0
 */
public interface IGrid extends Comparable<IGrid> {
    String getCode();
    int getPriority();
    String getLogo();
    void setLogo(String logo);
    String getLibelle();
    void setLibelle(String libelle);
    List<String> getVues();
    String getType();
    void setType(String type);
    int getMaxPos();
    List<Integer> getPts();
    void setPts(List<Integer> pts);
    String getSpinnerItemValue();
}
