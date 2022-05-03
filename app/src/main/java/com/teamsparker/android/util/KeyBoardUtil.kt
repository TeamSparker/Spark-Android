package com.teamsparker.android.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object KeyBoardUtil {
    fun hide(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus?.let { view ->
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }

    fun show(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus?.let { view ->
            inputMethodManager.showSoftInput(view, 0)
        }
    }
}
