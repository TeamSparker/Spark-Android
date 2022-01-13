package com.spark.android.ui.storage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.databinding.ItemProgressingListBinding
import okhttp3.internal.notify

class ProgressingVpAdapter : RecyclerView.Adapter<ProgressingVpAdapter.ProgressingVpViewHolder>() {

    var roomNameList = listOf<String>()

    fun setList(list: List<String>) {
        roomNameList = list
        notifyDataSetChanged()
    }

    class ProgressingVpViewHolder(private val binding: ItemProgressingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(str: String) {
            binding.str = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressingVpViewHolder {
        val binding = ItemProgressingListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProgressingVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressingVpViewHolder, position: Int) {
        holder.onBind(roomNameList[position])
    }

    override fun getItemCount(): Int = roomNameList.size
}