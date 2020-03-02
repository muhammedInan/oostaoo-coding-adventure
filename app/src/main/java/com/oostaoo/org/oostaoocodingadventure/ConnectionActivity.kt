package com.oostaoo.org.oostaoocodingadventure

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.oostaoo.org.oostaoocodingadventure.interfaces.APIService
import com.oostaoo.org.oostaoocodingadventure.database.loginRequestResult.LoginRequestResult
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

    private var identifier: String = ""
    private var password: String = ""
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedpreferences = getSharedPreferences("sharedpreferences", 0)
        identifier = sharedpreferences.getString("identifier", "")!!
        password = sharedpreferences.getString("password", "")!!
        if(identifier != "" && password != "") {
            request()
        }

        setContentView(R.layout.activity_connection)

        button_registration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        button_connect.setOnClickListener {

            identifier = edit_login.text.toString()
            password = edit_password.text.toString()

            when {
                identifier.isEmpty() -> {
                    edit_login.error = getString(R.string.error_empty_username)
                }
                password.isEmpty() -> {
                    edit_password.error = getString(R.string.error_empty_password)
                }
                else -> {
                    request()
                }
            }
        }
    }

    private fun request() {
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
        myCall.enqueue(object : Callback<LoginRequestResult> {
            override fun onResponse(call: Call<LoginRequestResult>?, response: Response<LoginRequestResult>?) {
                if (response != null) {
                    val loginRequestResult: LoginRequestResult = response.body()!!
                    val editor: SharedPreferences.Editor = sharedpreferences.edit()
                    editor.putString("identifier", identifier)
                    editor.putString("password", password)
                    editor.putString("jwt", loginRequestResult.jwt)
                    editor.putString("email", loginRequestResult.user!!.email)
                    editor.putInt("id", loginRequestResult.user.id)
                    editor.apply()
                    goHome()
                }
                else {
                    Toast.makeText(applicationContext, getText(R.string.error_connection), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginRequestResult>?, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}