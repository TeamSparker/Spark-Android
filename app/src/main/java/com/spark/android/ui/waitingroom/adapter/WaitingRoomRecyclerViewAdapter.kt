package com.spark.android.ui.waitingroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.Member
import com.spark.android.databinding.ItemWaitingRoomRecyclerviewBinding

class WaitingRoomRecyclerViewAdapter :
    ListAdapter<Member, WaitingRoomRecyclerViewAdapter.WaitingRoomRecyclerViewHolder>(
        waitingRoomDiffUtil
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaitingRoomRecyclerViewHolder {
        val binding = ItemWaitingRoomRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WaitingRoomRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaitingRoomRecyclerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun updateMemberList(members: List<Member>) {
        submitList(members)
    }

    class WaitingRoomRecyclerViewHolder(val binding: ItemWaitingRoomRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Member) {
            binding.members = data
        }

    }

    companion object {
        private val waitingRoomDiffUtil = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean =
                oldItem.userId == newItem.userId

            override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean =
                oldItem == newItem
        }
    }
}
