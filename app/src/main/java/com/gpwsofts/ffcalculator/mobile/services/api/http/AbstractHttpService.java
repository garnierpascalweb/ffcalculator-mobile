package com.gpwsofts.ffcalculator.mobile.services.api.http;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @since 1.0.0
 * Classe mere des services basés sur retrofit
 */
public abstract class AbstractHttpService {
    private static final String TAG_NAME = "AbstractHttpService";
    //TODO 1.0.0 url a foutre ailleurs qu'ici
    private static final String API_BASE_URL = "https://garnierpascalweb.fr/app/ffcalculator/api/v1/";
    private static Retrofit retrofit = null;
    protected static IApiInterface apiService;

    protected void initApiService() {
        // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        if (null == retrofit){
            Log.i(TAG_NAME, "constuction de l''instance de retrofit - base url = <"+ API_BASE_URL + ">");
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.i(TAG_NAME, "creation de l'instance IApiInterface");
            apiService = retrofit.create(IApiInterface.class);
        } else {
            Log.d(TAG_NAME, "apiService deja initialisée");
        }
    }
}
