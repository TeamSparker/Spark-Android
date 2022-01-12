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

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("setLeftBackground")
    fun setLeftBackground(imageview: ImageView, leftDay: String) {

        val number = if (leftDay == "D-day") {
            1
        } else {
            leftDay.replace("[^0-9]".toRegex(), "").toInt()
        }

        if (number == 1) {
            imageview.setImageResource(R.drawable.img_home_left_ticket_6)
        } else if (number <= 7) {
            imageview.setImageResource(R.drawable.img_home_left_ticket_5)
        } else if (number <= 33) {
            imageview.setImageResource(R.drawable.img_home_left_ticket_4)
        } else if (number <= 59) {
            imageview.setImageResource(R.drawable.img_home_left_ticket_3)
        } else if (number <= 63) {
            imageview.setImageResource(R.drawable.img_home_left_ticket_2)
        } else if (number == 66) {
            imageview.setImageResource(R.drawable.img_home_left_ticket_1)
        }
    }

    @JvmStatic
    @BindingAdapter("setLeftTicketColor")
    fun setLeftTicketColor(textView: TextView, leftDay: String) {

        val number = if (leftDay == "D-day") {
            1
        } else {
            leftDay.replace("[^0-9]".toRegex(), "").toInt()
        }

        if (number == 1) {
            textView.setTextColor(
                ContextCompat.getColor(
                    SparkApplication.ApplicationContext(),
                    R.color.spark_dark_pinkred
                )
            )
        } else if (number <= 7) {
            textView.setTextColor(
                ContextCompat.getColor(
                    SparkApplication.ApplicationContext(),
                    R.color.spark_pinkred
                )
            )
        } else if (number <= 33) {
            textView.setTextColor(
                ContextCompat.getColor(
                    SparkApplication.ApplicationContext(),
                    R.color.spark_bright_pinkred
                )
            )
        } else if (number <= 59) {
            textView.setTextColor(
                ContextCompat.getColor(
                    SparkApplication.ApplicationContext(),
                    R.color.spark_light_pinkred
                )
            )
        } else if (number <= 63) {
            textView.setTextColor(
                ContextCompat.getColor(
                    SparkApplication.ApplicationContext(),
                    R.color.spark_more_light_pinkred
                )
            )
        } else if (number == 66) {
            textView.setTextColor(
                ContextCompat.getColor(
                    SparkApplication.ApplicationContext(),
                    R.color.spark_most_light_pinkred
                )
            )
        }
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
        if (life == 1) {
            if (imageview.id == R.id.iv_home_right_ticket_life_one) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_full)
            } else if (imageview.id == R.id.iv_home_right_ticket_life_two) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_empty)
            } else if (imageview.id == R.id.iv_home_right_ticket_life_three) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_empty)
            }
        } else if (life == 2) {
            if (imageview.id == R.id.iv_home_right_ticket_life_one) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_full)
            } else if (imageview.id == R.id.iv_home_right_ticket_life_two) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_full)
            } else if (imageview.id == R.id.iv_home_right_ticket_life_three) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_empty)
            }
        } else if (life == 3) {
            if (imageview.id == R.id.iv_home_right_ticket_life_one) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_full)
            } else if (imageview.id == R.id.iv_home_right_ticket_life_two) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_full)
            } else if (imageview.id == R.id.iv_home_right_ticket_life_three) {
                imageview.setImageResource(R.drawable.ic_home_ticket_life_full)
            }
        }
    }

}