package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.model.vue.Vue;
import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

public interface IVueService extends ICleanableService {
    Vue getVueInstance(String codeVue);
    String getCodeVueFromMenuItem(int menuItemId);
    boolean isU17(String codeVue);
}
