package com.spark.android.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.entity.response.Room
import com.spark.android.databinding.ItemHomeLoadingBinding
import com.spark.android.databinding.ItemHomeRecyclerviewBinding
import com.spark.android.databinding.ItemHomeRecyclerviewWaitingBinding
import com.spark.android.ui.habit.HabitActivity
import com.spark.android.ui.home.adapter.multiview.INFINITE_LOADING
import com.spark.android.ui.home.adapter.multiview.TICKET_STARTED
import com.spark.android.ui.home.adapter.multiview.TICKET_WAITING
import com.spark.android.ui.waitingroom.WaitingRoomActivity
import java.lang.RuntimeException

class HomeRecyclerViewAdapter(
    private val finishRoomEvent: ((Int, String) -> Unit)? = null
) : ListAdapter<Room, RecyclerView.ViewHolder>(homeDiffUtil) {

    private lateinit var itemHomeRecyclerviewBinding: ItemHomeRecyclerviewBinding
    private lateinit var itemHomeRecyclerviewWaitingBinding: ItemHomeRecyclerviewWaitingBinding
    private lateinit var itemHomeLoadingBinding: ItemHomeLoadingBinding

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).infiniteLoading == "loading") {
            INFINITE_LOADING
        } else {
            if (getItem(position).isStarted) {
                TICKET_STARTED
            } else {
                TICKET_WAITING
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TICKET_STARTED -> {
                itemHomeRecyclerviewBinding = ItemHomeRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeRecyclerViewHolder(itemHomeRecyclerviewBinding)
            }
            TICKET_WAITING -> {
                itemHomeRecyclerviewWaitingBinding = ItemHomeRecyclerviewWaitingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeRecyclerViewWaitingHolder(itemHomeRecyclerviewWaitingBinding)
            }
            INFINITE_LOADING -> {
                itemHomeLoadingBinding = ItemHomeLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeRecyclerViewLoadingViewHolder(itemHomeLoadingBinding)
            }
            else -> {
                throw RuntimeException("알 수 없는 viewType error")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeRecyclerViewHolder) {
            holder.onBind(getItem(position), finishRoomEvent)
        } else if (holder is HomeRecyclerViewWaitingHolder) {
            holder.onBind(getItem(position))
        }
    }

    fun updateHomeList(rooms: List<Room>) {
        submitList(rooms)
    }

    inner class HomeRecyclerViewHolder(val binding: ItemHomeRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Room, finishRoomEvent: ((Int, String) -> Unit)? = null) {
            binding.room = data
            binding.root.setOnClickListener {
                if (data.myStatus == "COMPLETE" || data.myStatus == "FAIL") {
                    finishRoomEvent?.invoke(data.roomId, data.myStatus)
                } else {
                    val intent = Intent(it.context, HabitActivity::class.java).apply {
                        putExtra("roomId", getItem(absoluteAdapterPosition).roomId)
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    }
                    startActivity(it.context, intent, null)
                }
            }
        }
    }


    inner class HomeRecyclerViewWaitingHolder(val binding: ItemHomeRecyclerviewWaitingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Room) {
            binding.room = data
        }

        init {
            itemView.setOnClickListener {
                val intent = Intent(it.context, WaitingRoomActivity::class.java).apply {
                    putExtra("roomId", getItem(absoluteAdapterPosition).roomId)
                    putExtra("startPoint", WaitingRoomActivity.START_FROM_HOME)
                }
                startActivity(it.context, intent, null)
            }
        }
    }

    class HomeRecyclerViewLoadingViewHolder(val binding: ItemHomeLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)


    companion object {
        private val homeDiffUtil = object : DiffUtil.ItemCallback<Room>() {
            override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean =
                oldItem.roomId == newItem.roomId

            override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean =
                oldItem == newItem
        }
    }
}