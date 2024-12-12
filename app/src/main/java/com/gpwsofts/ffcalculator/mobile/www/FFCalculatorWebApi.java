package com.gpwsofts.ffcalculator.mobile.www;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @since 1.0.0
 * Couche Web : singleton permettant la récupération d'une instance de IApiInterface qui permet de faire des appels Http vers l'API Web
 */
public class FFCalculatorWebApi {
    private static final String TAG_NAME = "FFCalculatorWebApi";
    private static final String API_BASE_URL = "https://garnierpascalweb.fr/app/ffcalculator/api/v1/";
    private static FFCalculatorWebApi instance = null;
    private static Retrofit retrofit = null;
    private static IApiInterface apiService;

    public static FFCalculatorWebApi getInstance(){
        if (null == instance)
            instance = new FFCalculatorWebApi();
        return instance;
    }

    private FFCalculatorWebApi(){
        Log.i(TAG_NAME, "creation du singleton = FFCalculatorWebApi");
        if (null == retrofit){
            Log.i(TAG_NAME, "constuction de l'instance de retrofit - base url = <"+ API_BASE_URL + ">");
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.i(TAG_NAME, "creation de l'instance IApiInterface");
            apiService = retrofit.create(IApiInterface.class);
        } else {
            Log.d(TAG_NAME, "apiService deja initialisée");
        }
        Log.i(TAG_NAME, "fin creation du singleton = FFCalculatorWebApi");
    }

    public IApiInterface getApiService(){
        return apiService;
    }
}
