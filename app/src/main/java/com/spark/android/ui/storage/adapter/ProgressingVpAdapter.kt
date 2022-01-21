package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.databinding.ItemStorageProgressingListBinding
import com.spark.android.ui.storage.StoragePhotoCollectionActivity

class ProgressingVpAdapter : RecyclerView.Adapter<ProgressingVpAdapter.ProgressingVpViewHolder>() {

    var roomNameList = listOf<StorageRoom>()

    fun setList(list: List<StorageRoom>) {
        roomNameList = list
        notifyDataSetChanged()
    }


    class ProgressingVpViewHolder(private val binding: ItemStorageProgressingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(it.context, StoragePhotoCollectionActivity::class.java)
                intent.putExtra("roomId", requireNotNull(binding.storageRoom).roomId)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                it.context.startActivity(intent)
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
        holder.onBind(roomNameList[position])
    }

    override fun getItemCount(): Int = roomNameList.size
}
