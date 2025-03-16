package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.model.Vue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0
 * Service a propos des vues
 */
public class VueService implements IVueService {

    public static final String CODE_VUE_GENERALE = "G";
    public static final String CODE_VUE_ELITE = "E";
    public static final String CODE_VUE_OPEN_1 = "O1";
    public static final String CODE_VUE_OPEN_2 = "O2";
    public static final String CODE_VUE_OPEN_3 = "O3";
    public static final String CODE_VUE_U23 = "U23";
    public static final String CODE_VUE_U19 = "U19";
    public static final String CODE_VUE_U17 = "U17";
    public static final String CODE_VUE_ACCESS = "A";
    /**
     * La liste des vues deja chargée (lazy loading)
     * String : le code de la vue (exemple O2)
     * Vue : l'instance de la vue
     */
    private Map<String,Vue> listVues;

    /**
     * Table de mapping entre un bouton id de menu et une vue
     * @since 1.0.0
     */
    private final Map<Integer,String> vuesMapping;

    public VueService(){
        vuesMapping = new HashMap<>();
        vuesMapping.put(R.id.idMenuItemGeneral, VueService.CODE_VUE_GENERALE);
        vuesMapping.put(R.id.idMenuItemElite, VueService.CODE_VUE_ELITE);
        vuesMapping.put(R.id.idMenuItemOpen1, VueService.CODE_VUE_OPEN_1);
        vuesMapping.put(R.id.idMenuItemOpen2, VueService.CODE_VUE_OPEN_2);
        vuesMapping.put(R.id.idMenuItemOpen3, VueService.CODE_VUE_OPEN_3);
        vuesMapping.put(R.id.idMenuItemAccess, VueService.CODE_VUE_ACCESS);
        vuesMapping.put(R.id.idMenuItemU23, VueService.CODE_VUE_U23);
        vuesMapping.put(R.id.idMenuItemU19, VueService.CODE_VUE_U19);
        vuesMapping.put(R.id.idMenuItemU17, VueService.CODE_VUE_U17);
        listVues = new HashMap<>();
    }

    public Vue getVueInstance(String codeVue){
        Vue vue = listVues.get(codeVue);
        if (null == vue) {
            vue = new Vue();
            vue.setCode(codeVue);
            vue.setName(getReadableName(codeVue));
            vue.setIndexInComboMenu(getIndexInMenu(codeVue));
            vue.setMapClass(getClassToMap(codeVue));
            listVues.put(codeVue, vue);
        }
        return vue;
    }

    /**
     * Rend la vue a affecter selon le menu item de vue selectionnée, si non trouvé, générale
     * @param menuItemId l'id de l'item dans le menu
     * @return le codeVue du menuItem
     */
    public String getCodeVueFromMenuItem(int menuItemId){
        String codeVue = vuesMapping.get(menuItemId);
        if (null == codeVue)
            codeVue = VueService.CODE_VUE_GENERALE;
        return codeVue;
    }

    private int getIndexInMenu(String codeVue) {
        int indexToSelect;
        switch (codeVue) {
            case CODE_VUE_ELITE: {
                indexToSelect = 4;
                break;
            }
            case CODE_VUE_OPEN_1: {
                indexToSelect = 3;
                break;
            }
            case CODE_VUE_OPEN_2: {
                indexToSelect = 2;
                break;
            }
            case CODE_VUE_OPEN_3: {
                indexToSelect = 1;
                break;
            }
            case CODE_VUE_U23: {
                indexToSelect = 5;
                break;
            }
            case CODE_VUE_U19: {
                indexToSelect = 6;
                break;
            }
            case CODE_VUE_U17: {
                indexToSelect = 7;
                break;
            }
            case CODE_VUE_ACCESS: {
                indexToSelect = 8;
                break;
            }
            default: {
                indexToSelect = 0;
            }
        }
        return indexToSelect;
    }

    private String getReadableName(String codeVue) {
        String name;
        switch (codeVue) {
            case CODE_VUE_ELITE: {
                name = "Elite";
                break;
            }
            case CODE_VUE_OPEN_1: {
                name = "Open 1";
                break;
            }
            case CODE_VUE_OPEN_2: {
                name = "Open 2";
                break;
            }
            case CODE_VUE_OPEN_3: {
                name = "Open 3";
                break;
            }
            case CODE_VUE_U23: {
                name = "U23";
                break;
            }
            case CODE_VUE_U19: {
                name = "U19";
                break;
            }
            case CODE_VUE_U17: {
                name = "U17";
                break;
            }
            case CODE_VUE_ACCESS: {
                name = "Access";
                break;
            }
            default: {
                name = "Générale";
            }
        }
        return name;
    }

    public String getClassToMap(String codeVue) {
        String classToMap;
        if (codeVue.equals(CODE_VUE_U17)) {
            classToMap = "U17";
        } else {
            classToMap = "H";
        }
        //TODO 1.0.0 H et U17 devraient etre des constantes ou des Strings.xml
        return classToMap;
    }

    public boolean isU17(String codeVue){
        return codeVue.equals(CODE_VUE_U17);
    }

    @Override
    public void clean() {
        if (vuesMapping != null){
            vuesMapping.clear();
        }
        if (listVues != null){
            listVues.clear();
        }
    }
}
