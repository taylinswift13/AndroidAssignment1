package com.example.spaceshooter

import android.content.res.Resources
import android.support.v4.math.MathUtils.clamp
import android.util.Log

const val PLAYER_HEIGHT = 100
const val ACCELERATION=1.1f
const val MIN_VEL=2f
const val MAX_VEL=10f
const val GRAVITY=1.5f
const val LIFT=-(GRAVITY*2.5f)
const val DRAG=0.97f
const val PLAYER_STARTING_HEALTH = 3
const val PLAYER_STARTING_POSITION = 10F
class Player(res: Resources):BitmapEntity() {
    private val tag="Player"
    var health= PLAYER_STARTING_HEALTH
    init {
        setSprite(loadBitmap(res, R.drawable.space_ship, PLAYER_HEIGHT))
        respawn()
    }

    override fun respawn() {
        health= PLAYER_STARTING_HEALTH
        x= PLAYER_STARTING_POSITION
    }

    override fun onCollision(that: Entity) {
        Log.d(tag,"onCollision")
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