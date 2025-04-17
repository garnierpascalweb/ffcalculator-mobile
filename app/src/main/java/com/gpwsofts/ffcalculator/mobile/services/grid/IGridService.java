package com.gpwsofts.ffcalculator.mobile.services.grid;

import com.gpwsofts.ffcalculator.mobile.common.exception.InputLibelleFormatException;
import com.gpwsofts.ffcalculator.mobile.model.grid.Grid;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;
import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

import java.io.IOException;
import java.util.List;

public interface IGridService extends ICleanableService {
    List<IGrid> getGrids() throws IOException;
    String getIdClasseFromLibelle(String libelle) throws InputLibelleFormatException;
}
