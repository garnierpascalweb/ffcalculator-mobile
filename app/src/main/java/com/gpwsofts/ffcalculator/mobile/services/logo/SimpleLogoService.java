package com.gpwsofts.ffcalculator.mobile.services.logo;

import android.content.res.Resources;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.constants.IdClassesConstants;

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

    public SimpleLogoService(Resources res) {
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

    public Logo getLogo(String idClasse) {
        //TODO 1.0.0 : referencer tous les champs statiques de IdClassesConstants
        //todo 1.0.0: non !
        Logo logo = logoU17;
        switch (idClasse) {
            /**
             * Logo Elite
             */
            case IdClassesConstants.ELITE_CLASSE_1:
            case IdClassesConstants.ELITE_CLASSE_1_E:
            case IdClassesConstants.ELITE_CLASSE_1_G:
            case IdClassesConstants.ELITE_CLASSE_2:
            case IdClassesConstants.ELITE_CLASSE_2_E:
            case IdClassesConstants.ELITE_CLASSE_2_G:
            case IdClassesConstants.ELITE_NATIONALE:
            case IdClassesConstants.ELITE_NATIONALE_ETAPE:
            case IdClassesConstants.ELITE_NATIONALE_GENERAL:
            case IdClassesConstants.ELITE_OPEN_1:
            case IdClassesConstants.ELITE_OPEN_1_E:
            case IdClassesConstants.ELITE_OPEN_1_G:
            case IdClassesConstants.ELITE_OPEN_1_2:
            case IdClassesConstants.ELITE_OPEN_1_2_E:
            case IdClassesConstants.ELITE_OPEN_1_2_G:
            case IdClassesConstants.ELITE_OPEN_1_2_3:
            case IdClassesConstants.ELITE_OPEN_1_2_3_E:
            case IdClassesConstants.ELITE_OPEN_1_2_3_G:
            case IdClassesConstants.ELITE_OPEN_1_2_3_ACCESS:
            case IdClassesConstants.ELITE_OPEN_1_2_3_ACCESS_E:
            case IdClassesConstants.ELITE_OPEN_1_2_3_ACCESS_G:
                logo = logoElite;
                break;
            /**
             * Logo Open 1/2/3
             */
            case IdClassesConstants.OPEN_1_2_3:
            case IdClassesConstants.OPEN_1_2_3_E:
            case IdClassesConstants.OPEN_1_2_3_G:
            case IdClassesConstants.OPEN_1_2_3_ACCESS:
            case IdClassesConstants.OPEN_1_2_3_ACCESS_E:
            case IdClassesConstants.OPEN_1_2_3_ACCESS_G:
                logo = logoOpen123;
                break;
            /**
             * Logo Open 1/2
             */
            case IdClassesConstants.OPEN_1_2:
            case IdClassesConstants.OPEN_1_2_E:
            case IdClassesConstants.OPEN_1_2_G:
                logo = logoOpen12;
                break;
            /**
             * Logo Open 2/3
             */
            case IdClassesConstants.OPEN_2_3_ACCESS:
            case IdClassesConstants.OPEN_2_3_ACCESS_E:
            case IdClassesConstants.OPEN_2_3_ACCESS_G:
                logo = logoOpen23;
                break;
            /**
             * Logo Open 3
             */
            case IdClassesConstants.OPEN_3_ACCESS:
            case IdClassesConstants.OPEN_3_ACCESS_E:
            case IdClassesConstants.OPEN_3_ACCESS_G:
                logo = logoOpen3;
                break;
            /**
             * Logo U17
             */
            case IdClassesConstants.CHAMPIONNAT_DE_FRANCE_U17:
            case IdClassesConstants.CHAMPIONNAT_REGIONAL_U17:
            case IdClassesConstants.CHAMPIONNAT_DEPARTEMENTAL_U17:
            case IdClassesConstants.U17:
            case IdClassesConstants.COUPE_DE_FRANCE_U17:
                logo = logoU17;
                break;
            /**
             * Logo U19
             */
            case IdClassesConstants.FEDERALE_U19:
            case IdClassesConstants.FEDERALE_U19_ETAPE:
            case IdClassesConstants.FEDERALE_U19_GENERAL:
            case IdClassesConstants.U19:
            case IdClassesConstants.U19_E:
            case IdClassesConstants.U19_G:
            case IdClassesConstants.CHAMPIONNAT_DE_FRANCE_U19:
            case IdClassesConstants.CHAMPIONNAT_DE_FRANCE_U19_CLM:
            case IdClassesConstants.CHAMPIONNAT_REGIONAL_U19:
            case IdClassesConstants.CHAMPIONNAT_DEPARTEMENTAL_U19:
                logo = logoU19;
                break;
            /**
             * Logo U23
             */
            case IdClassesConstants.FEDERALE_U23:
            case IdClassesConstants.FEDERALE_U23_ETAPE:
            case IdClassesConstants.FEDERALE_U23_GENERAL:
            case IdClassesConstants.U23:
            case IdClassesConstants.U23_E:
            case IdClassesConstants.U23_G:
            case IdClassesConstants.CHAMPIONNAT_DE_FRANCE_U23:
            case IdClassesConstants.CHAMPIONNAT_DE_FRANCE_U23_CLM:
            case IdClassesConstants.CHAMPIONNAT_REGIONAL_U23:
            case IdClassesConstants.CHAMPIONNAT_DEPARTEMENTAL_U23:
            case IdClassesConstants.U23_CLASSE_2:
            case IdClassesConstants.U23_CLASSE_2_E:
            case IdClassesConstants.U23_CLASSE_2_G:
                logo = logoU23;
                break;
            default:

        }
        return logo;
    }
}
