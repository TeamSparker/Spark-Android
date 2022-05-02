package com.spark.android.util

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spark.android.R

object AnimationUtil {

    const val ROTATE_TIME: Long = 750
    const val EDIT_TEXT_FOCUS_TRANSLATION: Long = 200

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
                start()
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


    fun grayBoxToastAnimation(textview: TextView): Animator? {
        return AnimatorInflater.loadAnimator(textview.context, R.animator.animator_toast_message)
            .apply {
                setTarget(textview)
            }
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

    fun lostFocusInSetPurpose(
        textviewOne: TextView,
        textviewTwo: TextView,
        editTextOne: EditText,
        editTextTwo: EditText,
        constraintLayout: ConstraintLayout
    ) {
        editTextOne.isClickable = false
        editTextTwo.isClickable = false
        ObjectAnimator.ofFloat(constraintLayout, "translationY", 0f).apply {
            start()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    ObjectAnimator.ofFloat(textviewTwo, View.ALPHA, 0f, 1f).apply {
                        duration = EDIT_TEXT_FOCUS_TRANSLATION
                        start()
                    }
                    ObjectAnimator.ofFloat(textviewOne, View.ALPHA, 0f, 1f).apply {
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator?) {
                                super.onAnimationStart(animation)
                                textviewOne.visibility = View.VISIBLE
                                textviewTwo.visibility = View.VISIBLE
                                editTextOne.isClickable = true
                                editTextTwo.isClickable = true
                            }
                        })
                        duration = EDIT_TEXT_FOCUS_TRANSLATION
                        start()
                    }
                }
            })
        }
    }

    fun getFocusInSetPurpose(
        textviewOne: TextView,
        textviewTwo: TextView,
        editText: EditText,
        constraintLayout: ConstraintLayout,
        activity: Activity,
        outerConstraintLayout: ConstraintLayout
    ) {
        outerConstraintLayout.isClickable = false
        ObjectAnimator.ofFloat(textviewTwo, View.ALPHA, 1f, 0f).apply {
            duration = EDIT_TEXT_FOCUS_TRANSLATION
            start()
        }
        ObjectAnimator.ofFloat(textviewOne, View.ALPHA, 1f, 0f).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    ObjectAnimator.ofFloat(constraintLayout, "translationY", -220f).apply {
                        start()
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                textviewOne.visibility = View.GONE
                                textviewTwo.visibility = View.GONE
                                editText.isFocusableInTouchMode = true
                                editText.requestFocus()
                                editText.isCursorVisible = true
                                KeyBoardUtil.show(activity)
                                outerConstraintLayout.isClickable = true
                            }
                        })
                    }
                }
            })
            duration = EDIT_TEXT_FOCUS_TRANSLATION
            start()
        }
    }

}
