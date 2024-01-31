package com.gpwsofts.ffcalculator.mobile.services.logo;

import android.content.res.Resources;

import com.gpwsofts.ffcalculator.mobile.R;

public class SimpleLogoService implements ILogoService {
    private static final String TAG_NAME = "SimpleLogoService";
    private static Logo logoElite;
    private static Logo logoOpen123;
    private static Logo logoOpen12;
    private static Logo logoOpen23;
    private static Logo logoOpen3;
    private static Logo logoU17;
    private static Logo logoU19;
    private static Logo logoU23;

    private Resources res;
    public SimpleLogoService(Resources res){
        this.res = res;
        res.getString(R.string.logo_elite);
        res.getColor(R.color.logo_elite);
        logoElite = new Logo(res.getColor(R.color.logo_elite), res.getString(R.string.logo_elite));
        logoOpen123 = new Logo(res.getColor(R.color.logo_open_1_2_3), res.getString(R.string.logo_open_1_2_3));
        logoOpen12 = new Logo(res.getColor(R.color.logo_open_1_2), res.getString(R.string.logo_open_1_2));
        logoOpen23 = new Logo(res.getColor(R.color.logo_open_2_3), res.getString(R.string.logo_open_2_3));
        logoOpen3 = new Logo(res.getColor(R.color.logo_open_3), res.getString(R.string.logo_open_3));
        logoU17 = new Logo(res.getColor(R.color.logo_u17), res.getString(R.string.logo_u17));
        logoU19 = new Logo(res.getColor(R.color.logo_u19), res.getString(R.string.logo_u19));
        logoU23 = new Logo(res.getColor(R.color.logo_u23), res.getString(R.string.logo_u23));
    }

    public Logo getLogo(String idClasse){
        Logo logo = null;
        //TODO 1.0.0 calculer le bon logo
        return logoOpen3;
    }
}
