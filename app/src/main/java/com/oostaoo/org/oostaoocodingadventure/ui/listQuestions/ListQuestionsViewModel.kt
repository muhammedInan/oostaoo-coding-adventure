package com.oostaoo.org.oostaoocodingadventure.ui.listQuestions

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.oostaoo.org.oostaoocodingadventure.database.AppDatabase
import com.oostaoo.org.oostaoocodingadventure.database.DataRepository
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.campaign.SendCampaign
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListQuestionsViewModel(Name: String, level: String, langs: String, copy_paste: Boolean, sent_report: Boolean,
                             profile: Int, user: Int, technologiesId: ArrayList<Int>,
                             technologiesName: ArrayList<String>, application: Application) : AndroidViewModel(application) {

    private var mName = Name
    private var mLevel = level
    private var mLangs = langs
    private var mCopyPaste = copy_paste
    private var mSentReport = sent_report
    private var mProfile = profile
    private var mUser = user
    private var mTechnologiesId = technologiesId
    private var mTechnologiesName = technologiesName
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!
    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val tokenJwt = sharedPreferences.getString("jwt", "")

    fun getName() : String {
        return mName
    }

    fun getLevel() : String {
        return mLevel
    }

    fun getLangs() : String {
        return mLangs
    }

    fun getCopyPaste() : Boolean {
        return mCopyPaste
    }

    fun getSentReport() : Boolean {
        return mSentReport
    }

    fun getProfile() : Int {
        return mProfile
    }

    fun getUser() : Int {
        return mUser
    }

    fun getTechnologiesId() : ArrayList<Int> {
        return mTechnologiesId
    }

    fun getSelectedTechnologies() : ArrayList<String> {
        return  mTechnologiesName
    }

    fun getTechnologies() : LiveData<List<Technology>> {

        return repository.getTechnologies()
    }

    fun postCampaign(campaign: SendCampaign) {

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
        val idBody = "{\"Name\":\"${campaign.Name}\"," +
                " \"level\":\"${campaign.level}\"," +
                " \"langs\":\"${campaign.langs}\"," +
                " \"copy_paste\":${campaign.copy_paste}," +
                " \"sent_report\":${campaign.sent_report}," +
                " \"profile\":${campaign.profile}," +
                " \"user\":${campaign.user}," +
                " \"technologies\":${campaign.technologies}," +
                " \"questions\":${campaign.questions}}"
        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            idBody
        )
        val myCall = apiServiceInterface.postCampaign(body)
        myCall.enqueue(object : Callback<Campaign> {
            override fun onResponse(call: Call<Campaign>?, response: Response<Campaign>?) {
                if (response != null) {
                    Log.d("postCampaign", "ok")
                }
                else {
                    Log.d("postCampaign", "response null")
                }

            }

            override fun onFailure(call: Call<Campaign>?, t: Throwable) {
                Log.d("postCampaign", "error")
            }
        })
    }

    fun insertQuestion(question: Question) = viewModelScope.launch {
        repository.insertQuestion(question)
    }
}