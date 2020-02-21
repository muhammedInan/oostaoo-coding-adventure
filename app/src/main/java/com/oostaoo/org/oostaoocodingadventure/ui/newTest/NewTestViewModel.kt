package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign

class NewTestViewModel : ViewModel() {

    private var mutableLiveData = MutableLiveData<Campaign>()

    /*init {
        requestCampaign()
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
    }*/
}