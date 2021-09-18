package com.example.spaceshooter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.SystemClock.uptimeMillis
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlin.random.Random

const val STAGE_WIDTH=1080
const val STAGE_HEIGHT=720
const val STAR_COUNT=100
const val ENEMY_COUNT=6
val RNG= Random(uptimeMillis())
@Volatile var isBoosting = false
var playerSpeed = 0f

 const val PREFS= "com.example.spaceshooter"
const val LONGEST_DIST = "longest_distance"

class Game(context: Context) : SurfaceView(context), Runnable ,SurfaceHolder.Callback{
    private val prefs=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val tag="Game"
    private val gameThread= Thread(this)
    @Volatile
    private var isRunning=false
    private var isGameOver=false
    private val jukebox = Jukebox(context.assets)
    private val player= Player(resources)
    private val entities=ArrayList<Entity>()
    private val paint=Paint()
    private val paintText by lazy {
        val paint = Paint()
        paint.color = Color.WHITE
        paint
    }
    private var distanceTraveled=0
    private var maxDistanceTraveled=0

    init{
        holder.addCallback(this)
        holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT)
        for(i in 0 until STAR_COUNT){
            entities.add(Star())
        }
        for(i in 0 until ENEMY_COUNT){
            entities.add(Enemy(resources))
        }
    }

    private fun restart() {
        for(entity in entities){
            entity.respawn()
            player.respawn()
            distanceTraveled=0
            maxDistanceTraveled=prefs.getInt(LONGEST_DIST,0)
            isGameOver=false
            //play sound
            //update highscore
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        while (isRunning){
            update()
            render()
        }
    }
    private fun update() {
        player.update()
        for(entity in entities){
            entity.update()
        }
        distanceTraveled += playerSpeed.toInt()
        checkCollisions()
        if(!isGameOver)
        {checkGameOver()}
    }

    private fun checkCollisions() {
        for(i in STAR_COUNT until entities.size){
            val enemy =entities[i]
            if(isColliding(enemy,player)){
                enemy.onCollision(player)
                player.onCollision(enemy)
                jukebox.play(SFX.crash)
                //to do :sound effect
            }
        }

    }

    private fun checkGameOver() {
        if(player.health<0){
            jukebox.play(SFX.die)
            isGameOver=true
            if(distanceTraveled>maxDistanceTraveled){
                editor.putInt(LONGEST_DIST,distanceTraveled)
                editor.apply()
                //update highScore
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun render() {
        val canvas = acquireAndLOckCanvas()?:return
        canvas.drawColor(Color.rgb(25,25,112))

        for(entity in entities){
            entity.render(canvas, paint)
        }
        player.render(canvas,paint)
        renderHud(canvas,paintText)
        holder.unlockCanvasAndPost(canvas)
    }

    private fun renderHud(canvas: Canvas, paint: Paint) {
        val textSize=48f
        val margin =10f
        paint.textAlign=Paint.Align.LEFT
        paint.textSize=textSize
        if(!isGameOver){
            canvas.drawText("Health:${player.health}",margin,textSize,paint)
            canvas.drawText("Distance:${distanceTraveled}",margin,textSize*2,paint)
        }
        else{
            val centerX= STAGE_WIDTH*0.5f
            val centerY= STAGE_HEIGHT*0.5f
            paint.textAlign=Paint.Align.CENTER
            canvas.drawText("GAME OVER!",centerX,centerY,paint)
            canvas.drawText("(press to restart)",centerX,centerY+textSize,paint)
        }

    }

    private fun acquireAndLOckCanvas():Canvas?{
        if(holder?.surface?.isValid==false){
            return null
        }
        return  holder.lockCanvas()
    }



    fun pause() {
        Log.d(tag,"pause")
        isRunning=false
        gameThread.join()
    }

    fun resume() {
        Log.d(tag,"resume")
        isRunning=true

    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP ->
            {
                Log.d(tag,"slowing down")
                isBoosting = false
            }
            MotionEvent.ACTION_DOWN -> {
                Log.d(tag,"isBoosting")
                if(isGameOver){
                    restart()
                }
                else
                isBoosting = true
            }
        }
        return true
    }



    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d(tag,"surfaceCreated")
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(tag,"surfaceChanged,width:$width,height:$height")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(tag,"surfaceDestroyed")
    }
}