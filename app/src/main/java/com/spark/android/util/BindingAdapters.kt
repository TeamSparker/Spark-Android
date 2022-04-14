package com.spark.android.util

import android.content.res.ColorStateList
import android.net.Uri
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spark.android.R
import java.lang.IllegalArgumentException

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("profileImgUri")
    fun setProfileImg(imageview: ImageView, imgUri: Uri?) {
        if (imgUri == null) {
            Glide.with(imageview.context)
                .load(R.drawable.ic_profile_photo)
                .placeholder(R.drawable.shape_light_gray_line_circle)
                .error(R.drawable.shape_light_gray_line_circle)
                .circleCrop()
                .into(imageview)
        } else {
            Glide.with(imageview.context)
                .load(imgUri)
                .placeholder(R.drawable.shape_light_gray_line_circle)
                .error(R.drawable.shape_light_gray_line_circle)
                .circleCrop()
                .into(imageview)
        }
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageview: ImageView, url: String?) {
        url?.let {
            Glide.with(imageview.context)
                .load(url)
                .placeholder(R.color.spark_light_gray)
                .error(R.color.spark_light_gray)
                .into(imageview)
        }
    }

    @JvmStatic
    @BindingAdapter("setCircleImage")
    fun setCircleImage(imageview: ImageView, url: String?) {
        url?.let {
            Glide.with(imageview.context)
                .load(url)
                .placeholder(R.drawable.shape_light_gray_line_circle)
                .error(R.drawable.shape_light_gray_line_circle)
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
    @BindingAdapter(value = ["myStatus", "isUploaded"], requireAll = false)
    fun setRightBackground(imageview: ImageView, myStatus: String, isUploaded: Boolean) {
        if (myStatus == "DONE") {
            imageview.setImageResource(R.drawable.img_home_right_ticket_fold)
        } else if (myStatus == "NONE" || myStatus == "REST") {
            imageview.setImageResource(R.drawable.img_home_right_ticket)
        } else if (myStatus == "COMPLETE" && isUploaded) {
            imageview.setImageResource(R.drawable.img_home_right_ticket_finish_fold)
        } else if (myStatus == "COMPLETE" && !isUploaded) {
            imageview.setImageResource(R.drawable.img_home_right_ticket_finish)
        } else if (myStatus == "FAIL" && isUploaded) {
            imageview.setImageResource(R.drawable.img_home_right_ticket_finish_fold)
        } else if (myStatus == "FAIL" && !isUploaded) {
            imageview.setImageResource(R.drawable.img_home_right_ticket_finish)
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
    @BindingAdapter("certifyImgUri")
    fun setCertifyImg(imageview: ImageView, imgUri: Uri?) {
        if (imgUri == null) {
            imageview.setImageResource(R.drawable.shape_spark_light_gray_fill_rect)
        } else {
            imageview.setImageURI(imgUri)
        }
        imageview.clipToOutline = true
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
                            leftDay == 0 -> resources.getDrawable(
                                R.drawable.layer_list_habit_progressbar_6,
                                null
                            )
                            leftDay <= 6 -> resources.getDrawable(
                                R.drawable.layer_list_habit_progressbar_5,
                                null
                            )
                            leftDay <= 32 -> resources.getDrawable(
                                R.drawable.layer_list_habit_progressbar_4,
                                null
                            )
                            leftDay <= 58 -> resources.getDrawable(
                                R.drawable.layer_list_habit_progressbar_3,
                                null
                            )
                            leftDay <= 62 -> resources.getDrawable(
                                R.drawable.layer_list_habit_progressbar_2,
                                null
                            )
                            leftDay <= 66 -> resources.getDrawable(
                                R.drawable.layer_list_habit_progressbar_1,
                                null
                            )
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
                    }
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["habitUserStatus", "habitRestCount", "habitUserLeftDay"],
        requireAll = true
    )
    fun setSendSparkBtn(
        imageButton: ImageButton,
        status: String?,
        habitRestCount: Int?,
        habitUserLeftDay: Int?,
    ) {
        if (status != null) {
            if (habitRestCount != -1) {
                imageButton.setImageResource(
                    R.drawable.ic_habit_fire_darkgray
                )
                imageButton.isEnabled = false
            } else {
                if (habitUserLeftDay == 66) {
                    imageButton.setImageResource(
                        R.drawable.ic_habit_fire_inactive
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

    @JvmStatic
    @BindingAdapter("setCardOutLineColor")
    fun setCardOutLineColor(view: View, leftDay: Int?) {

        if (leftDay != null) {
            view.setBackgroundResource(
                when {
                    leftDay == 0 -> R.drawable.shape_spark_dark_pinkred_fill_line_2_rect_black
                    leftDay <= 6 -> R.drawable.shape_spark_pinkred_fill_line_2_rect_black
                    leftDay <= 32 -> R.drawable.shape_spark_bright_pinkred_fill_line_rect_2_black
                    leftDay <= 58 -> R.drawable.shape_spark_light_pinkred_fill_line_2_rect_black
                    leftDay <= 62 -> R.drawable.shape_spark_more_light_pinkred_fill_line_2_rect_black
                    leftDay <= 65 -> R.drawable.shape_spark_most_light_pinkred_fill_line_2_rect_black
                    leftDay == 66 -> R.drawable.shape_spark_most_light_pinkred_fill_line_2_rect_black
                    else -> throw IllegalStateException("바인딩 어댑터 setCardOutLineColor 오류")
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setCardInLineColor")
    fun setCardInLineColor(view: View, leftDay: Int?) {

        if (leftDay != null) {
            view.setBackgroundResource(
                when {
                    leftDay == 0 -> R.drawable.shape_spark_dark_pinkred_line_2_rect_bottomless
                    leftDay <= 6 -> R.drawable.shape_spark_pinkred_line_2_rect_bottomless
                    leftDay <= 32 -> R.drawable.shape_spark_bright_pinkred_line_2_rect_bottomless
                    leftDay <= 58 -> R.drawable.shape_spark_light_pinkred_line_2_rect_bottomless
                    leftDay <= 62 -> R.drawable.shape_spark_more_light_pinkred_line_2_rect_bottomless
                    leftDay <= 65 -> R.drawable.shape_spark_most_light_pinkred_line_2_rect_bottomless
                    leftDay == 66 -> R.drawable.shape_spark_most_light_pinkred_line_2_rect_bottomless
                    else -> throw IllegalStateException("바인딩 어댑터 setCardInLineColor 오류")
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setProgressingCardSparkFlake")
    fun setProgressingCardSparkFlake(imageview: ImageView, leftDay: Int?) {

        if (leftDay != null) {
            imageview.setBackgroundResource(
                when {
                    leftDay == 0 -> R.drawable.ic_storage_progressing_item_sparkflake6
                    leftDay <= 6 -> R.drawable.ic_storage_progressing_item_sparkflake5
                    leftDay <= 32 -> R.drawable.ic_storage_progressing_item_sparkflake4
                    leftDay <= 58 -> R.drawable.ic_storage_progressing_item_sparkflake3
                    leftDay <= 62 -> R.drawable.ic_storage_progressing_item_sparkflake2
                    leftDay <= 65 -> R.drawable.ic_storage_progressing_item_sparkflake1
                    leftDay == 66 -> R.drawable.ic_storage_progressing_item_sparkflake1
                    else -> throw IllegalStateException("바인딩 어댑터 setProgressingCardSparkFlake 오류")
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setIncompleteCardSparkFlake")
    fun setIncompleteCardSparkFlake(imageview: ImageView, failDay: Int?) {

        if (failDay != null) {
            imageview.setBackgroundResource(
                when {
                    failDay == 0 -> R.drawable.ic_storage_incomplete_item_sparkflake1
                    failDay <= 6 -> R.drawable.ic_storage_incomplete_item_sparkflake1
                    failDay <= 32 -> R.drawable.ic_storage_incomplete_item_sparkflake2
                    failDay <= 58 -> R.drawable.ic_storage_incomplete_item_sparkflake3
                    failDay <= 62 -> R.drawable.ic_storage_incomplete_item_sparkflake4
                    failDay <= 65 -> R.drawable.ic_storage_incomplete_item_sparkflake5
                    failDay == 66 -> R.drawable.ic_storage_incomplete_item_sparkflake6
                    else -> throw IllegalStateException("바인딩 어댑터 setIncompleteCardSparkFlake 오류")
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "status"], requireAll = false)
    fun ImageView.setPhotoImage(url: String?, status: String) {
        this.clipToOutline = true
        when (status) {
            "DONE" -> {
                Glide.with(this.context)
                    .load(url)
                    .placeholder(R.color.spark_dark_gray)
                    .error(R.color.spark_dark_gray)
                    .into(this)
            }
            "REST" -> {
                Glide.with(this.context)
                    .load(R.drawable.ic_photo_collection_sticker_rest)
                    .placeholder(R.color.spark_dark_gray)
                    .error(R.color.spark_dark_gray)
                    .into(this)
            }
            "NONE" -> {
                Glide.with(this.context)
                    .load(R.drawable.ic_photo_collection_sticker_none)
                    .placeholder(R.color.spark_dark_gray)
                    .error(R.color.spark_dark_gray)
                    .into(this)
            }
            "CONSIDER" -> {

            }
        }
    }

    @JvmStatic
    @BindingAdapter("setFinishRoomDialogTitle")
    fun setFinishRoomDialogTitle(textview: TextView, myStatus: String?) {
        if (myStatus == "COMPLETE") {
            textview.setText(R.string.finish_room_dialog_success_title)
        } else if (myStatus == "FAIL") {
            textview.setText(R.string.finish_room_dialog_fail_title)
        }
    }

    @JvmStatic
    @BindingAdapter("setFinishRoomDialogContent")
    fun setFinishRoomDialogContent(textview: TextView, myStatus: String?) {
        if (myStatus == "COMPLETE") {
            textview.setText(R.string.finish_room_dialog_success_content)
        } else if (myStatus == "FAIL") {
            textview.setText(R.string.finish_room_dialog_fail_content)
        }
    }

    @JvmStatic
    @BindingAdapter("setShareDialogContent")
    fun TextView.setShareDialogContent(day: Int) {
        this.setText(
            when (day) {
                in 1..2 -> R.string.insta_left_day_1_content
                in 3..6 -> R.string.insta_left_day_3_content
                in 7..32 -> R.string.insta_left_day_7_content
                in 33..58 -> R.string.insta_left_day_33_content
                in 59..65 -> R.string.insta_left_day_59_content
                66 -> R.string.insta_left_day_66_content
                else -> throw IllegalArgumentException("leftDay 범위 에러")
            }
        )
    }

    @JvmStatic
    @BindingAdapter("setFinishRoomDialogLottie")
    fun setFinishRoomDialogLottie(lottie: LottieAnimationView, myStatus: String?) {
        if (myStatus == "COMPLETE") {
            lottie.setAnimation("home_card_success.json")
        } else if (myStatus == "FAIL") {
            lottie.setAnimation("home_card_fail.json")
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["isThumbProfile", "alarmImgUrl"], requireAll = false)
    fun ImageView.setImgFromAlarmCenter(isThumbProfile: Boolean?, alarmImgUrl: String?) {
        if (alarmImgUrl != null) {
            when (requireNotNull(isThumbProfile)) {
                true -> Glide.with(this)
                    .load(alarmImgUrl)
                    .placeholder(R.drawable.shape_light_gray_line_circle)
                    .error(R.drawable.shape_light_gray_line_circle)
                    .circleCrop()
                    .into(this)
                false -> Glide.with(this)
                    .load(alarmImgUrl)
                    .placeholder(R.color.spark_light_gray)
                    .error(R.color.spark_light_gray)
                    .centerCrop()
                    .into(this)
            }
        } else {
            this.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("setSendSparkMessageItem")
    fun setSendSparkMessageItem(textview: TextView, position: Int?) {
        when(position){
            0 -> textview.setText(R.string.habit_send_spark_message_typing)
            1 -> textview.setText(R.string.habit_send_spark_message_first)
            2 -> textview.setText(R.string.habit_send_spark_message_second)
            3 -> textview.setText(R.string.habit_send_spark_message_third)
            4 -> textview.setText(R.string.habit_send_spark_message_fourth)
        }
    }

    @JvmStatic
    @BindingAdapter("setEditTextVisibility")
    fun setEditTextVisibility(editText: EditText, isTyping: LiveData<Boolean>?) {
        if (isTyping != null) {
            if (isTyping.value == true) {
                editText.visibility = View.VISIBLE
                editText.requestFocus()
            } else {
                editText.clearFocus()
                editText.visibility = View.GONE
            }
        }
    }
}

