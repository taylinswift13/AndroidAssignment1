package com.example.spaceshooter
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
    abstract class Entity() {
        private val TAG = "Entity"
        var x = 0f
        var y = 0f
        var width = 0f
        var height = 0f
        var velX = 0f
        var velY = 0f

        init{
            Log.d(TAG, "Entity created")
        }

        open fun update() {}
        open fun render(canvas: Canvas, paint: Paint) {}
        open fun onCollision(that: Entity) {} //notify the Entity about collisions
        open fun destroy() {}

        fun left() = x
        fun right() = x + width
        fun top() = y
        fun bottom() = y + height
        fun centerX() = x + (width * 0.5f)
        fun centerY() = y + (height * 0.5f)

        fun setLeft(leftEdgePosition: Float) {
            x = leftEdgePosition
        }
        fun setRight(rightEdgePosition: Float) {
            x = rightEdgePosition - width
        }
        fun setTop(topEdgePosition: Float) {
            y = topEdgePosition
        }
        fun setBottom(bottomEdgePosition: Float) {
            y = bottomEdgePosition - height
        }
        fun setCenter(x: Float, y: Float) {
            this.x = x - width * 0.5f
            this.y = y - height * 0.5f
        }
    }

    //a basic axis-aligned bounding box intersection test.
//https://gamedev.stackexchange.com/questions/586/what-is-the-fastest-way-to-work-out-2d-bounding-box-intersection
    fun isColliding(a: Entity, b: Entity): Boolean {
        return !(a.right() <= b.left() || b.right() <= a.left() || a.bottom() <= b.top() || b.bottom() <= a.top())
    }