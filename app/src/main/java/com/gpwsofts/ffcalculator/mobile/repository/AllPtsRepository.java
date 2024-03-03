package com.gpwsofts.ffcalculator.mobile.repository;

public class AllPtsRepository {
    private static AllPtsRepository instance;

    public static AllPtsRepository getInstance(){
        if (null == instance)
            instance = new AllPtsRepository();
        return instance;
    }

    private AllPtsRepository(){

    }
}
