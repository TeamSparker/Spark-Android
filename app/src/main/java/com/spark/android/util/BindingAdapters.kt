package com.spark.android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.spark.android.R

object BindingAdapters {

    // 기초세팅(폴더링)을 위해서 그냥 넣어놓은거니까 나중에 바인딩 어댑터 만들때 삭제해주세요
    @JvmStatic
    @BindingAdapter("setLeftBackground")
    fun setLeftBackground(imageview: ImageView, leftDay: String ) {
        val number = leftDay.replace("[^0-9]".toRegex(), "").toInt()

        if(number == 1){
            imageview.setImageResource(R.drawable.img_home_left_ticket_6)
        } else if(number <= 7){
            imageview.setImageResource(R.drawable.img_home_left_ticket_5)
        } else if(number <= 33){
            imageview.setImageResource(R.drawable.img_home_left_ticket_4)
        } else if(number <= 59){
            imageview.setImageResource(R.drawable.img_home_left_ticket_3)
        } else if(number <= 63){
            imageview.setImageResource(R.drawable.img_home_left_ticket_3)
        } else if(number == 66){
            imageview.setImageResource(R.drawable.img_home_left_ticket_3)
        }
    }
}