package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

public interface IVueService extends ICleanableService {
    public Vue createVue(String codeVue);
    public String getCodeVueFromMenuItem(int menuItemId);
    public boolean isU17(String codeVue);
}
