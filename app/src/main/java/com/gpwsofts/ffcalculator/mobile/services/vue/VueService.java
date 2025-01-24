package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.model.Vue;

import java.util.HashMap;
import java.util.Map;

/**
 * Service a priopos des vues
 */
public class VueService implements IVueService {
    /**
     * Table de mapping entre un bouton id de menu et une vue
     * @since 1.0.0
     */
    private Map<Integer,String> vuesMapping;

    public VueService(){
        vuesMapping = new HashMap<Integer,String>();
        vuesMapping.put(R.id.idMenuItemGeneral, Vue.GENERALE);
        vuesMapping.put(R.id.idMenuItemElite, Vue.ELITE);
        vuesMapping.put(R.id.idMenuItemOpen1, Vue.OPEN_1);
        vuesMapping.put(R.id.idMenuItemOpen2, Vue.OPEN_2);
        vuesMapping.put(R.id.idMenuItemOpen3, Vue.OPEN_3);
        vuesMapping.put(R.id.idMenuItemAccess, Vue.ACCESS);
        vuesMapping.put(R.id.idMenuItemU23, Vue.U23);
        vuesMapping.put(R.id.idMenuItemU19, Vue.U19);
        vuesMapping.put(R.id.idMenuItemU17, Vue.U17);
    }

    /**
     * Rend la vue a affecter selon le menu item de vue selectionnée, si non trouvé, générale
     * @param menuItemId
     * @return
     */
    public String getVueFromMenuItem(int menuItemId){
        String vue = vuesMapping.get(menuItemId);
        if (null == vue)
            vue = Vue.GENERALE;
        return vue;
    }

}
