package com.spark.android.util

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.spark.android.R
import com.spark.android.SparkApplication
import java.lang.IllegalStateException

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("setLeftBackground")
    fun setLeftBackground(imageview: ImageView, leftDay: Int) {

        imageview.setImageResource(
            when {
                leftDay == 0 -> R.drawable.img_home_left_ticket_6
                leftDay <= 7 -> R.drawable.img_home_left_ticket_5
                leftDay <= 33 -> R.drawable.img_home_left_ticket_4
                leftDay <= 59 -> R.drawable.img_home_left_ticket_3
                leftDay <= 63 -> R.drawable.img_home_left_ticket_2
                leftDay == 66 -> R.drawable.img_home_left_ticket_1
                else -> throw IllegalStateException("바인딩 어댑터 setLeftBackground 오류")
            }
        )
    }


    @JvmStatic
    @BindingAdapter("setLeftTicketColor")
    fun setLeftTicketColor(textView: TextView, leftDay: Int) {

        textView.setTextColor(when {
            leftDay == 0 -> ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_pinkred)
            leftDay <= 7 -> ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_pinkred)
            leftDay <= 33 -> ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_bright_pinkred)
            leftDay <= 59 -> ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_light_pinkred)
            leftDay <= 63 -> ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_more_light_pinkred)
            leftDay == 66 -> ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_most_light_pinkred)
            else -> throw IllegalStateException("바인딩 어댑터 setLeftTicketColor 오류")
        })
    }

    @JvmStatic
    @BindingAdapter("setCircleImage")
    fun setCircleImage(imageview: ImageView, url: String?) {
        url?.let {
            Glide.with(imageview.context)
                .load(url)
                .circleCrop()
                .into(imageview)
        }
    }

    @JvmStatic
    @BindingAdapter("setRightBackground")
    fun setRightBackground(imageview: ImageView, isDone: Boolean) {
        if (isDone) {
            imageview.setImageResource(R.drawable.img_home_right_ticket_fold)
        } else {
            imageview.setImageResource(R.drawable.img_home_right_ticket)
        }
    }

    @JvmStatic
    @BindingAdapter("setRightTicketLife")
    fun setRightTicketLife(imageview: ImageView, life: Int) {
        when (life) {
            1 -> {
                imageview.setImageResource(when (imageview.id) {
                    R.id.iv_home_right_ticket_life_one -> R.drawable.ic_home_ticket_life_full
                    R.id.iv_home_right_ticket_life_two -> R.drawable.ic_home_ticket_life_empty
                    R.id.iv_home_right_ticket_life_three -> R.drawable.ic_home_ticket_life_empty
                    else -> throw IllegalStateException("바인딩 어댑터 setRightTicketLife 오류")
                })
            }
            2 -> {
                imageview.setImageResource(when (imageview.id) {
                    R.id.iv_home_right_ticket_life_one -> R.drawable.ic_home_ticket_life_full
                    R.id.iv_home_right_ticket_life_two -> R.drawable.ic_home_ticket_life_full
                    R.id.iv_home_right_ticket_life_three -> R.drawable.ic_home_ticket_life_empty
                    else -> throw IllegalStateException("바인딩 어댑터 setRightTicketLife 오류")
                })
            }
            3 -> {
                imageview.setImageResource(when (imageview.id) {
                    R.id.iv_home_right_ticket_life_one -> R.drawable.ic_home_ticket_life_full
                    R.id.iv_home_right_ticket_life_two -> R.drawable.ic_home_ticket_life_full
                    R.id.iv_home_right_ticket_life_three -> R.drawable.ic_home_ticket_life_full
                    else -> throw IllegalStateException("바인딩 어댑터 setRightTicketLife 오류")
                })
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageview: ImageView, url: String) {
        Glide.with(imageview.context)
            .load(url)
            .circleCrop()
            .into(imageview)
    }

    @JvmStatic
    @BindingAdapter("setLeftTicketComment")
    fun setLeftTicketComment(textview: TextView, leftDay: Int) {

        val context = textview.context
        textview.text = when {
            leftDay == 0 -> {
                context.getString(R.string.home_ticket_left_comment_6)
            }
            leftDay <= 7 -> {
                context.getString(R.string.home_ticket_left_comment_5)
            }
            leftDay <= 33 -> {
                context.getString(R.string.home_ticket_left_comment_4)
            }
            leftDay <= 59 -> {
                context.getString(R.string.home_ticket_left_comment_3)
            }
            leftDay <= 63 -> {
                context.getString(R.string.home_ticket_left_comment_2)
            }
            leftDay == 66 -> {
                context.getString(R.string.home_ticket_left_comment_1)
            }
            else -> throw IllegalStateException("바인딩 어댑터 setLeftTicketComment 오류")
        }
    }
}

