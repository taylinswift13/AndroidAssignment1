package com.example.spaceshooter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
const val PLAYER_HEIGHT = 100

class Player(game: Game):Entity() {
    var bitmap:Bitmap
    init {
        bitmap=BitmapFactory.decodeResource(game.resources,
        R.drawable.space_ship)
        val ratio= PLAYER_HEIGHT.toFloat()/bitmap.height
        val newH=(bitmap.height*ratio).toInt()
        val newW=(bitmap.width*ratio).toInt()
        bitmap=Bitmap.createScaledBitmap(bitmap,newW,newH,true)
        width=bitmap.width.toFloat()
        height=bitmap.height.toFloat()
    }
    override fun render(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmap,x,y,paint)
    }
}