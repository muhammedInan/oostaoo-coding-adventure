package com.oostaoo.org.oostaoocodingadventure.interfaces;

import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign;
import com.oostaoo.org.oostaoocodingadventure.database.entreprise.Entreprise;
import com.oostaoo.org.oostaoocodingadventure.database.loginRequestResult.LoginRequestResult;
import com.oostaoo.org.oostaoocodingadventure.database.profile.Profile;
import com.oostaoo.org.oostaoocodingadventure.database.question.Question;
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology;
import com.oostaoo.org.oostaoocodingadventure.database.user.User;

import java.util.List;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    String BASE_URL = "https://roodeo.com";

    @POST("/auth/local")
    Call<LoginRequestResult> logUser(@Body RequestBody body);

    @POST("/auth/local/register")
    Call<Object> createUser(@Body RequestBody body);

    @POST("api/campaigns")
    Call<Campaign> postCampaign(@Body RequestBody body);

    @GET("api/campaigns")
    Call<List<Campaign>> getCampaigns(@Query("user_in") Integer user_in);

    @GET("api/campaigns/{id}")
    Call<Campaign> getCampaign(@Path("id") int id);

    @GET("api/profiles")
    Call<List<Profile>> getProfiles();

    @GET("api/technologies")
    Call<List<Technology>> getTechnologies();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("users")
    Call<List<User>> getUsers();

    @GET("api/entreprises/{id}")
    Call<Entreprise> getEntreprise(@Path("id") int id);

    @GET("api/questions")
    Call<List<Question>> getQuestions();
}
