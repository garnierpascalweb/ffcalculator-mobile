package com.gpwsofts.ffcalculator.mobile.services.season;

import com.gpwsofts.ffcalculator.mobile.model.IResult;
import com.gpwsofts.ffcalculator.mobile.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock pour le service Season
 * @since 1.0.0
 */
public class MockSeasonService extends AbstractSeasonService {
    private static final int NUMBER_OF_RESULTS = 7;
    @Override
    public List<IResult> getResults() {
        List<IResult> results = null;
        results = new ArrayList<IResult>();
        for (int i=0; i<NUMBER_OF_RESULTS; i++){
            results.add(getMockedResult());
        }
        return results;
    }

    protected IResult getMockedResult(){
        IResult result = null;
        result = new Result();
        result.setIdClasse("1.24.1");
        result.setLibelle("Open 2/3");
        result.setPlace("Saint Rambert d'Albon");
        result.setPos(4);
        result.setPrts(137);
        result.setPts(106.75);
        return result;
    }
}
