package com.gpwsofts.ffcalculator.mobile.services.api.business.grid;

import static org.junit.Assert.assertNotNull;

import com.gpwsofts.ffcalculator.mobile.model.Grid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SimpleGridServiceTest {
    @Test
    public void getGrids() {
        SimpleGridService sgs = new SimpleGridService();
        List<Grid> grids = sgs.getGrids();
        assertNotNull("la liste des grilles ne devrait pas etre nulle", grids);
        System.out.println(grids.size()  + " grilles chargées");
    }
}
