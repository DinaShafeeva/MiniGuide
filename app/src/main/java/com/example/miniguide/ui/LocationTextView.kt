package com.example.miniguide.ui

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
        initHintText()
        initArrow()
    }

    private fun initArrow() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(context, R.drawable.ic_forward_arrow), null)
    }

    @SuppressLint("ResourceAsColor")
    private fun initHintText() {
        this.apply {
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.size_text)
            )

            val padding = resources.getDimensionPixelOffset(R.dimen.location_tv_padding)
            setPadding(padding, padding, padding, padding)

            setHintTextColor(R.color.colorHintLocation)
        }
    }

    private fun initBackground() {
        this.background = ContextCompat.getDrawable(context, R.drawable.background_shape)
    }
}