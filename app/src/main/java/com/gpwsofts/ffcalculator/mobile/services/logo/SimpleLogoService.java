package com.gpwsofts.ffcalculator.mobile.services.logo;

import android.content.res.Resources;

import com.gpwsofts.ffcalculator.mobile.R;

import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, Logo> logos;

    private Resources res;

    public SimpleLogoService(Resources res) {
        this.res = res;
        logos = new HashMap<>();
        logos.put(res.getString(R.string.logo_elite),  new Logo(res.getColor(R.color.logo_elite), res.getString(R.string.logo_elite)));
        logos.put(res.getString(R.string.logo_open_1_2_3),  new Logo(res.getColor(R.color.logo_open_1_2_3), res.getString(R.string.logo_open_1_2_3)));
        logos.put(res.getString(R.string.logo_open_1_2),  new Logo(res.getColor(R.color.logo_open_1_2), res.getString(R.string.logo_open_1_2)));
        logos.put(res.getString(R.string.logo_open_2_3),  new Logo(res.getColor(R.color.logo_open_2_3), res.getString(R.string.logo_open_2_3)));
        logos.put(res.getString(R.string.logo_open_3),  new Logo(res.getColor(R.color.logo_open_3), res.getString(R.string.logo_open_3)));
        logos.put(res.getString(R.string.logo_u17),  new Logo(res.getColor(R.color.logo_u17), res.getString(R.string.logo_u17)));
        logos.put(res.getString(R.string.logo_u19),  new Logo(res.getColor(R.color.logo_u19), res.getString(R.string.logo_u19)));
        logos.put(res.getString(R.string.logo_u23),  new Logo(res.getColor(R.color.logo_u23), res.getString(R.string.logo_u23)));
        logos.put(res.getString(R.string.logo_cdfn1),  new Logo(res.getColor(R.color.logo_cdfn1), res.getString(R.string.logo_cdfn1)));
        logos.put(res.getString(R.string.logo_cdfn2),  new Logo(res.getColor(R.color.logo_cdfn2), res.getString(R.string.logo_cdfn2)));
        logos.put(res.getString(R.string.logo_cdfn3),  new Logo(res.getColor(R.color.logo_cdfn3), res.getString(R.string.logo_cdfn3)));
    }

    public Logo getLogo(String idLogo) {
        return logos.get(idLogo);
    }
}
