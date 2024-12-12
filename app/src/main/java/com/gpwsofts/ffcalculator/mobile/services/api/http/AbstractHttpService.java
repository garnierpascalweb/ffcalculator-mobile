package com.gpwsofts.ffcalculator.mobile.services.api.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @since 1.0.0
 * Classe mere des services basés sur retrofit
 */
public abstract class AbstractHttpService {
    //TODO 1.0.0 url a foutre ailleurs qu'ici
    private static final String API_BASE_URL = "https://garnierpascalweb.fr/app/ffcalculator/api/v1/";
    protected static Retrofit retrofit = null;

    protected static Retrofit getClient() {
        // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
