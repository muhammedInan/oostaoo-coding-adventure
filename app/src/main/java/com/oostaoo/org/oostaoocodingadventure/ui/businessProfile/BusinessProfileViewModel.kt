package com.oostaoo.org.oostaoocodingadventure.ui.businessProfile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.oostaoo.org.oostaoocodingadventure.database.AppDatabase
import com.oostaoo.org.oostaoocodingadventure.database.DataRepository
import com.oostaoo.org.oostaoocodingadventure.database.entreprise.Entreprise
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusinessProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =  application.getSharedPreferences("sharedpreferences", 0)
    private val entrepriseId = sharedPreferences.getInt("idEntreprise", 0)
    private val tokenJwt = sharedPreferences.getString("jwt", "")
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    init {
        requestEntreprise()
    }

    fun getEntreprise() : LiveData<Entreprise> {
        return repository.getEntreprise(entrepriseId)
    }

    private fun requestEntreprise() {

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
        val myCall = apiServiceInterface.getEntreprise(entrepriseId)
        myCall.enqueue(object : Callback<Entreprise> {
            override fun onResponse(call: Call<Entreprise>, response: Response<Entreprise>) {
                val entreprise = response.body()
                if(entreprise != null) {
                    insert(entreprise)
                }
            }

            override fun onFailure(call: Call<Entreprise>, t: Throwable) {
                Log.d("loadUser", "error")
            }
        })
    }

    fun insert(entreprise: Entreprise) = viewModelScope.launch {
        repository.insertEntreprise(entreprise)
    }
}