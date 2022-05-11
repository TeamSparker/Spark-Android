package com.teamsparker.android.ui.storage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamsparker.android.R
import com.teamsparker.android.data.remote.entity.response.StorageCardPhoto
import com.teamsparker.android.databinding.ItemPhotoMainPickListBinding

class PhotoMainPickRvAdapter(private val setNewThumbnail: (Int, Int) -> Unit) :
    RecyclerView.Adapter<PhotoMainPickRvAdapter.PhotoMainPickRvViewHolder>() {

    var photoList = listOf<StorageCardPhoto>()

    fun setPhotoMainList(list: List<StorageCardPhoto>) {
        photoList = list
        notifyDataSetChanged()
    }

    class PhotoMainPickRvViewHolder(private val binding: ItemPhotoMainPickListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            storageCardPhoto: StorageCardPhoto,
            currentItemPos: Int,
            thumbnailUrl: String,
            setNewThumbnail: (Int, Int) -> Unit,
        ) {
            binding.storageCardPhoto = storageCardPhoto

            if (isInitialOpening) {
                if (storageCardPhoto.certifyingImg == thumbnailUrl) {
                    currentThumbnailItemPos = currentItemPos
                }
            }

            binding.ivMainPhotoPickItemRoundedCorner.setOnClickListener {
                isInitialOpening = false
                setNewThumbnail(currentItemPos, storageCardPhoto.recordId)
                thumbnail = storageCardPhoto.certifyingImg
            }

            if (currentThumbnailItemPos == currentItemPos) {
                binding.viewMainPhotoPickCardPickedBoundary.setBackgroundResource(R.drawable.shape_spark_pinkred_line_rect_2)
                binding.tvMainPhotoPickTagMain.visibility = View.VISIBLE
            } else {
                binding.viewMainPhotoPickCardPickedBoundary.setBackgroundResource(R.drawable.shape_spark_light_gray_line_rect_2)
                binding.tvMainPhotoPickTagMain.visibility = View.INVISIBLE
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
        holder.onBind(photoList[position], position, thumbnail, setNewThumbnail)
    }

    override fun getItemCount(): Int = photoList.size

    companion object {
        var thumbnail = ""
        var currentThumbnailItemPos = -1
        var isInitialOpening = true
    }
}
