package com.spark.android.ui.habit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.HabitRecord
import com.spark.android.data.remote.entity.response.HabitResponse
import com.spark.android.databinding.ItemHabitTeamBinding
import com.spark.android.ui.habit.HabitSendSparkBottomSheet

class HabitRecyclerViewAdapter : RecyclerView.Adapter<HabitRecyclerViewAdapter.HabitViewHolder>() {
    val list = mutableListOf<HabitRecord>()
    lateinit var response : HabitResponse

    class HabitViewHolder(private val binding: ItemHabitTeamBinding, private val size: Int) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int, record: HabitRecord, response: HabitResponse) {
            if (position == size - 1) {
                binding.viewItemHabitTeamDivider.visibility = View.GONE
            }

            binding.record = record
            binding.response = response
            binding.btnItemHabitTeamSend.setOnClickListener {
                HabitSendSparkBottomSheet().show((it.context as AppCompatActivity).supportFragmentManager,
                    this.javaClass.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding =
            ItemHabitTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding, list.size)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.onBind(position, list[position], response)
    }

    override fun getItemCount() = list.size
}