package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.databinding.ItemStorageProgressingListBinding
import com.spark.android.ui.storage.photo.StoragePhotoCollectionActivity
import com.spark.android.util.FirebaseLogUtil
import com.spark.android.util.FirebaseLogUtil.CLICK_CARD_MY_ROOM

class ProgressingVpAdapter :
    ListAdapter<StorageRoom, ProgressingVpAdapter.ProgressingVpViewHolder>(progressingDiffUtil) {


    class ProgressingVpViewHolder(private val binding: ItemStorageProgressingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { progressingCard ->
                //GA 트래킹
                FirebaseLogUtil.logClickEvent(CLICK_CARD_MY_ROOM)

                val intent =
                    Intent(progressingCard.context, StoragePhotoCollectionActivity::class.java)
                intent.apply {
                    putExtra("roomId", requireNotNull(binding.storageRoom).roomId)
                    putExtra("thumbnail", requireNotNull(binding.storageRoom).thumbnail)
                    putExtra("cardType", "progressingCard")
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                progressingCard.context.startActivity(intent)
            }
        }

        fun onBind(storageRoom: StorageRoom) {
            binding.storageRoom = storageRoom
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressingVpViewHolder {
        val binding = ItemStorageProgressingListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProgressingVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressingVpViewHolder, position: Int) {

        if (holder is ProgressingVpViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    fun updateProgressingRoomList(storageRooms: List<StorageRoom>) {
        submitList(storageRooms)
    }

    companion object {
        private val progressingDiffUtil = object : DiffUtil.ItemCallback<StorageRoom>() {
            override fun areItemsTheSame(oldItem: StorageRoom, newItem: StorageRoom): Boolean =
                oldItem.roomId == newItem.roomId

            override fun areContentsTheSame(oldItem: StorageRoom, newItem: StorageRoom): Boolean =
                oldItem == newItem
        }
    }
}
