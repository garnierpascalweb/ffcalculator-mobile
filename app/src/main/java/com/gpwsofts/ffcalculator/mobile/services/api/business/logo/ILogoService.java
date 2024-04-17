package com.gpwsofts.ffcalculator.mobile.services.api.business.logo;

import com.gpwsofts.ffcalculator.mobile.model.Logo;

/**
 * Interface de service Logo
 *
 * @since 1.0.0
 */
public interface ILogoService {
    /**
     * Rend une instance de Logo par rapport a une idClasse
     *
     * @param idClasse
     * @return
     */
    Logo getLogo(String idClasse);
}
