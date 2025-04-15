package com.gpwsofts.ffcalculator.mobile.services.town;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

import java.io.IOException;
import java.util.List;

/**
 * Service de chargement de la liste des villes
 * @since 1.0.0
 */
public interface ITownService extends ICleanableService {
    List<String> getTowns() throws IOException;
}
