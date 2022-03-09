package com.spark.android.ui.storage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageCardPhoto
import com.spark.android.databinding.ItemPhotoMainPickListBinding

class PhotoMainPickRvAdapter :
    RecyclerView.Adapter<PhotoMainPickRvAdapter.PhotoMainPickRvViewHolder>() {

    var photolist = listOf<StorageCardPhoto>()
    fun setList(list: List<StorageCardPhoto>) {
        photolist = list
        notifyDataSetChanged()
    }

    class PhotoMainPickRvViewHolder(private val binding : ItemPhotoMainPickListBinding ) :
    RecyclerView.ViewHolder(binding.root) {
        fun onBind(storageCardPhoto: StorageCardPhoto){
            binding.storageCardPhoto = storageCardPhoto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoMainPickRvViewHolder {
        val binding = ItemPhotoMainPickListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoMainPickRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoMainPickRvViewHolder, position: Int) {
        holder.onBind(photolist[position])
    }

    override fun getItemCount(): Int = photolist.size
}


