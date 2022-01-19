package com.spark.android.util

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spark.android.R
import org.xmlpull.v1.XmlPullParser
import java.lang.IllegalStateException

object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["profileImgUri", "profileImgBitmap"], requireAll = false)
    fun setProfileImg(imageview: ImageView, imgUri: Uri?, imgBitmap: Bitmap?) {
        if (imgUri == null && imgBitmap == null) {
            Glide.with(imageview.context)
                .load(R.drawable.ic_profile_photo)
                .circleCrop()
                .into(imageview)
        } else {
            imgUri?.let { uri ->
                Glide.with(imageview.context)
                    .load(uri)
                    .circleCrop()
                    .into(imageview)
            }
            imgBitmap?.let { bitmap ->
                Glide.with(imageview.context)
                    .load(bitmap)
                    .circleCrop()
                    .into(imageview)
            }
        }
    }

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
    fun setLeftBackground(imageview: ImageView, leftDay: Int?) {

        if (leftDay != null) {
            imageview.setImageResource(
                when {
                    leftDay == 0 -> R.drawable.img_home_left_ticket_6
                    leftDay <= 1 -> R.drawable.img_home_left_ticket_5
                    leftDay <= 7 -> R.drawable.img_home_left_ticket_4
                    leftDay <= 33 -> R.drawable.img_home_left_ticket_3
                    leftDay <= 59 -> R.drawable.img_home_left_ticket_2
                    leftDay <= 63 -> R.drawable.img_home_left_ticket_1
                    leftDay == 66 -> R.drawable.img_home_left_ticket_1
                    else -> throw IllegalStateException("바인딩 어댑터 setLeftBackground 오류")
                }
            )
        }
    }


    @JvmStatic
    @BindingAdapter("setLeftTicketColor")
    fun setLeftTicketColor(textView: TextView, leftDay: Int?) {
        val context = textView.context

        if (leftDay != null) {
            textView.setTextColor(
                when {
                    leftDay == 0 -> ContextCompat.getColor(context, R.color.spark_dark_pinkred)
                    leftDay <= 1 -> ContextCompat.getColor(context, R.color.spark_pinkred)
                    leftDay <= 7 -> ContextCompat.getColor(context, R.color.spark_bright_pinkred)
                    leftDay <= 33 -> ContextCompat.getColor(context, R.color.spark_light_pinkred)
                    leftDay <= 59 -> ContextCompat.getColor(context,
                        R.color.spark_more_light_pinkred)
                    leftDay <= 63 -> ContextCompat.getColor(context,
                        R.color.spark_most_light_pinkred)
                    leftDay == 66 -> ContextCompat.getColor(context,
                        R.color.spark_most_light_pinkred)
                    else -> throw IllegalStateException("바인딩 어댑터 setLeftTicketColor 오류")
                }
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
        when (life) {
            1 -> {
                imageview.setImageResource(
                    when (imageview.id) {
                        R.id.iv_home_right_ticket_life_one -> R.drawable.ic_home_ticket_life_full
                        R.id.iv_home_right_ticket_life_two -> R.drawable.ic_home_ticket_life_empty
                        R.id.iv_home_right_ticket_life_three -> R.drawable.ic_home_ticket_life_empty
                        else -> throw IllegalStateException("바인딩 어댑터 setRightTicketLife 오류")
                    }
                )
            }
            2 -> {
                imageview.setImageResource(
                    when (imageview.id) {
                        R.id.iv_home_right_ticket_life_one -> R.drawable.ic_home_ticket_life_full
                        R.id.iv_home_right_ticket_life_two -> R.drawable.ic_home_ticket_life_full
                        R.id.iv_home_right_ticket_life_three -> R.drawable.ic_home_ticket_life_empty
                        else -> throw IllegalStateException("바인딩 어댑터 setRightTicketLife 오류")
                    }
                )
            }
            3 -> {
                imageview.setImageResource(
                    when (imageview.id) {
                        R.id.iv_home_right_ticket_life_one -> R.drawable.ic_home_ticket_life_full
                        R.id.iv_home_right_ticket_life_two -> R.drawable.ic_home_ticket_life_full
                        R.id.iv_home_right_ticket_life_three -> R.drawable.ic_home_ticket_life_full
                        else -> throw IllegalStateException("바인딩 어댑터 setRightTicketLife 오류")
                    }
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setLeftTicketComment")
    fun setLeftTicketComment(textview: TextView, leftDay: Int?) {

        val context = textview.context
        if (leftDay != null) {
            textview.text = when {
                leftDay == 0 -> {
                    context.getString(R.string.home_ticket_left_comment_7)
                }
                leftDay <= 1 -> {
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

    @JvmStatic
    @BindingAdapter("setFloatingIconTint")
    fun setFloatingIconTint(floatingBtn: FloatingActionButton, color: Int) {
        floatingBtn.imageTintList = ColorStateList.valueOf(color)
    }

    @JvmStatic
    @BindingAdapter(value = ["certifyImgUri", "certifyImgBitmap"], requireAll = false)
    fun setCertifyImg(imageview: ImageView, imgUri: Uri?, imgBitmap: Bitmap?) {
        if (imgUri == null && imgBitmap == null) {
            imageview.setImageResource(R.drawable.shape_spark_light_gray_fill_rect)
        } else {
            imgUri?.let { uri ->
                imageview.setImageURI(uri)
            }
            imgBitmap?.let { bitmap ->
                imageview.setImageBitmap(bitmap)
            }
            imageview.clipToOutline = true
        }
    }

    @JvmStatic
    @BindingAdapter("setProgressBarBackground")
    fun setProgressBarBackground(progressBar: ProgressBar, leftDay: Int?) {
        val resources = progressBar.context.resources
        if (leftDay != null) {
            progressBar.progressDrawable =
                (
                when {
                    leftDay == 0 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_6,null)
                    leftDay <= 1 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_5,null)
                    leftDay <= 7 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_4,null)
                    leftDay <= 33 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_3,null)
                    leftDay <= 59 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_2,null)
                    leftDay <= 63 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_1,null)
                    leftDay == 66 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_1,null)
                    else -> throw IllegalStateException("bindingAdapter setProgressBarLeftBackground error")
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setHabitBackground")
    fun setHabitBackground(imageview: ImageView, leftDay: Int?) {
        if (leftDay != null) {
            imageview.setImageResource(
                when {
                    leftDay == 0 -> R.drawable.bg_habit_sparkflake6
                    leftDay <= 1 -> R.drawable.bg_habit_sparkflake5
                    leftDay <= 7 -> R.drawable.bg_habit_sparkflake4
                    leftDay <= 33 -> R.drawable.bg_habit_sparkflake3
                    leftDay <= 59 -> R.drawable.bg_habit_sparkflake2
                    leftDay <= 63 -> R.drawable.bg_habit_sparkflake1
                    leftDay == 66 -> R.drawable.bg_habit_sparkflake1
                    else -> throw IllegalStateException("bindingAdapter setHabitBackground error")
                }
            )
        }
    }

}

