package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.databinding.ItemStorageCompleteListBinding
import com.spark.android.ui.storage.photo.StoragePhotoCollectionActivity
import com.spark.android.util.FirebaseLogUtil.logClickEvent

class CompleteVpAdapter :
    ListAdapter<StorageRoom, CompleteVpAdapter.CompleteVpViewHolder>(completeDiffUtil) {

    class CompleteVpViewHolder(
        private val binding: ItemStorageCompleteListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { completeCard ->
                //GA 트래킹
                logClickEvent("click_CARD_my_room")

                val intent =
                    Intent(completeCard.context, StoragePhotoCollectionActivity::class.java)
                intent.apply {
                    putExtra("roomId", requireNotNull(binding.storageRoom).roomId)
                    putExtra("thumbnail", requireNotNull(binding.storageRoom).thumbnail)
                    putExtra("cardType", "completeCard")
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                completeCard.context.startActivity(intent)
            }
        }

        fun onBind(storageRoom: StorageRoom) {
            binding.storageRoom = storageRoom
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteVpViewHolder {
        val binding = ItemStorageCompleteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CompleteVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompleteVpViewHolder, position: Int) {
        if (holder is CompleteVpViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    fun updateCompleteRoomList(storageRooms: List<StorageRoom>) {
        submitList(storageRooms)
    }

    companion object {
        private val completeDiffUtil = object : DiffUtil.ItemCallback<StorageRoom>() {
            override fun areItemsTheSame(oldItem: StorageRoom, newItem: StorageRoom): Boolean =
                oldItem.roomId == newItem.roomId

            override fun areContentsTheSame(oldItem: StorageRoom, newItem: StorageRoom): Boolean =
                oldItem == newItem
        }
    }
}
