package com.example.miniguide.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun showKeyboardImplicit(targetView: View) {
        val inputMethodManager = targetView.context.getInputMethodManager()
        inputMethodManager.showSoftInput(targetView, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboardImplicit(targetView: View) {
        val inputMethodManager = targetView.context.getInputMethodManager()
        inputMethodManager.hideSoftInputFromWindow(targetView.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyboardForced(targetView: View) {
        val inputMethodManager = targetView.context.getInputMethodManager()
        inputMethodManager.hideSoftInputFromWindow(targetView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}