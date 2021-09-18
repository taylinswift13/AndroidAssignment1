package com.example.spaceshooter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Star: Entity() {
    //private val TAG="Star"
    private val color=Color.rgb(RNG.nextInt(255),RNG.nextInt(255),RNG.nextInt(255))
    private val radius=(RNG.nextInt(8)+2).toFloat()
    init {
        respawn()
    }
    override fun respawn(){
        x= RNG.nextInt(STAGE_WIDTH).toFloat()
        y= RNG.nextInt(STAGE_HEIGHT).toFloat()
        width=radius*2f
        height=width
    }

    override fun update() {
        super.update()
        if(radius<3){
            x-= playerSpeed/1.5f
        }
        if (radius>=3) {
            if (radius < 6) {
                x-= playerSpeed/3
            }
        }
        if(radius>=6 && radius<=10)
        {
            x-= playerSpeed/5
        }


        if(right()<0){
            setLeft(STAGE_WIDTH.toFloat())
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