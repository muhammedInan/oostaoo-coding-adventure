package com.oostaoo.org.oostaoocodingadventure

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import com.oostaoo.org.oostaoocodingadventure.model.User
import com.oostaoo.org.oostaoocodingadventure.utils.isEmailValid
import kotlinx.android.synthetic.main.activity_connection.*
import kotlinx.android.synthetic.main.activity_connection.edit_password
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.R.string
import org.json.JSONObject



class RegistrationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)

        button_registrate.setOnClickListener {

            val username = edit_username.text
            val email = edit_email.text
            val password = edit_password.text

            if(username.isEmpty()) {
                edit_username.error = getString(R.string.error_empty_username)
            }
            if(email.isEmpty()) {
                edit_email.error = getString(R.string.error_empty_email)
            }
            else if (!isEmailValid(email)) {
                edit_email.error = getString(R.string.error_invalid_email)
            }
            else if (password.isEmpty()) {
                edit_password.error = getString(R.string.error_empty_password)
            }
            else {
                val user = User(username.toString(), email.toString(), password.toString())
                val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()

                val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl(getString(R.string.WS_BASE_URL) + "/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build()
                val apiServiceInterface = retrofit.create(APIService::class.java)
                val myCall = apiServiceInterface.createUser(user)
                myCall.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
                        when (response?.code()) {
                            200 -> goConnection()
                            else -> {
                                val jObjError = JSONObject(response?.errorBody()?.string())
                                Toast.makeText(applicationContext, jObjError.getString("message"), Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        Toast.makeText(applicationContext, t?.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    fun goConnection() {
        startActivity(Intent(this, ConnectionActivity::class.java))
        finish()
    }
}