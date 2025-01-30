package com.gpwsofts.ffcalculator.mobile.services.grid;

import com.gpwsofts.ffcalculator.mobile.InputLibelleFormatException;
import com.gpwsofts.ffcalculator.mobile.model.Grid;

import java.io.IOException;
import java.util.List;

public interface IGridService {
    List<Grid> getGrids() throws IOException;
    String getIdClasseFromLibelle(String libelle) throws InputLibelleFormatException;
}
