package com.oostaoo.org.oostaoocodingadventure.ui.myTests

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oostaoo.org.oostaoocodingadventure.database.AppDatabase
import com.oostaoo.org.oostaoocodingadventure.database.DataRepository
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyTestsViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val userId = sharedPreferences.getInt("id", 0)
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    init {
        requestCampaigns()
    }

    fun getCampaigns(): LiveData<List<Campaign>> {
        return repository.getCampaigns()
    }

    fun requestCampaigns() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APIService.BASE_URL + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val apiServiceInterface = retrofit.create(APIService::class.java)
        val myCall = apiServiceInterface.getCampaigns(userId)
        myCall.enqueue(object : Callback<List<Campaign>> {
            override fun onResponse(call: Call<List<Campaign>>, response: Response<List<Campaign>>) {
                val campaigns = response.body()
                if(campaigns != null) {
                    deleteAllCampaigns()
                    for (campaign in campaigns) {
                        insert(campaign)
                    }
                }
            }

            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {}
        })
    }

    fun insert(campaign: Campaign) = viewModelScope.launch {
        repository.insertCampaign(campaign)
    }

    fun deleteAllCampaigns() = viewModelScope.launch {
        repository.deleteAllCampaigns()
    }
}