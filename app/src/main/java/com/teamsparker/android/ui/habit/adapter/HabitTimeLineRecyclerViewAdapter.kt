package com.teamsparker.android.ui.habit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamsparker.android.data.remote.entity.response.Timeline
import com.teamsparker.android.databinding.ItemHabitTimeLineBinding
import com.teamsparker.android.util.ItemDiffCallback

class HabitTimeLineRecyclerViewAdapter() :
    ListAdapter<Timeline, HabitTimeLineRecyclerViewAdapter.HabitTImeLineRecyclerViewHolder>(
        ItemDiffCallback<Timeline>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.title == new.title }
        )
    ) {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitTImeLineRecyclerViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }

        val binding = ItemHabitTimeLineBinding.inflate(inflater, parent, false)

        return HabitTImeLineRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitTImeLineRecyclerViewHolder, position: Int) {
        holder.onBInd(getItem(position))
    }

    class HabitTImeLineRecyclerViewHolder(private val binding: ItemHabitTimeLineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBInd(data: Timeline) {
            binding.timeLine = data
        }
    }
}
