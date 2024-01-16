package com.gpwsofts.ffcalculator.mobile.services.vues;

import android.content.res.Resources;

import java.util.ArrayList;

public interface IVueService {

    public static final String GENERALE = "G";
    public static final String ELITE = "E";
    public static final String OPEN_1 = "O1";
    public static final String OPEN_2 = "O2";
    public static final String OPEN_3 = "O3";
    public static final String U19 = "U19";
    public static final String U17 = "U17";
    public static final String ACCESS = "A";

    public ArrayList<String> getComboboxClassesForVue(String vue);
}
