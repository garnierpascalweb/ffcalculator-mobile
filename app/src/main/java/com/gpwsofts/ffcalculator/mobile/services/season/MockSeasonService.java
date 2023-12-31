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

    public MockSeasonService(){
        super();
        getMockedResults();
    }


    protected void getMockedResults(){
        // Open 2/3
        IResult mockResult1 = new Result();
        mockResult1.setPlace("St Romain de Popey");
        mockResult1.setLogo("Open 2/3");
        mockResult1.setPts(10.29);
        mockResult1.setPrts(147);
        mockResult1.setPos(16);
        mockResult1.setLibelle("Open 2/3 Access");
        mockResult1.setIdClasse("1.25.0");
        listResult.add(mockResult1);
        IResult mockResult2 = new Result();
        mockResult2.setPlace("Mizirieux");
        mockResult2.setLogo("Open 2/3");
        mockResult2.setPts(23.01);
        mockResult2.setPrts(177);
        mockResult2.setPos(11);
        mockResult2.setLibelle("Open 2/3 Access");
        mockResult2.setIdClasse("1.25.0");
        listResult.add(mockResult2);
        IResult mockResult3 = new Result();
        mockResult3.setPlace("Cusset");
        mockResult3.setLogo("Open 2/3");
        mockResult3.setPts(70);
        mockResult3.setPrts(56);
        mockResult3.setPos(2);
        mockResult3.setLibelle("Open 2/3 Access");
        mockResult3.setIdClasse("1.25.0");
        listResult.add(mockResult3);
        IResult mockResult4 = new Result();
        mockResult4.setPlace("St Rambert d'Albon");
        mockResult4.setLogo("Open 2/3");
        mockResult4.setPts(25.16);
        mockResult4.setPrts(148);
        mockResult4.setPos(8);
        mockResult4.setLibelle("Open 2/3 Access");
        mockResult4.setIdClasse("1.25.0");
        listResult.add(mockResult4);
        IResult mockResult5 = new Result();
        mockResult5.setPlace("Roanne");
        mockResult5.setLogo("Open 1/2/3");
        mockResult5.setPts(5.32);
        mockResult5.setPrts(38);
        mockResult5.setPos(15);
        mockResult5.setLibelle("Open 1/2/3 Access");
        mockResult5.setIdClasse("1.24.2");
        listResult.add(mockResult5);
        IResult mockResult6 = new Result();
        mockResult6.setPlace("Arles");
        mockResult6.setLogo("Open 3");
        mockResult6.setPts(8);
        mockResult6.setPrts(80);
        mockResult6.setPos(14);
        mockResult6.setLibelle("Open 3 Access");
        mockResult6.setIdClasse("1.25.1");
        listResult.add(mockResult6);
        // Cadet
        IResult mockResult7 = new Result();
        mockResult7.setPlace("St Amand Montrond");
        mockResult7.setLogo("U17");
        mockResult7.setPts(39.15);
        mockResult7.setPrts(87);
        mockResult7.setPos(2);
        mockResult7.setLibelle("U17 - Cadet");
        mockResult7.setIdClasse("1.31");
        listResult.add(mockResult7);
        // elite
        IResult mockResult8 = new Result();
        mockResult8.setPlace("Hyeres");
        mockResult8.setLogo("Elite");
        mockResult8.setPts(5.76);
        mockResult8.setPrts(48);
        mockResult8.setPos(19);
        mockResult8.setLibelle("Elite Open 1/2/3");
        mockResult8.setIdClasse("1.12.6");
        listResult.add(mockResult8);
        // Federale juniors
        IResult mockResult9 = new Result();
        mockResult9.setPlace("St Jean de Védas");
        mockResult9.setLogo("U19");
        mockResult9.setPts(140.3);
        mockResult9.setPrts(122);
        mockResult9.setPos(3);
        mockResult9.setLibelle("Federale U19");
        mockResult9.setIdClasse("1.14");
        listResult.add(mockResult9);
    }

    protected void getMyResults(){
        IResult mockResult1 = new Result();
        mockResult1.setPlace("Marcigny (71)");
        mockResult1.setPts(3.36);
        mockResult1.setPrts(48);
        mockResult1.setPos(16);
        mockResult1.setLibelle("Open 2/3");
        mockResult1.setIdClasse("1.25.0");
        IResult mockResult2 = new Result();
        mockResult2.setPlace("Voiron - TPV (38)");
        mockResult2.setPts(10.92);
        mockResult2.setPrts(78);
        mockResult2.setPos(14);
        mockResult2.setLibelle("Open 3");
        mockResult2.setIdClasse("2.25.1G");
        listResult.add(mockResult1);
        listResult.add(mockResult2);
    }
}
