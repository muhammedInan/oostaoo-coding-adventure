package com.oostaoo.org.oostaoocodingadventure

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import kotlinx.android.synthetic.main.activity_connection.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_connection)

        button_registration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        button_connect.setOnClickListener {
            val identifier = edit_login.text.toString()
            val password = edit_password.text.toString()

            when {
                identifier.isEmpty() -> {
                    edit_login.error = getString(R.string.error_empty_username)
                }
                password.isEmpty() -> {
                    edit_password.error = getString(R.string.error_empty_password)
                }
                else -> {
                    val okHttpClient = OkHttpClient.Builder()
                            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build()

                    val retrofit: Retrofit = Retrofit.Builder()
                            .baseUrl(APIService.BASE_URL + "/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build()
                    val apiServiceInterface = retrofit.create(APIService::class.java)
                    val idBody = "{\"identifier\":\"$identifier\", \"password\":\"$password\"}"
                    val body = RequestBody.create(
                            okhttp3.MediaType.parse("application/json; charset=utf-8"),
                            idBody
                    )
                    val myCall = apiServiceInterface.logUser(body)
                    myCall.enqueue(object : Callback<Any> {
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            if (response.body() != null) {
                                goHome()
                            }
                            else {
                                Toast.makeText(applicationContext, getText(R.string.error_connection), Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    fun goHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}