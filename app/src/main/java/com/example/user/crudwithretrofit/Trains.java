package com.example.user.crudwithretrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 9/30/2017.
 */

public class Trains {

    @SerializedName("name")
    public String name;
    @SerializedName("type")
    public String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
