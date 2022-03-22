package com.spark.android.ui.storage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.R
import com.spark.android.data.remote.entity.response.StorageCardPhoto
import com.spark.android.databinding.ItemPhotoMainPickListBinding

class PhotoMainPickRvAdapter(private val onPhotoCLick: (Int,Int) -> Unit) :
    RecyclerView.Adapter<PhotoMainPickRvAdapter.PhotoMainPickRvViewHolder>() {

    var photolist = listOf<StorageCardPhoto>()


    fun setList(list: List<StorageCardPhoto>) {
        photolist = list
        notifyDataSetChanged()
    }

    class PhotoMainPickRvViewHolder(private val binding: ItemPhotoMainPickListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(storageCardPhoto: StorageCardPhoto, position: Int, onPhotoCLick: (Int,Int) -> Unit) {
            binding.storageCardPhoto = storageCardPhoto
            if (selectedItemPos == position) {
                binding.viewMainPhotoPickCardPickedBoundary.setBackgroundResource(R.drawable.shape_spark_pinkred_line_rect_2)
                binding.tvMainPhotoPickTagMain.visibility = View.VISIBLE
            } else {
                binding.viewMainPhotoPickCardPickedBoundary.setBackgroundResource(R.drawable.shape_spark_light_gray_line_rect_2)
                binding.tvMainPhotoPickTagMain.visibility = View.INVISIBLE
            }
            binding.ivMainPhotoPickItemRoundedCorner.setOnClickListener {
                onPhotoCLick(position,storageCardPhoto.recordId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoMainPickRvViewHolder {
        val binding = ItemPhotoMainPickListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoMainPickRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoMainPickRvViewHolder, position: Int) {
        holder.onBind(photolist[position], position, onPhotoCLick)
    }

    override fun getItemCount(): Int = photolist.size

    companion object {
        var selectedItemPos = -1
    }
}


