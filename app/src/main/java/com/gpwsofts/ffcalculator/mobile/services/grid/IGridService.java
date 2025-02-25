package com.gpwsofts.ffcalculator.mobile.services.grid;

import com.gpwsofts.ffcalculator.mobile.exception.InputLibelleFormatException;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

import java.io.IOException;
import java.util.List;

public interface IGridService extends ICleanableService {
    List<Grid> getGrids() throws IOException;
    String getIdClasseFromLibelle(String libelle) throws InputLibelleFormatException;
}
