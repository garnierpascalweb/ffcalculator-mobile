package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.model.vue.IVue;
import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

public interface IVueService extends ICleanableService {
    IVue getVueInstance(String codeVue);
    String getCodeVueFromMenuItem(int menuItemId);
    boolean isU17(String codeVue);
}
