package com.gpwsofts.ffcalculator.mobile.services.vue;

import java.util.Arrays;

/**
 * @since 1.0.0
 * Service a propos des vues
 */
public class VueService extends AbstractVueService {

    public VueService(String[] vuesLibelles, String[] vuesCodes){
        super(vuesLibelles, vuesCodes);
    }

    @Override
    public String getClassTypeForCodeVue(String codeVue) {
        final String classTypeForCodeView;
        if (codeVue.equals(CLASS_TYPE_U17))
            classTypeForCodeView = CLASS_TYPE_U17;
        else classTypeForCodeView = CLASS_TYPE_H;
        return classTypeForCodeView;
    }


    @Override
    public int getIndexFromCodeVue(String codeVue) {
        return Arrays.asList(vuesCodes).indexOf(codeVue);
    }

    @Override
    public String getLibelleFromCodeVue(String codeVue) {
       return Arrays.asList(vuesLibelles).get(getIndexFromCodeVue(codeVue));
    }

    @Override
    public void clean() {

    }
}
