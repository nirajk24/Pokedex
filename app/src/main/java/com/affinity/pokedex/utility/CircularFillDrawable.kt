package com.affinity.pokedex.utility

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable


class CircularFillDrawable(private val startColor: Int, private val endColor: Int) : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var radius = 0f

    override fun draw(canvas: Canvas) {
        val centerX = bounds.exactCenterX()
        val centerY = bounds.exactCenterY()

        paint.shader = RadialGradient(centerX, centerY, radius, startColor, endColor, Shader.TileMode.CLAMP)

        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int = PixelFormat.TRANSPARENT

    fun setRadius(radius: Float) {
        this.radius = radius
        invalidateSelf()
    }
}

