package com.spark.android.ui.habit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.databinding.ItemHabitSendSparkMessageBinding

class HabitSendSparkRecyclerViewAdapter :
    RecyclerView.Adapter<HabitSendSparkRecyclerViewAdapter.HabitSendSparkViewHolder>() {
    class HabitSendSparkViewHolder(
        private val binding: ItemHabitSendSparkMessageBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            binding.position = position
            if (position == 0) {
                binding.message = "메시지 직접 입력하기"
                binding.layoutItemHabitSendSparkMessage.setPadding(0, 0, 0, 0)
            } else {
                binding.message = "test${position}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitSendSparkViewHolder {
        val binding =
            ItemHabitSendSparkMessageBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return HabitSendSparkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitSendSparkViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 5
    }
}