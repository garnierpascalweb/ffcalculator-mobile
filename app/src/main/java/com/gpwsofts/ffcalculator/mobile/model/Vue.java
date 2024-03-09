package com.gpwsofts.ffcalculator.mobile.model;

/**
 * @since 1.0.0
 * Un model pour une vue
 */
public class Vue {
    public static final String GENERALE = "G";
    public static final String ELITE = "E";
    public static final String OPEN_1 = "O1";
    public static final String OPEN_2 = "O2";
    public static final String OPEN_3 = "O3";
    public static final String U19 = "U19";
    public static final String U17 = "U17";
    public static final String ACCESS = "A";
    /**
     * Le nom de la vue
     */
    /**
     * Le code de la vue tel qu'il apparait dans le csv et les shared prefs,
     * G, E, O1, O2, O3...
     */
    private String code;
    /**
     * L'index dans la combo box
     */
    private int indexInComboMenu;
    /**
     * Le mapping sur un type de classement national
     * H (homme), U17 (cadets)
     */
    private String mapClass;

    public Vue(String code) {
        this.code = code;
        this.indexInComboMenu = getIndexInMenu(code);
        this.mapClass = getClassToMap(code);
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

    private int getIndexInMenu(String vue) {
        int indexToSelect = 0;
        switch (vue) {
            case ELITE: {
                indexToSelect = 4;
                break;
            }
            case OPEN_1: {
                indexToSelect = 3;
                break;
            }
            case OPEN_2: {
                indexToSelect = 2;
                break;
            }
            case OPEN_3: {
                indexToSelect = 1;
                break;
            }
            case U19: {
                indexToSelect = 5;
                break;
            }
            case U17: {
                indexToSelect = 6;
                break;
            }
            case ACCESS: {
                indexToSelect = 7;
                break;
            }
            default: {
                indexToSelect = 0;
            }
        }
        return indexToSelect;
    }

    private String getClassToMap(String vue) {
        String classToMap = "H";
        switch (vue) {
            case U17: {
                classToMap = "U17";
                break;
            }
            default: {
                classToMap = "G";
            }
        }
        return classToMap;
    }
}
