package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.model.Vue;

import java.util.HashMap;
import java.util.Map;

/**
 * Service a priopos des vues
 */
public class VueService implements IVueService {

    public static final String GENERALE = "G";
    public static final String ELITE = "E";
    public static final String OPEN_1 = "O1";
    public static final String OPEN_2 = "O2";
    public static final String OPEN_3 = "O3";
    public static final String U23 = "U23";
    public static final String U19 = "U19";
    public static final String U17 = "U17";
    public static final String ACCESS = "A";

    /**
     * Table de mapping entre un bouton id de menu et une vue
     * @since 1.0.0
     */
    private Map<Integer,String> vuesMapping;

    public VueService(){
        vuesMapping = new HashMap<Integer,String>();
        vuesMapping.put(R.id.idMenuItemGeneral, VueService.GENERALE);
        vuesMapping.put(R.id.idMenuItemElite, VueService.ELITE);
        vuesMapping.put(R.id.idMenuItemOpen1, VueService.OPEN_1);
        vuesMapping.put(R.id.idMenuItemOpen2, VueService.OPEN_2);
        vuesMapping.put(R.id.idMenuItemOpen3, VueService.OPEN_3);
        vuesMapping.put(R.id.idMenuItemAccess, VueService.ACCESS);
        vuesMapping.put(R.id.idMenuItemU23, VueService.U23);
        vuesMapping.put(R.id.idMenuItemU19, VueService.U19);
        vuesMapping.put(R.id.idMenuItemU17, VueService.U17);
    }

    public Vue createVue(String codeVue){
        Vue vue = new Vue();
        vue.setCode(codeVue);
        vue.setName(getName(codeVue));
        vue.setIndexInComboMenu(getIndexInMenu(codeVue));
        vue.setMapClass(getClassToMap(codeVue));
        return vue;
    }

    /**
     * Rend la vue a affecter selon le menu item de vue selectionnée, si non trouvé, générale
     * @param menuItemId
     * @return
     */
    public String getCodeVueFromMenuItem(int menuItemId){
        String codeVue = vuesMapping.get(menuItemId);
        if (null == codeVue)
            codeVue = VueService.GENERALE;
        return codeVue;
    }

    private int getIndexInMenu(String codeVue) {
        int indexToSelect = 0;
        switch (codeVue) {
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
            case U23: {
                indexToSelect = 5;
                break;
            }
            case U19: {
                indexToSelect = 6;
                break;
            }
            case U17: {
                indexToSelect = 7;
                break;
            }
            case ACCESS: {
                indexToSelect = 8;
                break;
            }
            default: {
                indexToSelect = 0;
            }
        }
        return indexToSelect;
    }

    private String getName(String codeVue) {
        String name = null;
        switch (codeVue) {
            case ELITE: {
                name = "Elite";
                break;
            }
            case OPEN_1: {
                name = "Open 1";
                break;
            }
            case OPEN_2: {
                name = "Open 2";
                break;
            }
            case OPEN_3: {
                name = "Open 3";
                break;
            }
            case U23: {
                name = "U23";
                break;
            }
            case U19: {
                name = "U19";
                break;
            }
            case U17: {
                name = "U17";
                break;
            }
            case ACCESS: {
                name = "Access";
                break;
            }
            default: {
                name = "Générale";
            }
        }
        return name;
    }

    private String getClassToMap(String codeVue) {
        String classToMap = "H";
        switch (codeVue) {
            case U17: {
                classToMap = "U17";
                break;
            }
            default: {
                classToMap = "H";
            }
        }
        return classToMap;
    }

}
