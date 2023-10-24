package com.gpwsofts.ffcalculator.mobile.services.season;

import com.gpwsofts.ffcalculator.mobile.model.IResult;

import java.util.List;

public abstract class AbstractSeasonService implements ISeasonService{
    @Override
    public abstract List<IResult> getResults();

}
