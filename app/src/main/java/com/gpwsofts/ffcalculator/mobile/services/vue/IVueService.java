package com.gpwsofts.ffcalculator.mobile.services.vue;

import com.gpwsofts.ffcalculator.mobile.model.Vue;

public interface IVueService {
    public Vue createVue(String codeVue);
    public String getCodeVueFromMenuItem(int menuItemId);
    public boolean isU17(String codeVue);
}
