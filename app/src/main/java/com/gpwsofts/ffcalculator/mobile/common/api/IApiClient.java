package com.gpwsofts.ffcalculator.mobile.common.api;

/**
 * @since 1.0.0
 */
public interface IApiClient {
    void sendErrorToBackEnd(String tagName, Exception e);
}
