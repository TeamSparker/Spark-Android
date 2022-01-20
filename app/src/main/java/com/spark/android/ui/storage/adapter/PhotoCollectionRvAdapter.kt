package com.spark.android.ui.storage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageCardPhoto
import com.spark.android.databinding.ItemPhotoCollectionListBinding

class PhotoCollectionRvAdapter :
    RecyclerView.Adapter<PhotoCollectionRvAdapter.PhotoCollectionRvViewHolder>() {

    var photolist = listOf<StorageCardPhoto>()
    fun setList(list: List<StorageCardPhoto>) {
        photolist = list
        notifyDataSetChanged()
    }


    class PhotoCollectionRvViewHolder(private val binding: ItemPhotoCollectionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(storageCardPhoto:StorageCardPhoto) {
            binding.storageCardPhoto = storageCardPhoto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoCollectionRvViewHolder {
        val binding = ItemPhotoCollectionListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoCollectionRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoCollectionRvViewHolder, position: Int) {
        holder.onBind(photolist[position])
    }

    override fun getItemCount(): Int = photolist.size
}