package com.example.pokedex.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class OutlineTextView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    private val text = "BULBASAUR"
    private val textSize = 78f
    private val outlineWidth = 2f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = this@OutlineTextView.textSize
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 5f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    init {
        createOutlineTextImage()
    }

    private fun createOutlineTextImage() {
        val textWidth = paint.measureText(text)
        val textHeight = paint.descent() - paint.ascent()

        val width = (textWidth + outlineWidth * 2).toInt()
        val height = (textHeight + outlineWidth * 2).toInt()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val xPosition = (width - textWidth) / 2
        val yPosition = (height + textHeight) / 2

        paint.style = Paint.Style.FILL // Fill the text
        paint.color = Color.WHITE // Set the fill color

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint) // Draw a white background

        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK

        canvas.drawText(text, xPosition, yPosition, paint)

        setImageDrawable(BitmapDrawable(resources, bitmap))
    }
}
