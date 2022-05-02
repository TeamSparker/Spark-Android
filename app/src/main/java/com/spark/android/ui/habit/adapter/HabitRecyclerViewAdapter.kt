package com.spark.android.ui.habit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.HabitRecord
import com.spark.android.data.remote.entity.response.HabitResponse
import com.spark.android.databinding.ItemHabitTeamBinding
import com.spark.android.ui.habit.HabitSendSparkActivity

class HabitRecyclerViewAdapter :
    ListAdapter<HabitRecord, HabitRecyclerViewAdapter.HabitViewHolder>(habitDiffUtil) {
    lateinit var response: HabitResponse

    class HabitViewHolder(private val binding: ItemHabitTeamBinding, private val size: Int) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int, record: HabitRecord, response: HabitResponse) {
            if (position == size - 1) {
                binding.viewItemHabitTeamDivider.visibility = View.GONE
            }

            binding.record = record
            binding.response = response
            binding.btnItemHabitTeamSend.setOnClickListener {
//                val habitSendSparkBottomSheet = HabitSendSparkBottomSheet()
//                habitSendSparkBottomSheet.setSelectedItem(record.nickname, record.recordId)
//                habitSendSparkBottomSheet.show((it.context as AppCompatActivity).supportFragmentManager,
//                    this.javaClass.name)

                val intent = Intent(it.context, HabitSendSparkActivity::class.java)
                intent.putExtra("roomId", response.roomId)
                intent.putExtra("recordId", record.recordId)
                intent.putExtra("nickname", record.nickname)
                intent.putExtra("profileImg", record.profileImg)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding =
            ItemHabitTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding, currentList.size)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.onBind(position, getItem(position), response)
    }

    fun updateHabitList(habits: List<HabitRecord>) {
        submitList(habits)
    }

    companion object {
        private val habitDiffUtil = object : DiffUtil.ItemCallback<HabitRecord>() {
            override fun areItemsTheSame(oldItem: HabitRecord, newItem: HabitRecord): Boolean =
                oldItem.recordId == newItem.recordId

            override fun areContentsTheSame(oldItem: HabitRecord, newItem: HabitRecord): Boolean =
                oldItem == newItem
        }
    }
}
