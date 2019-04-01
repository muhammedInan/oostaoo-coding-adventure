package com.oostaoo.org.oostaoocodingadventure

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.oostaoo.org.oostaoocodingadventure.utils.isEmailValid
import kotlinx.android.synthetic.main.activity_connection.*

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

            //TODO: Test login and password in WS
            if(login == "test@gmail.com" && password == "test") {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            else {
                if(login.isEmpty()) {
                    edit_login.error = getString(R.string.error_empty_login)
                }
                else if (!isEmailValid(login)) {
                    edit_login.error = getString(R.string.error_invalid_email)
                }
                else if (password.isEmpty()) {
                    edit_password.error = getString(R.string.error_empty_password)
                }
                else {
                    Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}