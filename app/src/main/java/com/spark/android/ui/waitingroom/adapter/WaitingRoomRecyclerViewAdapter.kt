package com.spark.android.ui.waitingroom.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.Member
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.databinding.ItemWaitingRoomRecyclerviewBinding
import com.spark.android.ui.waitingroom.data.WaitingData
import java.util.logging.Handler

class WaitingRoomRecyclerViewAdapter :RecyclerView.Adapter<WaitingRoomRecyclerViewAdapter.WaitingRoomRecyclerViewHolder>() {

    var members = mutableListOf<Member>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaitingRoomRecyclerViewHolder {
        val binding = ItemWaitingRoomRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WaitingRoomRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaitingRoomRecyclerViewHolder, position: Int) {
        holder.onBind(members[position])
    }

    override fun getItemCount(): Int = members.size

    inner class WaitingRoomRecyclerViewHolder(val binding: ItemWaitingRoomRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Member) {
            binding.members = data
        }

    }
}