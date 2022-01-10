package com.spark.android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    // 기초세팅(폴더링)을 위해서 그냥 넣어놓은거니까 나중에 바인딩 어댑터 만들때 삭제해주세요
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageview: ImageView, url: String) {
        Glide.with(imageview.context)
            .load(url)
            .circleCrop()
            .into(imageview)
    }
}
