package com.oostaoo.org.oostaoocodingadventure

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, ConnectionActivity::class.java))
            finish()
        }, 2000)

    }
}
