package com.gpwsofts.ffcalculator.mobile.services.result;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Implemntation Random Mock du service de Resultat
 * @since 1.0.0
 */
public class RandomResultService extends AbstractResultService{
    private DecimalFormat df =  new DecimalFormat("#.##");
    @Override
    public double getPts(int pos, int prts, String idClasse) {
        BigDecimal myRounded = new BigDecimal(Math.random()*200).setScale(2, BigDecimal.ROUND_HALF_UP);
        double pts = myRounded.doubleValue();
        System.out.println("points : " + pts);
        return pts;
    }
}
