package com.oostaoo.org.oostaoocodingadventure.ui.configEmail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

class ConfigEmailViewModel(idCampaign: Int, names: ArrayList<String>, emails: ArrayList<String>, application: Application) : AndroidViewModel(application) {

    private var mIdCampaign = idCampaign
    private var mNames = names
    private var mEmails = emails
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    init {
        requestCampaign(mIdCampaign)
    }

    fun getCampaign(): LiveData<Campaign> {
        return repository.getCampaign(mIdCampaign)
    }

    fun getNames() : ArrayList<String> {
        return mNames
    }

    fun getEmails() : ArrayList<String> {
        return mEmails
    }

    private fun requestCampaign(idCampaign: Int) {

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
                if (campaign != null) {
                    insertCampaign(campaign)
                }
            }
            override fun onFailure(call: Call<Campaign>, t: Throwable) {}
        })
    }
    fun insertCampaign(campaign: Campaign) = viewModelScope.launch {
        repository.insertCampaign(campaign)
    }
}
