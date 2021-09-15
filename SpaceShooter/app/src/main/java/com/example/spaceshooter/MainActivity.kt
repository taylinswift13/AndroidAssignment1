package com.example.spaceshooter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val tag="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button=findViewById<Button>(R.id.StartGameButton)?.setOnClickListener {
           // Log.d(tag,"onClickListener called!")
            val intent= Intent(this,GameActivity::class.java)
            startActivity(intent)
        }

    }
}