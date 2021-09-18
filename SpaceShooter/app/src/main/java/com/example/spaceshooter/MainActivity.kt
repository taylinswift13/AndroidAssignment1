package com.example.spaceshooter

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val TAG="MainActivity"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button=findViewById<Button>(R.id.StartGameButton)?.setOnClickListener {
           // Log.d(tag,"onClickListener called!")
            val intent= Intent(this,GameActivity::class.java)
            startActivity(intent)
        }
        val prefs = getSharedPreferences(PREFS, MODE_PRIVATE)
        val longestDistance = prefs.getInt(LONGEST_DIST,0)

        val highscore = findViewById<TextView>(R.id.highscore)
        highscore.text="Longest distance：$longestDistance km"
    }
}