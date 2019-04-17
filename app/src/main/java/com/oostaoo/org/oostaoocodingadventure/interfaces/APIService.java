package com.oostaoo.org.oostaoocodingadventure.interfaces;

import com.oostaoo.org.oostaoocodingadventure.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    //public static final String ENDPOINT = "http://ec2-35-180-87-221.eu-west-3.compute.amazonaws.com:1337";

    @GET("/usercodingames")
    Call<List<User>> getUsers();

    @POST("/usercodingames")
    Call<User> createUser(@Body User user);
}
