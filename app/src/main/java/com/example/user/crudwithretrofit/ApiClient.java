package com.example.user.crudwithretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 10/1/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "http://10.0.2.2/reTROfit/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
