package com.oostaoo.org.oostaoocodingadventure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.oostaoo.org.oostaoocodingadventure.utils.isEmailValid
import kotlinx.android.synthetic.main.activity_connection.*

class RegistrationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)

        val login = edit_login.text
        val password = edit_password.text

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
            //TODO: Send logins to API
            Toast.makeText(this, "Your login will be register via the API", Toast.LENGTH_SHORT).show()
        }
    }
}