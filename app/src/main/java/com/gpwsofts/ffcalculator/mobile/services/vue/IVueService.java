package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

public interface IVueService extends ICleanableService {
    Vue createVue(String codeVue);
    String getCodeVueFromMenuItem(int menuItemId);
    boolean isU17(String codeVue);
}
