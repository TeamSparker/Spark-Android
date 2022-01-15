package com.spark.android.ui.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.databinding.ItemCompleteListBinding
import com.spark.android.ui.storage.StoragePhotoCollectionActivity

class CompleteVpAdapter : RecyclerView.Adapter<CompleteVpAdapter.CompleteVpViewHolder>() {

    var roomList = listOf<String>()

    fun setList(list: List<String>) {
        roomList = list
        notifyDataSetChanged()
    }

    class CompleteVpViewHolder(private val binding: ItemCompleteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(it.context, StoragePhotoCollectionActivity::class.java)
                it.context.startActivity(intent)
            }
        }

        fun onBind(str: String) {
            binding.str = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteVpViewHolder {
        val binding = ItemCompleteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CompleteVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompleteVpViewHolder, position: Int) {
        holder.onBind(roomList[position])
        //아이템 포지션을 받아서 각각의 아이템에 데이터를 바인딩해줌 꼭 필요함
    }

    override fun getItemCount(): Int = roomList.size
}