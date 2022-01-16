package com.spark.android.ui.habit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spark.android.R
import com.spark.android.databinding.ItemHabitTeamBinding
import com.spark.android.ui.habit.HabitSendSparkBottomSheet

class HabitRecyclerViewAdapter(var list: MutableList<String>) :
    RecyclerView.Adapter<HabitRecyclerViewAdapter.HabitViewHolder>() {
    class HabitViewHolder(private val binding: ItemHabitTeamBinding, private val size: Int) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int, data: String) {
            if (position == 0) {
                binding.ivItemHabitTeamMe.visibility = View.VISIBLE
                binding.tvItemHabitTeamSendCount.visibility = View.VISIBLE
                binding.btnItemHabitTeamSend.setImageResource(R.drawable.ic_habit_fire_darkgray)
            } else {
                binding.btnItemHabitTeamSend.setOnClickListener {
                    HabitSendSparkBottomSheet().show((it.context as AppCompatActivity).supportFragmentManager,
                        this.javaClass.name)
                }
            }

            if (position == size - 1) {
                binding.viewItemHabitTeamDivider.visibility = View.GONE
            }

            // test
            if (position == 1) {
                binding.ivItemHabitTeamStickerComplete.visibility = View.VISIBLE
            }

            // test
            if (position == 2) {
                binding.ivItemHabitTeamStickerRest.visibility = View.VISIBLE
            }

            // test
            Glide.with(this.itemView.context)
                .load("data:image/jpeg;base64,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")
                .circleCrop()
                .into(binding.ivItemHabitTeamProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding =
            ItemHabitTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding, list.size)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.onBind(position, list[position])
    }

    override fun getItemCount() = list.size
}