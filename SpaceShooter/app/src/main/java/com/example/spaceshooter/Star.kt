package com.example.spaceshooter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Star: Entity() {
    private val TAG="Star"
    private val color=Color.YELLOW
    private val radius:Float
    init {
        radius= (RNG.nextInt(6)+2).toFloat()
        x= RNG.nextInt(STAGE_WIGTH).toFloat()
        y= RNG.nextInt(STAGE_HEIGHT).toFloat()
        width=radius*2f
        height=width
    }
    override fun update() {
        super.update()
        x-= playerSpeed
        if(right()<0){
            setLeft(STAGE_WIGTH.toFloat())
            setTop(RNG.nextInt(STAGE_HEIGHT-height.toInt()).toFloat())
        }
        if(top()> STAGE_HEIGHT)setBottom(0f)
    }

    override fun render(canvas: Canvas, paint: Paint) {
        super.render(canvas, paint)
        paint.color=color
        canvas.drawCircle(x,y,radius,paint)
    }
}