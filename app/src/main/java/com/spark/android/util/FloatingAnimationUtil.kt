package com.spark.android.util

import android.animation.ObjectAnimator
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spark.android.R

object FloatingAnimationUtil {

    fun toggleFab(
        buttonMain: FloatingActionButton,
        buttonMakeRoom: FloatingActionButton,
        buttonJoinCode: FloatingActionButton,
        backgroundLayout : ConstraintLayout,
        fabState: Boolean
    ) {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션 세팅
        if (fabState) {
            ObjectAnimator.ofFloat(buttonJoinCode, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(buttonMakeRoom, "translationY", 0f).apply { start() }
            buttonMain.setImageResource(R.drawable.ic_fab_plus)
            backgroundLayout.visibility = View.GONE

        // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션 세팅
        } else {
            ObjectAnimator.ofFloat(buttonJoinCode, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(buttonMakeRoom, "translationY", -400f).apply { start() }
            buttonMain.setImageResource(R.drawable.ic_fab_plus_rotate_45)
            backgroundLayout.visibility = View.VISIBLE
        }
    }
}