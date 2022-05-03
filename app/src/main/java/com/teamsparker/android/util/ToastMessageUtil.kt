package com.teamsparker.android.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.getToast(msg: String): Toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
