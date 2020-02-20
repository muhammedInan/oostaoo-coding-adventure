package com.oostaoo.org.oostaoocodingadventure.interfaces;

import com.oostaoo.org.oostaoocodingadventure.model.Campaign;
import com.oostaoo.org.oostaoocodingadventure.model.LoginRequestResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    String BASE_URL = "http://roodeo.com";

    @POST("/auth/local")
    Call<LoginRequestResult> logUser(@Body RequestBody body);

    @POST("/auth/local/register")
    Call<Object> createUser(@Body RequestBody body);

    @GET("api/campaigns")
    Call<List<Campaign>> getCampaigns(@Query("user_in") Integer user_in);

    @GET("api/campaigns/{id}")
    Call<Campaign> getCampaign(@Path("id") int id);
}
