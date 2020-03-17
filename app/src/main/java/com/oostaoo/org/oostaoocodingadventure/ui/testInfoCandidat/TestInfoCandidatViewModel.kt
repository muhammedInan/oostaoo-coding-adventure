package com.oostaoo.org.oostaoocodingadventure.ui.testInfoCandidat

import android.app.Application
import androidx.lifecycle.*
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

class TestInfoCandidatViewModel(idCampaign: Int, application: Application) : AndroidViewModel(application) {
}