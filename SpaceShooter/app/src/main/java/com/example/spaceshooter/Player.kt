package com.example.spaceshooter

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.math.MathUtils.clamp
import android.util.Log

const val PLAYER_HEIGHT = 130
const val ACCELERATION=1.1f
const val MIN_VEL=0.1f
const val MAX_VEL=20f
const val GRAVITY=1.1f
const val LIFT=-(GRAVITY*2f)
const val DRAG=0.97f
const val PLAYER_STARTING_HEALTH = 3
class Player(res: Resources):BitmapEntity() {
    private val TAG="Player"
    var health= PLAYER_STARTING_HEALTH
    init {
        setSprite(loadBitmap(res, R.drawable.space_ship, PLAYER_HEIGHT))
    }

    override fun onCollision(that: Entity) {
        Log.d(TAG,"onCollision")
        health--
    }

    override fun update() {
        velX*= DRAG
        velY+= GRAVITY
        if(isBoosting){
            velX*= ACCELERATION
            velY+= LIFT
        }
        velX=clamp(velX, MIN_VEL, MAX_VEL)
        velY=clamp(velY, -MAX_VEL, MAX_VEL)

        y +=velY
        playerSpeed=velX

        if(bottom()> STAGE_HEIGHT){
            setBottom(STAGE_HEIGHT.toFloat())
        }
        else if(top()<0f){
            setTop(0f)
        }
    }
}