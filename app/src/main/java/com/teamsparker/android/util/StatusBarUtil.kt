package com.teamsparker.android.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController

fun Activity.initStatusBarColor(color: Int) {
    this.window.statusBarColor = this.getColor(color)
}

fun Activity.initStatusBarTextColorToWhite() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        this.window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        this.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
    }
}
