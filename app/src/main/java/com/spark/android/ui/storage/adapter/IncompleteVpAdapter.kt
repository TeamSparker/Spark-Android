package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.databinding.ItemStorageIncompleteListBinding
import com.spark.android.ui.storage.photo.StoragePhotoCollectionActivity

class IncompleteVpAdapter : RecyclerView.Adapter<IncompleteVpAdapter.IncompleteVpViewHolder>() {

    var roomNameList = listOf<StorageRoom>()

    fun setList(list: List<StorageRoom>) {
        roomNameList = list
        notifyDataSetChanged()
    }

    class IncompleteVpViewHolder(private val binding: ItemStorageIncompleteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { incompleteCard ->
                val intent = Intent(incompleteCard.context, StoragePhotoCollectionActivity::class.java)
                intent.apply {
                    putExtra("roomId", requireNotNull(binding.storageRoom).roomId)
                    putExtra("thumbnail", requireNotNull(binding.storageRoom).thumbnail)
                    putExtra("cardType","incompleteCard")
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
        holder.onBind(roomNameList[position])
    }

    override fun getItemCount(): Int = roomNameList.size
}
