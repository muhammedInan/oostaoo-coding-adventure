package com.oostaoo.org.oostaoocodingadventure

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import com.oostaoo.org.oostaoocodingadventure.model.User
import com.oostaoo.org.oostaoocodingadventure.utils.isEmailValid
import kotlinx.android.synthetic.main.activity_connection.*
import okhttp3.OkHttpClient
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
            val login = edit_login.text.toString()
            val password = edit_password.text.toString()

            if(login.isEmpty()) {
                edit_login.error = getString(R.string.error_empty_username)
            }
            else if (!isEmailValid(login)) {
                edit_login.error = getString(R.string.error_invalid_email)
            }
            else if (password.isEmpty()) {
                edit_password.error = getString(R.string.error_empty_password)
            }
            else {
                val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()

                val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl(getString(R.string.WS_BASE_URL) + "/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build()
                val apiServiceInterface = retrofit.create(APIService::class.java)
                val myCall = apiServiceInterface.users
                myCall.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        var isFind = false
                        fun testUser(users: List<User>) {
                            var i = 0
                            while(i < users.size && !isFind) {
                                if (users[i].email == login && users[i].password == password) {
                                    isFind = true
                                }
                                i++
                            }
                        }
                        if (response.body() != null) {
                            testUser(response.body())
                        }
                        if (isFind) {
                            goHome()
                        }
                        else {
                            Toast.makeText(applicationContext, getText(R.string.error_connection), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    fun goHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}