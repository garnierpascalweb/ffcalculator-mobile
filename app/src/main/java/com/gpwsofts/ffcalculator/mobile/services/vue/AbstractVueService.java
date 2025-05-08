package com.gpwsofts.ffcalculator.mobile.services.vue;

public abstract class AbstractVueService implements IVueService {
    protected String[] vuesLibelles;
    protected String[] vuesCodes;

    public AbstractVueService(String[] vuesLibelles, String[] vuesCodes){
        this.vuesLibelles = vuesLibelles;
        this.vuesCodes = vuesCodes;
    }

    public String[] getVuesCodes() {
        return vuesCodes;
    }

    public void setVuesCodes(String[] vuesCodes) {
        this.vuesCodes = vuesCodes;
    }

    public String[] getVuesLibelles() {
        return vuesLibelles;
    }

    public void setVuesLibelles(String[] vuesLibelles) {
        this.vuesLibelles = vuesLibelles;
    }
}
