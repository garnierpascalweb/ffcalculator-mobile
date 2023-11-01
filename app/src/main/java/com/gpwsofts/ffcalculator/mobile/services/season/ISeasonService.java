package com.gpwsofts.ffcalculator.mobile.services.season;

import com.gpwsofts.ffcalculator.mobile.model.IResult;

import java.util.List;

/**
 * Interface pour le service des saisons
 * @since 1.0.0
 */
public interface ISeasonService {
    /**
     * @since 1.0.0
     * @return la liste des resultats de la saison
     */
    public List<IResult> getResults();
}
