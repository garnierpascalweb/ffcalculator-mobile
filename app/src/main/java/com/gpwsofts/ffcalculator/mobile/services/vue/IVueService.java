package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

public interface IVueService extends ICleanableService {
    String CLASS_TYPE_H = "H";
    String CLASS_TYPE_U17 = "U17";
    String getClassTypeForCodeVue(String codeVue);
    String[] getVuesCodes();
    String[] getVuesLibelles();
    int getIndexFromCodeVue(String codeVue);
    String getLibelleFromCodeVue(String codeVue);
}
