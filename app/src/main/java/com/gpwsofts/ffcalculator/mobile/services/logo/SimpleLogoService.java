package com.gpwsofts.ffcalculator.mobile.services.logo;

import android.content.res.Resources;

import com.gpwsofts.ffcalculator.mobile.R;

public class SimpleLogoService implements ILogoService {
    private static final String TAG_NAME = "SimpleLogoService";
    private static Logo logoElite;
    private Resources res;
    public SimpleLogoService(Resources res){
        this.res = res;
        res.getString(R.string.logo_elite);
        res.getColor(R.color.logo_elite);
        logoElite = new Logo(res.getColor(R.color.logo_elite), res.getString(R.string.logo_elite));
    }

    public Logo getLogo(String idClasse){
        Logo logo = null;
        return logoElite;
    }
}
