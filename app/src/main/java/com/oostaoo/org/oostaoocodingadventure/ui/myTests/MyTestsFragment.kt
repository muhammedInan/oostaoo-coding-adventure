package com.oostaoo.org.oostaoocodingadventure.ui.myTests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.widget.Toast
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import com.oostaoo.org.oostaoocodingadventure.model.LoginRequestResult
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyTestsFragment : Fragment() {

    private lateinit var myTestsViewModel: MyTestsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        myTestsViewModel =
                ViewModelProviders.of(this).get(MyTestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_tests, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        myTestsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedpreferences: SharedPreferences = activity!!.getSharedPreferences("sharedpreferences", 0)
        val userId = sharedpreferences.getInt("id", 0)

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
        myCall.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>?, response: Response<Any>?) {
                if (response != null) {
                    Toast.makeText(context,"good", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, getText(R.string.error_connection), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Any>?, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}