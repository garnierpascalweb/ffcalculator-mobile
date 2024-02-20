package com.gpwsofts.ffcalculator.mobile.services.logo;

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
    public Logo getLogo(String idClasse);
}
