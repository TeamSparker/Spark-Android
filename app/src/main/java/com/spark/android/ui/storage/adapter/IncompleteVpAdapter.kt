package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.databinding.ItemStorageIncompleteListBinding
import com.spark.android.ui.storage.photo.StoragePhotoCollectionActivity
import com.spark.android.util.FirebaseLogUtil

class IncompleteVpAdapter :
    ListAdapter<StorageRoom, IncompleteVpAdapter.IncompleteVpViewHolder>(incompleteDiffUtil) {

    class IncompleteVpViewHolder(private val binding: ItemStorageIncompleteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { incompleteCard ->
                //GA 트래킹
                FirebaseLogUtil.logClickEvent("click_CARD_my_room")

                val intent =
                    Intent(incompleteCard.context, StoragePhotoCollectionActivity::class.java)
                intent.apply {
                    putExtra("roomId", requireNotNull(binding.storageRoom).roomId)
                    putExtra("thumbnail", requireNotNull(binding.storageRoom).thumbnail)
                    putExtra("cardType", "incompleteCard")
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                incompleteCard.context.startActivity(intent)
            }
        }

        fun onBind(storageRoom: StorageRoom) {
            binding.storageRoom = storageRoom
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncompleteVpViewHolder {
        val binding = ItemStorageIncompleteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return IncompleteVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IncompleteVpViewHolder, position: Int) {
        if (holder is IncompleteVpViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    fun updateIncompleteRoomList(storageRooms: List<StorageRoom>) {
        submitList(storageRooms)
    }

    companion object {
        private val incompleteDiffUtil = object : DiffUtil.ItemCallback<StorageRoom>() {
            override fun areItemsTheSame(oldItem: StorageRoom, newItem: StorageRoom): Boolean =
                oldItem.roomId == newItem.roomId

            override fun areContentsTheSame(oldItem: StorageRoom, newItem: StorageRoom): Boolean =
                oldItem == newItem
        }
    }
}
