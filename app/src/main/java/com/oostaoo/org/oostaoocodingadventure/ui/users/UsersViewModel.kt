package com.oostaoo.org.oostaoocodingadventure.ui.users

import android.app.Application
import android.content.SharedPreferences
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

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val adminId = sharedPreferences.getInt("id", 0)
    private val tokenJwt = sharedPreferences.getString("jwt", "")
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    init {
        requestUsers()
    }

    fun getUsers(): LiveData<List<User>> {
        return repository.getUsers(adminId)
    }

    private fun requestUsers() {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APIService.BASE_URL + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val apiServiceInterface = retrofit.create(APIService::class.java)
        val myCall = apiServiceInterface.users
        myCall.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response.body()
                if (users != null) {
                    for (user in users) {
                        insertUser(user)
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {}
        })
    }
    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }
}