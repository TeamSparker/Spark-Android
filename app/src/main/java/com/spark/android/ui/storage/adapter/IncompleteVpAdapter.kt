package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.databinding.ItemStorageIncompleteListBinding
import com.spark.android.ui.storage.StoragePhotoCollectionActivity

class IncompleteVpAdapter : RecyclerView.Adapter<IncompleteVpAdapter.IncompleteVpViewHolder>() {

    var roomNameList = listOf<StorageRoom>()

    fun setList(list: List<StorageRoom>) {
        roomNameList = list
        notifyDataSetChanged()
    }

    class IncompleteVpViewHolder(private val binding: ItemStorageIncompleteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(it.context, StoragePhotoCollectionActivity::class.java)
                it.context.startActivity(intent)
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