package com.gpwsofts.ffcalculator.mobile.services.client;

/**
 * @since 1.0.0
 */
public interface IApiClient {
    void sendErrorToBackEnd(String tagName, Exception e);
}
