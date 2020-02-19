package com.oostaoo.org.oostaoocodingadventure.ui.myTests

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import com.oostaoo.org.oostaoocodingadventure.model.Campaign
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyTestsViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my tests Fragment"
    }
    val text: LiveData<String> = _text

    private var mutableLiveData = MutableLiveData<List<Campaign>>()
    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val userId = sharedPreferences.getInt("id", 0)

    init {
        requestCampaigns()
    }

    fun getCampaigns(): LiveData<List<Campaign>> {
        return mutableLiveData
    }

    fun requestCampaigns(): LiveData<List<Campaign>> {
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
                mutableLiveData.value = campaigns
            }

            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }
}