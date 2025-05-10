package com.gpwsofts.ffcalculator.mobile.common.www;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @since 1.0.0
 * Couche Web : singleton permettant la récupération d'une instance de IApiInterface qui permet de faire des appels Http vers l'API Web
 */
public class FFCalculatorWebApi {

    private static FFCalculatorWebApi instance = null;
    private static Retrofit retrofit = null;
    private static IApiInterface apiService;

    public static FFCalculatorWebApi getInstance(){
        if (null == instance)
            instance = new FFCalculatorWebApi();
        return instance;
    }

    private FFCalculatorWebApi(){
        if (null == retrofit){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(IApiInterface.class);
        }
    }

    public IApiInterface getApiService(){
        return apiService;
    }
}
