package com.example.spaceshooter

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint

const val ENEMY_HEIGHT=80
const val ENEMY_SPAWN_OFFSET= STAGE_WIGTH*2
class Enemy(res:Resources):BitmapEntity() {
    init {
        val id = when(RNG.nextInt(1,6)){
            1->R.drawable.enemy_1
            2->R.drawable.enemy_2
            3->R.drawable.enemy_3
            4->R.drawable.enemy_4
            5->R.drawable.enemy_5
            else -> R.drawable.enemy_1
        }
        val bmp= loadBitmap(res,id, ENEMY_HEIGHT)
        setSprite(flipVertically(bmp))
    }
    fun respawn(){
        x=(STAGE_WIGTH+ RNG.nextInt(ENEMY_SPAWN_OFFSET)).toFloat()
        y= RNG.nextInt(STAGE_HEIGHT- ENEMY_HEIGHT).toFloat()
    }
    override fun update() {
        velX=-playerSpeed
        x+=velX
        if(right()<0){
            respawn()
        }
    }

    override fun onCollision(that: Entity) {
        
        respawn()
    }
}


