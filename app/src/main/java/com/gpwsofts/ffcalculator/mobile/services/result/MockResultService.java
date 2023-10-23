package com.gpwsofts.ffcalculator.mobile.services.result;

public class MockResultService extends AbstractResultService {
    public static final double MOCK_VALUE = 128.65;
    public double getPts(int pos, int prts, String idClasse){
        return MOCK_VALUE;
    }
}
