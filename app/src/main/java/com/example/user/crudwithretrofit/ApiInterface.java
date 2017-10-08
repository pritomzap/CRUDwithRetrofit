package com.example.user.crudwithretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by user on 10/1/2017.
 */

public interface ApiInterface {

    @POST("all_train.php")
    Call<List<Trains>> getTrains();
}
