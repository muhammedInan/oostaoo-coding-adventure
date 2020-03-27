package com.oostaoo.org.oostaoocodingadventure

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //TO DELETE APPLICATION DATA :
        //(applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()

        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, ConnectionActivity::class.java))
            finish()
        }, 2000)

    }
}
