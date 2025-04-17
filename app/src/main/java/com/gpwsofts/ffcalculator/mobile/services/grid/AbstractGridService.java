package com.gpwsofts.ffcalculator.mobile.services.grid;

import com.gpwsofts.ffcalculator.mobile.common.reader.ReaderProvider;
import com.gpwsofts.ffcalculator.mobile.common.exception.InputLibelleFormatException;
import com.gpwsofts.ffcalculator.mobile.model.grid.Grid;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGridService implements IGridService {
    protected final ReaderProvider readerProvider;
    protected List<Grid> grids;
    public AbstractGridService(ReaderProvider readerProvider) {
        this.grids = new ArrayList<>();
        this.readerProvider = readerProvider;
    }
    @Override
    public String getIdClasseFromLibelle(String libelle) throws InputLibelleFormatException {
        String idClasse;
        if (null == libelle)
            throw new InputLibelleFormatException("libelle est null");
        try {
            final int borneInf = libelle.indexOf("(") + 1;
            final int borneSup = libelle.indexOf(")");
            idClasse = libelle.substring(borneInf, borneSup);
        } catch (IndexOutOfBoundsException ie) {
            throw new InputLibelleFormatException(" le libelle " + libelle + " nest pas au bon format", ie);
        }
        return idClasse;
    }
}
