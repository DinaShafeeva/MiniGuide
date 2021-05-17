package com.example.miniguide.ui.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.widget.doOnTextChanged
import com.example.miniguide.R
import com.mapbox.search.*

class PlacesSearchEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    private var searchEngine: SearchEngine? = null
    private var searchRequestTask: SearchRequestTask? = null
    private var resultLimit: Int = 0

    private var searchCallback: SearchSelectionCallback? = null

    init {
        applyAttributes(attrs)

        setTextIsSelectable(true)
        initSearchEngine()
        initPadding()
        initHintText()
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

    private fun applyAttributes(attributeSet: AttributeSet?) {
        attributeSet?.let {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.PlacesSearchAutoCTextView)

            resultLimit =
                typedArray.getInteger(R.styleable.PlacesSearchAutoCTextView_resultLimit, 5)

            typedArray.recycle()
        }
    }

    private fun initSearchEngine() {
        searchEngine = MapboxSearchSdk.createSearchEngine()
    }

    private fun initOnTextChangeListener() {
        searchCallback?.let {
            doOnTextChanged { text, _, _, _ ->
                searchRequestTask = searchEngine?.search(
                    text.toString(),
                    SearchOptions(limit = resultLimit),
                    it
                )
            }
        }
    }

    fun setOnSearchCallback(callBack: SearchSelectionCallback) {
        searchCallback = callBack
        initOnTextChangeListener()
    }
}