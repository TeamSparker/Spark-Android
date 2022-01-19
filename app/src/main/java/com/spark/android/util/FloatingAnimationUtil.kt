package com.spark.android.util

import android.animation.ObjectAnimator
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spark.android.R
import java.util.logging.Handler

object FloatingAnimationUtil {

    const val ROTATE_TIME :Long = 1500

    fun toggleFab(
        buttonMain: FloatingActionButton,
        buttonMakeRoom: FloatingActionButton,
        buttonJoinCode: FloatingActionButton,
        backgroundLayout: ConstraintLayout,
        textViewMakeRoom: TextView,
        textViewJoinCode: TextView,
        fabState: Boolean
    ) {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션 세팅
        if (fabState) {
            ObjectAnimator.ofFloat(buttonMain, View.ROTATION, 45f, 0f).apply {
                duration = 300
                start()
            }
            ObjectAnimator.ofFloat(buttonJoinCode, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(buttonMakeRoom, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(textViewJoinCode, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(textViewMakeRoom, "translationY", 0f).apply { start() }
            textViewJoinCode.visibility = View.GONE
            textViewMakeRoom.visibility = View.GONE
            buttonMain.setImageResource(R.drawable.ic_fab_plus)
            backgroundLayout.visibility = View.GONE

            // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션 세팅
        } else {
            ObjectAnimator.ofFloat(buttonMain, View.ROTATION, -45f, 0f).apply {
                duration = 300
                start ()
            }
            textViewJoinCode.visibility = View.VISIBLE
            textViewMakeRoom.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(buttonJoinCode, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(buttonMakeRoom, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(textViewJoinCode, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(textViewMakeRoom, "translationY", -360f).apply { start() }
            buttonMain.setImageResource(R.drawable.ic_fab_plus_rotate_45)
            backgroundLayout.visibility = View.VISIBLE
        }
    }

    fun openToastAnimation(textview: TextView) {
        ObjectAnimator.ofFloat(textview, "translationY", -150f).apply { start() }
    }

    fun closeToastAnimation(textview: TextView) {
        ObjectAnimator.ofFloat(textview, "translationY", 0f).apply { start() }
    }

    fun closeFabAnimation(
        buttonMain: FloatingActionButton,
        buttonMakeRoom: FloatingActionButton,
        buttonJoinCode: FloatingActionButton,
        backgroundLayout: ConstraintLayout,
        textViewMakeRoom: TextView,
        textViewJoinCode: TextView,
    ) {
        ObjectAnimator.ofFloat(buttonMain, View.ROTATION, 45f, 0f).apply {
            duration = 300
            start()
        }
        ObjectAnimator.ofFloat(buttonJoinCode, "translationY", 0f).apply { start() }
        ObjectAnimator.ofFloat(buttonMakeRoom, "translationY", 0f).apply { start() }
        ObjectAnimator.ofFloat(textViewJoinCode, "translationY", 0f).apply { start() }
        ObjectAnimator.ofFloat(textViewMakeRoom, "translationY", 0f).apply { start() }
        textViewJoinCode.visibility = View.GONE
        textViewMakeRoom.visibility = View.GONE
        buttonMain.setImageResource(R.drawable.ic_fab_plus)
        backgroundLayout.visibility = View.GONE
    }

    fun rotateAnimation(
        imageButton: ImageButton
    ) {
        ObjectAnimator.ofFloat(imageButton, View.ROTATION, 360f, 0f).apply {
            duration = ROTATE_TIME
            start()
        }
    }
}