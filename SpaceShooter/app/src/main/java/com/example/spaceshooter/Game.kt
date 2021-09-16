package com.example.spaceshooter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock.uptimeMillis
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlin.random.Random

const val STAGE_WIGTH=1080
const val STAGE_HEIGHT=720
const val STAR_COUNT=40
const val ENEMY_COUNT=10
public val RNG= Random(uptimeMillis())
@Volatile var isBoosting = false
var playerSpeed = 0f

class Game(context: Context?) : SurfaceView(context), Runnable ,SurfaceHolder.Callback{
    private val TAG="Game"
    private val gameThread= Thread(this)
    @Volatile
    private var isRunning=false
    private var isGameOver=false
    private val player= Player(resources)
    private val stars=ArrayList<Entity>()
    private val enemies=ArrayList<Entity>()
    private val paint=Paint()

    init{
        resources
        getHolder().addCallback(this)
        holder.setFixedSize(STAGE_WIGTH, STAGE_HEIGHT)
        for(i in 0 until STAR_COUNT){
            stars.add(Star())
        }
        for(i in 0 until ENEMY_COUNT){
            enemies.add(Enemy(resources))
        }
    }


    override fun run() {
        while (isRunning){
            update()
            render()
        }
    }
    private fun update() {
        player.update()
        for(star in stars){
            star.update()
        }
        for(enemy in enemies){
            enemy.update()
        }
        checkCollisions()
        checkGameOver()
    }

    private fun checkCollisions() {
        if(player.health<0){
            isGameOver=true;
        }
    }

    private fun checkGameOver() {
        for(enemy in enemies){
            if(isColliding(enemy,player)){
            enemy.onCollision(player)
            player.onCollision(enemy)
                //to do :sound effect
            }
        }
    }


    private fun render() {
        val canvas = acquireAndLOckCanvas()?:return
        canvas.drawColor(Color.BLUE)

        for(star in stars){
            star.render(canvas, paint)
        }
        for(enemy in enemies){
            enemy.render(canvas, paint)
        }
        player.render(canvas,paint)
        holder.unlockCanvasAndPost(canvas)
    }
    private fun acquireAndLOckCanvas():Canvas?{
        if(holder?.surface?.isValid==false){
            return null
        }
        return  holder.lockCanvas()
    }



    fun pause() {
        Log.d(TAG,"pause")
        isRunning=false
        gameThread.join()
    }

    fun resume() {
        Log.d(TAG,"resume")
        isRunning=true

    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP ->
            {
                Log.d(TAG,"slowing down")
                isBoosting = false
            }
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG,"isBoosting")
                isBoosting = true
            }
        }
        return true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d(TAG,"surfaceCreated")
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(TAG,"surfaceChanged,width:$width,height:$height")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(TAG,"surfaceDestroyed")
    }
}