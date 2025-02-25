package com.gpwsofts.ffcalculator.mobile.services.logo;

import android.content.res.Resources;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.model.Logo;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;


public class SimpleLogoService implements ILogoService {
    private static final String TAG_NAME = "SimpleLogoService";

    private static Map<String, Logo> logos;

    private final Resources res;

    public SimpleLogoService(Resources res) {
        this.res = res;
        logos = new HashMap<>();
        logos.put(res.getString(R.string.logo_elite), new Logo(res.getColor(R.color.logo_elite), res.getString(R.string.logo_elite)));
        logos.put(res.getString(R.string.logo_open_1_2_3), new Logo(res.getColor(R.color.logo_open_1_2_3), res.getString(R.string.logo_open_1_2_3)));
        logos.put(res.getString(R.string.logo_open_1), new Logo(res.getColor(R.color.logo_open_1), res.getString(R.string.logo_open_1)));
        logos.put(res.getString(R.string.logo_open_1_2), new Logo(res.getColor(R.color.logo_open_1_2), res.getString(R.string.logo_open_1_2)));
        logos.put(res.getString(R.string.logo_open_2_3), new Logo(res.getColor(R.color.logo_open_2_3), res.getString(R.string.logo_open_2_3)));
        logos.put(res.getString(R.string.logo_open_3), new Logo(res.getColor(R.color.logo_open_3), res.getString(R.string.logo_open_3)));
        logos.put(res.getString(R.string.logo_u17), new Logo(res.getColor(R.color.logo_u17), res.getString(R.string.logo_u17)));
        logos.put(res.getString(R.string.logo_u19), new Logo(res.getColor(R.color.logo_u19), res.getString(R.string.logo_u19)));
        logos.put(res.getString(R.string.logo_u23), new Logo(res.getColor(R.color.logo_u23), res.getString(R.string.logo_u23)));
        logos.put(res.getString(R.string.logo_cdfn1), new Logo(res.getColor(R.color.logo_cdfn1), res.getString(R.string.logo_cdfn1)));
        logos.put(res.getString(R.string.logo_cdfn2), new Logo(res.getColor(R.color.logo_cdfn2), res.getString(R.string.logo_cdfn2)));
        logos.put(res.getString(R.string.logo_cdfn3), new Logo(res.getColor(R.color.logo_cdfn3), res.getString(R.string.logo_cdfn3)));
        logos.put(res.getString(R.string.logo_unknown), new Logo(res.getColor(R.color.logo_unknown), res.getString(R.string.logo_unknown)));
    }

    /**
     * @param idLogo id de logo, s'il est null, renvoi un logo par defaut
     * @return une instance de Logo pour un idLogo
     */
    public Logo getLogo(String idLogo) {
        Logo logo = null;
        if (null == idLogo){
            LogUtils.w(TAG_NAME, "idLogo null, renvoi du logo par defaut");
            logo = getUnknownLogo();
        } else {
            logo = logos.get(idLogo);
            if (null == logo){
                LogUtils.w(TAG_NAME, "aucun logo pour " + idLogo + ", renvoi du logo par defaut");
                logo = getUnknownLogo();
            }
        }
        return logo;
    }

    /**
     * le logo par defaut unknown
     * @return
     */
    public Logo getUnknownLogo(){
        return logos.get(res.getString(R.string.logo_unknown));
    }

    @Override
    public void clean() {
        if (logos != null){
            logos.clear();
        }
    }
}
