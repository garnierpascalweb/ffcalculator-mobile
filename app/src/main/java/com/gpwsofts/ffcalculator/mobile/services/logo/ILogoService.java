package com.gpwsofts.ffcalculator.mobile.services.logo;

import com.gpwsofts.ffcalculator.mobile.model.logo.ILogo;
import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

/**
 * Interface de service Logo
 *
 * @since 1.0.0
 */
public interface ILogoService extends ICleanableService {
    /**
     * Rend une instance de Logo par rapport a une idClasse
     *
     * @param idClasse id de la classe
     * @return
     */
    ILogo getLogo(String idClasse);

    /**
     * Rend une instance de Logo inconnu
     * @return une instance de logo
     */
    ILogo getUnknownLogo();
}
