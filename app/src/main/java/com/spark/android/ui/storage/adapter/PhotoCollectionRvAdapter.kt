package com.spark.android.ui.storage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.databinding.ItemPhotoCollectionListBinding

class PhotoCollectionRvAdapter :
    RecyclerView.Adapter<PhotoCollectionRvAdapter.PhotoCollectionRvViewHolder>() {

    var leftDayList = listOf<String>()

    fun setList(list: List<String>) {
        leftDayList = list
        notifyDataSetChanged()
    }


    class PhotoCollectionRvViewHolder(private val binding: ItemPhotoCollectionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(str: String) {
            binding.str = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoCollectionRvViewHolder {
        val binding = ItemPhotoCollectionListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoCollectionRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoCollectionRvViewHolder, position: Int) {
        holder.onBind(leftDayList[position])
    }

    override fun getItemCount(): Int = leftDayList.size
}