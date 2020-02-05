package com.oostaoo.org.oostaoocodingadventure.interfaces;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    String BASE_URL = "http://roodeo.com";

    @POST("/auth/local")
    Call<Object> logUser(@Body RequestBody body);

    @POST("/auth/local/register")
    Call<Object> createUser(@Body RequestBody body);
}
