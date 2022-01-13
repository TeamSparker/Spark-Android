package com.spark.android.ui.storage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.databinding.ItemIncompleteListBinding

class IncompleteVpAdapter : RecyclerView.Adapter<IncompleteVpAdapter.IncompleteVpViewHolder>() {

    var roomNameList = listOf<String>()

    fun setList(list: List<String>) {
        roomNameList = list
        notifyDataSetChanged()
    }

    class IncompleteVpViewHolder(private val binding: ItemIncompleteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(str: String) {
            binding.str = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncompleteVpViewHolder {
        val binding = ItemIncompleteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return IncompleteVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IncompleteVpViewHolder, position: Int) {
        holder.onBind(roomNameList[position])
    }

    override fun getItemCount(): Int = roomNameList.size
}