package com.oostaoo.org.oostaoocodingadventure.ui.testCandidats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import com.oostaoo.org.oostaoocodingadventure.model.Campaign
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestCandidatsViewModel(idCampaign: Int) : ViewModel() {

    private var mutableLiveData = MutableLiveData<Campaign>()
    private var mIdCampaign = idCampaign

    init {
        requestCampaign(mIdCampaign)
    }

    fun getCampaign(): LiveData<Campaign> {
        return mutableLiveData
    }

    private fun requestCampaign(idCampaign: Int): LiveData<Campaign> {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APIService.BASE_URL + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val apiServiceInterface = retrofit.create(APIService::class.java)
        val myCall = apiServiceInterface.getCampaign(idCampaign)
        myCall.enqueue(object : Callback<Campaign> {
            override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                val campaign = response.body()
                mutableLiveData.value = campaign
            }

            override fun onFailure(call: Call<Campaign>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }
}