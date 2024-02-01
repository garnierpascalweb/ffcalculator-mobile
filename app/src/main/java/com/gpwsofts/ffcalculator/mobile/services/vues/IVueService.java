package com.gpwsofts.ffcalculator.mobile.services.vues;

import java.util.ArrayList;

/**
 * Interface de service vue
 * @since 1.0.0
 */
public interface IVueService {

    /**
     * La vue Générale
     */
    public static final String GENERALE = "G";
    /**
     * La vue Elite
     */
    public static final String ELITE = "E";
    /**
     * La vue Open 1
     */
    public static final String OPEN_1 = "O1";
    /**
     * La vue Open 2
     */
    public static final String OPEN_2 = "O2";
    /**
     * La Vue Open 3
     */
    public static final String OPEN_3 = "O3";
    /**
     * La vue U19
     */
    public static final String U19 = "U19";
    /**
     * La Vue U17
     */
    public static final String U17 = "U17";
    /**
     * La Vue Access
     */
    public static final String ACCESS = "A";

    /**
     * Rend la liste des items de la combobox de choix de course, pour une vue sélectionnée
     * @since 1.0.0
     * @param vue
     * @return
     */
    public ArrayList<String> getComboboxClassesForVue(String vue);
}
