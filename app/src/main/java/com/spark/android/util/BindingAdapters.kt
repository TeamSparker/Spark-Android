package com.spark.android.util

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spark.android.R
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
    fun setImage(imageview: ImageView, url: String?) {
        url?.let {
            Glide.with(imageview.context)
                .load(url)
                .into(imageview)
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
    @BindingAdapter("setLeftBackground")
    fun setLeftBackground(imageview: ImageView, leftDay: Int?) {

        if (leftDay != null) {
            imageview.setImageResource(
                when {
                    leftDay == 0 -> R.drawable.img_home_left_ticket_6
                    leftDay <= 6 -> R.drawable.img_home_left_ticket_5
                    leftDay <= 32 -> R.drawable.img_home_left_ticket_4
                    leftDay <= 58 -> R.drawable.img_home_left_ticket_3
                    leftDay <= 62 -> R.drawable.img_home_left_ticket_2
                    leftDay <= 65 -> R.drawable.img_home_left_ticket_1
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
                    leftDay <= 6 -> ContextCompat.getColor(context, R.color.spark_pinkred)
                    leftDay <= 32 -> ContextCompat.getColor(context, R.color.spark_bright_pinkred)
                    leftDay <= 58 -> ContextCompat.getColor(context, R.color.spark_light_pinkred)
                    leftDay <= 62 -> ContextCompat.getColor(
                        context,
                        R.color.spark_more_light_pinkred
                    )
                    leftDay <= 65 -> ContextCompat.getColor(
                        context,
                        R.color.spark_most_light_pinkred
                    )
                    leftDay == 66 -> ContextCompat.getColor(
                        context,
                        R.color.spark_most_light_pinkred
                    )
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
                leftDay <= 6 -> {
                    context.getString(R.string.home_ticket_left_comment_6)
                }
                leftDay <= 32 -> {
                    context.getString(R.string.home_ticket_left_comment_5)
                }
                leftDay <= 58 -> {
                    context.getString(R.string.home_ticket_left_comment_4)
                }
                leftDay <= 62 -> {
                    context.getString(R.string.home_ticket_left_comment_3)
                }
                leftDay <= 65 -> {
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
    @BindingAdapter("playLoadingLottie")
    fun playLoadingLottie(lottie: LottieAnimationView, play: Boolean) {
        if (play) {
            lottie.playAnimation()
        } else {
            lottie.cancelAnimation()
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
                            leftDay == 0 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_6,
                                null)
                            leftDay <= 6 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_5,
                                null)
                            leftDay <= 32 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_4,
                                null)
                            leftDay <= 58 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_3,
                                null)
                            leftDay <= 62 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_2,
                                null)
                            leftDay <= 66 -> resources.getDrawable(R.drawable.layer_list_habit_progressbar_1,
                                null)
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
                    leftDay <= 6 -> R.drawable.bg_habit_sparkflake5
                    leftDay <= 32 -> R.drawable.bg_habit_sparkflake4
                    leftDay <= 58 -> R.drawable.bg_habit_sparkflake3
                    leftDay <= 62 -> R.drawable.bg_habit_sparkflake2
                    leftDay <= 66 -> R.drawable.bg_habit_sparkflake1
                    else -> throw IllegalStateException("bindingAdapter setHabitBackground error")
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setStatusStickerImage")
    fun setStatusStickerImage(imageview: ImageView, status: String?) {
        if (status != null) {
            imageview.setImageResource(
                when (status) {
                    "NONE" -> 0
                    "CONSIDER" -> R.drawable.ic_habit_sticker_thinking
                    "REST" -> R.drawable.ic_habit_sticker_rest
                    "DONE" -> R.drawable.ic_habit_sticker_complete
                    else -> throw IllegalStateException("bindingAdapter setStatusStickerImage error")
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setUserStatus", "setUserLeftDay")
    fun setStatusText(textview: TextView, status: String?, leftDay: Int?) {
        if (status != null && leftDay != null) {
            if (leftDay == 66) {
                textview.setText(R.string.item_habit_team_status_ready)
            } else {
                textview.setText(
                    when (status) {
                        "NONE" -> R.string.item_habit_team_status_none
                        "CONSIDER" -> R.string.item_habit_team_status_consider
                        "REST" -> R.string.item_habit_team_status_rest
                        "DONE" -> R.string.item_habit_team_status_complete
                        else -> throw IllegalStateException("bindingAdapter setStatusText error")
                    })
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["habitUserStatus", "habitRestCount"], requireAll = true)
    fun setSendSparkImg(imageButton: ImageButton, status: String?, habitRestCount: Int?) {
        if (status != null) {
            if (habitRestCount != -1) {
                imageButton.setImageResource(
                    R.drawable.ic_habit_fire_darkgray
                )
                imageButton.isEnabled = false
            } else {
                imageButton.setImageResource(
                    when (status) {
                        "DONE", "REST" -> R.drawable.ic_habit_fire_inactive
                        "NONE", "CONSIDER" -> R.drawable.ic_habit_fire_default
                        else -> throw IllegalStateException("bindingAdapter setSendSparkImg error")
                    }
                )
                imageButton.isEnabled = (
                        when (status) {
                            "DONE", "REST" -> false
                            "NONE", "CONSIDER" -> true
                            else -> throw IllegalStateException("bindingAdapter setSendSparkImg error")
                        }
                        )
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["certificationLeftDay", "certificationStatus"], requireAll = true)
    fun setHabitCertificationButton(button: Button, leftDay: Int?, status: String?) {
        if (status != null && leftDay != null) {
            if (leftDay == 66) {
                button.setBackgroundResource(R.drawable.bg_habit_today_inactive)
                button.isEnabled = false
            } else {
                button.setBackgroundResource(
                    when (status) {
                        "DONE", "REST" -> R.drawable.bg_habit_today_inactive
                        "NONE", "CONSIDER" -> R.drawable.bg_habit_today_active
                        else -> throw IllegalStateException("bindingAdapter setHabitCertificationButton error")
                    }
                )
                button.isEnabled = (
                    when (status) {
                        "DONE", "REST" -> false
                        "NONE", "CONSIDER" -> true
                        else -> throw IllegalStateException("bindingAdapter setHabitCertificationButton error")
                    }
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["setVisibilityLeftDay", "setVisibilityStatus"], requireAll = true)
    fun setHabitCertificationVisibility(imageview: ImageView, leftDay: Int?, status: String?) {
        if (status != null && leftDay != null) {
            if (leftDay == 66) {
                imageview.visibility = View.GONE
            } else {
                imageview.visibility = (
                    when (status) {
                        "DONE", "REST" -> View.GONE
                        "NONE", "CONSIDER" -> View.VISIBLE
                        else -> throw IllegalStateException("bindingAdapter setHabitCertificationVisibility error")
                    }
                )
            }
        }
    }
}

