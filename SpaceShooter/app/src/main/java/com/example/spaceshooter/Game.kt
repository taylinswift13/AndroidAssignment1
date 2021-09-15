package com.example.spaceshooter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock.uptimeMillis
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlin.concurrent.thread

const val STAGE_WIGTH=1080
const val STAGE_HEIGHT=720
const val STAR_COUNT=40
public val RNG=Random(uptimeMillis())


class Game(context: Context?) : SurfaceView(context), Runnable ,SurfaceHolder.Callback{
    private val TAG="Game"
    private val gameThread= Thread(this)
    @Volatile
    private var isRunning=false
    private val player:Player= Player(this)
    private val stars=ArrayList<Entity>()
    private val paint=Paint()

    init{
        resources
        getHolder().addCallback(this)
        holder.setFixedSize(STAGE_WIGTH, STAGE_HEIGHT)
        for(i in 0 until STAR_COUNT){
            stars.add(Star())
        }
    }


    override fun run() {
        while (isRunning){
            update()
            render()
        }
    }
    private fun update() {
        for(star in stars){
            star.update()
        }
    }

    private fun render() {
        val canvas = acquireAndLOckCanvas()?:return
        canvas.drawColor(Color.BLUE)
        player.render(canvas,paint)
        for(star in stars){
            star.render(canvas, paint)
        }
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