package com.spark.android.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.spark.android.R
import com.spark.android.SparkApplication

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageview: ImageView, url: String) {
        Glide.with(imageview.context)
            .load(url)
            .into(imageview)
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

    @JvmStatic
    @BindingAdapter("setLeftTicketComment")
    fun setLeftTicketComment(textview: TextView, leftDay: String) {
        val number = if (leftDay == "D-day") {
            1
        } else {
            leftDay.replace("[^0-9]".toRegex(), "").toInt()
        }
        val context = textview.context
        if (number == 1) {
            textview.text = context.getString(R.string.home_ticket_left_comment_6)
        } else if (number <= 7) {
            textview.text = context.getString(R.string.home_ticket_left_comment_5)
        } else if (number <= 33) {
            textview.text = context.getString(R.string.home_ticket_left_comment_4)
        } else if (number <= 59) {
            textview.text = context.getString(R.string.home_ticket_left_comment_3)
        } else if (number <= 63) {
            textview.text = context.getString(R.string.home_ticket_left_comment_2)
        } else if (number == 66) {
            textview.text = context.getString(R.string.home_ticket_left_comment_1)
        }
    }
}

