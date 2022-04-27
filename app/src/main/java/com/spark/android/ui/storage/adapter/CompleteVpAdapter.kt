package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.databinding.ItemStorageCompleteListBinding
import com.spark.android.ui.storage.photo.StoragePhotoCollectionActivity

class CompleteVpAdapter : RecyclerView.Adapter<CompleteVpAdapter.CompleteVpViewHolder>() {

    var storageRoomList = listOf<StorageRoom>()

    fun setList(list: List<StorageRoom>) {
        storageRoomList = list
        notifyDataSetChanged()
    }

    class CompleteVpViewHolder(private val binding: ItemStorageCompleteListBinding, private val size: Int) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { completeCard ->
                val intent = Intent(completeCard.context, StoragePhotoCollectionActivity::class.java)
                intent.apply {
                    putExtra("roomId", requireNotNull(binding.storageRoom).roomId)
                    putExtra("thumbnail", requireNotNull(binding.storageRoom).thumbnail)
                    putExtra("cardType","completeCard")
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                completeCard.context.startActivity(intent)
            }
        }

        fun onBind(position: Int, storageRoom: StorageRoom) {
            binding.storageRoom = storageRoom
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteVpViewHolder {
        val binding =
            ItemStorageCompleteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompleteVpViewHolder(binding, storageRoomList.size)
    }

    override fun onBindViewHolder(holder: CompleteVpViewHolder, position: Int) {
        holder.onBind(position,storageRoomList[position])
        //아이템 포지션을 받아서 각각의 아이템에 데이터를 바인딩해줌 꼭 필요함
    }

    override fun getItemCount(): Int = storageRoomList.size
}
