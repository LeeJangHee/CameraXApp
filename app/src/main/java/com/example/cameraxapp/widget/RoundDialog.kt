package com.example.cameraxapp.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cameraxapp.R

class RoundDialog @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyle: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr, defStyle) {
    var cornerRadius: Int

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundDialog,
            defStyleAttr,
            defStyle
        ).apply {
            try {
                cornerRadius = getDimensionPixelSize(R.styleable.RoundDialog_cornerRadius, 0)
                if (cornerRadius > 0) setCornerRadius()
            } finally {
                recycle()
            }
        }
    }

    fun setCornerRadius() {
        val background = background
        val gd = GradientDrawable()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            gd.cornerRadius = cornerRadius.toFloat()
        }

        if (background is ColorDrawable) {
            gd.setColor(background.color)
        }
        setBackground(gd)
        clipToOutline = true
    }
}