package com.gpwsofts.ffcalculator.mobile.services.vues;

import android.content.res.Resources;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;

import java.util.ArrayList;
import java.util.Arrays;


public class SimpleVueService implements IVueService {
    private static final String TAG_NAME = "SimpleVueService";
    private Resources res;
    public SimpleVueService(Resources res){
        this.res = res;
    }
    public ArrayList<String> getComboboxClassesForVue(String vue) {
        ArrayList<String> listClasses = new ArrayList<String>();
        switch (vue){
            case ELITE : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_E))));
                break;
            }
            case OPEN_1 : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_O1))));
                break;
            }
            case OPEN_2 : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_O2))));
                break;
            }
            case OPEN_3 : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_O3))));
                break;
            }
            case ACCESS : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_A))));
                break;
            }
            case U19 : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_U19))));
                break;
            }
            case U17 : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_U17))));
                break;
            }
            default : {
                Log.d(TAG_NAME, "chargement dans la liste deroulante des classes eligibles a la vue G car " + vue);
                listClasses.addAll(new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.classes_for_G))));
                break;
            }
        }
        //TODO 1.0.0 vue U23 ?
        return listClasses;
    }
}
