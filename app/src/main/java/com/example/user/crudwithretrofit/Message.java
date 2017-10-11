package com.example.user.crudwithretrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 10/12/2017.
 */

public class Message {

    @SerializedName("message")
    public String message;
    public String getMessage() {
        return message;
    }
}
