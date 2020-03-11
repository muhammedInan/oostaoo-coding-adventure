package com.oostaoo.org.oostaoocodingadventure.ui.userProfile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.oostaoo.org.oostaoocodingadventure.database.AppDatabase
import com.oostaoo.org.oostaoocodingadventure.database.DataRepository
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.user.User
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val userId = sharedPreferences.getInt("id", 0)
    private val tokenJwt = sharedPreferences.getString("jwt", "")
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    init {
        requestUser()
    }

    fun getUser() : LiveData<User> {
        return repository.getUser(userId)
    }

    private fun requestUser() {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $tokenJwt")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APIService.BASE_URL + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val apiServiceInterface = retrofit.create(APIService::class.java)
        val myCall = apiServiceInterface.getUser(userId)
        myCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                if(user != null) {
                    insert(user)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("loadUser", "error")
            }
        })
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

}