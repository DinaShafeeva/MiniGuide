package com.example.miniguide.ui.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.miniguide.R

class LocationTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        initBackground()
        initPadding()
        initHintText()
        initArrow()
    }

    private fun initArrow() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(context, R.drawable.ic_forward_arrow),
            null
        )
    }

    private fun initPadding() {
        val padding = resources.getDimensionPixelOffset(R.dimen.location_tv_padding)
        setPadding(padding, padding, padding, padding)
    }

    @SuppressLint("ResourceAsColor")
    private fun initHintText() {
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.default_text_size)
        )

        setHintTextColor(R.color.colorHintLocation)
    }

    private fun initBackground() {
        background = ContextCompat.getDrawable(context, R.drawable.background_shape)
    }
}