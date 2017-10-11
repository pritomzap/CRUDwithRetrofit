package com.example.user.crudwithretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by user on 10/1/2017.
 */

public interface ApiInterface {

    @POST("all_train.php")
    Call<List<Trains>> getTrains();

    @GET("insertTable.php")
    Call<Message> setTrains(@Query("name") String name,
                           @Query("type") String type);
}
