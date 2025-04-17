package com.gpwsofts.ffcalculator.mobile.model.vue;

/**
 * @since 1.0.0
 * Un model pour une vue
 */
public class Vue implements IVue {

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

    @Override
    public String getCode() {
        return code;
    }
    @Override
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public int getIndexInComboMenu() {
        return indexInComboMenu;
    }
    @Override
    public void setIndexInComboMenu(int indexInComboMenu) {
        this.indexInComboMenu = indexInComboMenu;
    }
    @Override
    public String getMapClass() {
        return mapClass;
    }
    @Override
    public void setMapClass(String mapClass) {
        this.mapClass = mapClass;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
