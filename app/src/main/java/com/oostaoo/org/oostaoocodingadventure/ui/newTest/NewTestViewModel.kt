package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.oostaoo.org.oostaoocodingadventure.database.AppDatabase
import com.oostaoo.org.oostaoocodingadventure.database.DataRepository
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.profile.Profile
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewTestViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val tokenJwt = sharedPreferences.getString("jwt", "")
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    init {
        requestProfiles()
        requestTechnologies()
    }

    fun getProfiles(): LiveData<List<Profile>> {
        return repository.getProfiles()
    }

    fun getTechnologies(): LiveData<List<Technology>> {
        return repository.getTechnologies()
    }

    private fun requestProfiles() {

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
        val myCall = apiServiceInterface.profiles
        myCall.enqueue(object : Callback<List<Profile>> {
            override fun onResponse(call: Call<List<Profile>>, response: Response<List<Profile>>) {
                val profiles = response.body()
                if (profiles != null) {
                    for (profile in profiles) {
                        insertProfile(profile)
                    }
                }
            }

            override fun onFailure(call: Call<List<Profile>>, t: Throwable) {
            }
        })
    }

    private fun requestTechnologies() {

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
        val myCall = apiServiceInterface.technologies
        myCall.enqueue(object : Callback<List<Technology>> {
            override fun onResponse(call: Call<List<Technology>>, response: Response<List<Technology>>) {
                val technologies = response.body()
                if (technologies != null) {
                    for (technology in technologies) {
                        insertTechnology(technology)
                    }
                }
            }

            override fun onFailure(call: Call<List<Technology>>, t: Throwable) {}
        })
    }

    fun insertProfile(profile: Profile) = viewModelScope.launch {
        repository.insertProfile(profile)
    }

    fun deleteAllProfiles() = viewModelScope.launch {
        repository.deleteAllProfiles()
    }

    fun insertTechnology(technology: Technology) = viewModelScope.launch {
        repository.insertTechnology(technology)
    }

    fun deleteAllTechnologies() = viewModelScope.launch {
        repository.deleteAllTechnologies()
    }
}