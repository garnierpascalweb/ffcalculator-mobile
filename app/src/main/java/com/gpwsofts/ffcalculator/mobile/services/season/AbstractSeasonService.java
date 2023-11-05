package com.gpwsofts.ffcalculator.mobile.services.season;

import com.gpwsofts.ffcalculator.mobile.model.IResult;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSeasonService implements ISeasonService{
    protected List<IResult> listResult = null;

    public AbstractSeasonService(){
        listResult =  listResult = new ArrayList<IResult>(15);
    }
    @Override
    public List<IResult> getResults() {
        return listResult;
    }

}
