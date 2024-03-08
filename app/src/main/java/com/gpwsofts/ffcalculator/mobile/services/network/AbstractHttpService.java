package com.gpwsofts.ffcalculator.mobile.services.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @since 1.0.0
 * Un chien de retrotefi hérité des chiens fils
 */
public class AbstractHttpService {
    protected static Retrofit retrofit = null;
    static Retrofit getClient() {
        // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://garnierpascalweb.fr/app/ffcalculator/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
