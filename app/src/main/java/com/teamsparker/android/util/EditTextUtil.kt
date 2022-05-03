package com.teamsparker.android.util

import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.teamsparker.android.R

object EditTextUtil {
    fun focusedEditText(editText: EditText, view: View) {
        val context = view.context
        editText.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(noUse: View, hasFocus: Boolean) {
                if (hasFocus) {
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.spark_pinkred))
                } else {
                    if (editText.text.isEmpty()) {
                        view.setBackgroundColor(ContextCompat.getColor(context, R.color.spark_gray))
                    }
                }
            }

        })

    }
}
