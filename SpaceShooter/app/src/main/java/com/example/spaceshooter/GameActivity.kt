package com.example.spaceshooter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class GameActivity : AppCompatActivity() {
    private val tag="GameActivity"
    private lateinit var game:Game
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        game= Game(this)
        setContentView(game)
        Log.d(tag,"onCreate called")
    }

    override fun onPause() {
        game.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        game.resume()
    }
}