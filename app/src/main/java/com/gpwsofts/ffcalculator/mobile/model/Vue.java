package com.gpwsofts.ffcalculator.mobile.model;

import android.util.Log;

/**
 * @since 1.0.0
 * Un model pour une vue
 */
public class Vue {

    private static final String TAG_NAME = "Vue";
    /**
     * Le code de la vue tel qu'il apparait dans le csv et les shared prefs,
     * G, E, O1, O2, O3...
     */
    private String code;
    /**
     * Le nom de la vue
     */
    private String name;
    /**
     * L'index dans la combo box
     */
    private int indexInComboMenu;
    /**
     * Le mapping sur un type de classement national
     * H (homme), U17 (cadets)
     */
    private String mapClass;

    public Vue() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIndexInComboMenu() {
        return indexInComboMenu;
    }

    public void setIndexInComboMenu(int indexInComboMenu) {
        this.indexInComboMenu = indexInComboMenu;
    }

    public String getMapClass() {
        return mapClass;
    }

    public void setMapClass(String mapClass) {
        this.mapClass = mapClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
