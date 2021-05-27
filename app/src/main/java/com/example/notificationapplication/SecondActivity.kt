package com.example.notificationapplication

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import com.tapadoo.alerter.Alerter

class SecondActivity : AppCompatActivity() {


    lateinit var layoutDisconnected : LinearLayout
    lateinit var layoutConnected :LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        layoutConnected = findViewById(R.id.layoutconnected)
        layoutDisconnected = findViewById(R.id.layoutDisconnected)

        val  networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this , Observer { isConnected ->

            if(isConnected){
                layoutDisconnected.visibility = View.GONE
                layoutConnected.visibility = View.VISIBLE
            }else{
                layoutConnected.visibility = View.GONE
                layoutDisconnected.visibility = View.VISIBLE
            }
        })
    }
}