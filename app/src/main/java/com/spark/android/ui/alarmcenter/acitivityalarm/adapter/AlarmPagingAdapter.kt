package com.spark.android.ui.alarmcenter.acitivityalarm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.BR
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.databinding.ItemAlarmListBinding
import com.spark.android.ui.alarmcenter.acitivityalarm.adapter.AlarmPagingAdapter.ActivityAlarmViewHolder

class AlarmPagingAdapter :
    PagingDataAdapter<Alarm, ActivityAlarmViewHolder>(activityAlarmDiffUtil) {
    class ActivityAlarmViewHolder(
        private val binding: ItemAlarmListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm) {
            binding.setVariable(BR.alarm, alarm)
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ActivityAlarmViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityAlarmViewHolder =
        ActivityAlarmViewHolder(
            ItemAlarmListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        private val activityAlarmDiffUtil = object : DiffUtil.ItemCallback<Alarm>() {
            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
                oldItem.noticeId == newItem.noticeId

            override fun areContentsTheSame(
                oldItem: Alarm,
                newItem: Alarm
            ): Boolean =
                oldItem == newItem

        }
    }
}
