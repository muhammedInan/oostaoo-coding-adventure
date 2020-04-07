package com.oostaoo.org.oostaoocodingadventure.ui.testQuestions

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.oostaoo.org.oostaoocodingadventure.database.AppDatabase
import com.oostaoo.org.oostaoocodingadventure.database.DataRepository
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestQuestionsViewModel(idCampaign: Int, application: Application) : AndroidViewModel(application) {

    private var mIdCampaign = idCampaign
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!
    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val tokenJwt = sharedPreferences.getString("jwt", "")

    init {

        requestQuestions()
        requestCampaign(mIdCampaign)
    }

    fun getCampaign(): LiveData<Campaign> {

        return repository.getCampaign(mIdCampaign)
    }

    fun getQuestions(): LiveData<List<Question>> {

        return repository.getQuestions()
    }

    private fun requestCampaign(idCampaign: Int) {

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
        val myCall = apiServiceInterface.getCampaign(idCampaign)
        myCall.enqueue(object : Callback<Campaign> {
            override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                val campaign = response.body()
                if (campaign != null) insertCampaign(campaign)
            }
            override fun onFailure(call: Call<Campaign>, t: Throwable) {}
        })
    }

    private fun requestQuestions() {

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
        val myCall = apiServiceInterface.questions
        myCall.enqueue(object : Callback<List<Question>> {
            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                val questions = response.body()
                if (questions != null) {
                    for (question in questions) {
                        insertQuestions(question)
                    }
                }
            }
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {}
        })
    }

    fun insertCampaign(campaign: Campaign) = viewModelScope.launch {

        repository.insertCampaign(campaign)
    }

    fun insertQuestions(question: Question) = viewModelScope.launch {

        repository.insertQuestion(question)
    }
}