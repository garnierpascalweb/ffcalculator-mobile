package com.gpwsofts.ffcalculator.mobile.services.result;

import java.text.DecimalFormat;

/**
 * Implemntation Random Mock du service de Resultat
 * @since 1.0.0
 */
public class RandomResultService extends AbstractResultService{
    private DecimalFormat df =  new DecimalFormat("#.##");
    @Override
    public double getPts(int pos, int prts, String idClasse) {
        double rand = Math.random()*200;
        return Double.valueOf(df.format(rand));
    }
}
