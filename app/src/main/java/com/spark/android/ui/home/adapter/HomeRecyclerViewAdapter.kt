package com.spark.android.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.databinding.ItemHomeRecyclerviewBinding
import com.spark.android.databinding.ItemHomeRecyclerviewWaitingBinding
import com.spark.android.ui.habit.HabitActivity
import com.spark.android.ui.home.adapter.multiview.TICKET_STARTED
import com.spark.android.ui.home.adapter.multiview.TICKET_WAITING
import com.spark.android.ui.home.data.TicketData
import java.lang.RuntimeException
import kotlin.properties.Delegates

class HomeRecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemHomeRecyclerviewBinding: ItemHomeRecyclerviewBinding
    private lateinit var itemHomeRecyclerviewWaitingBinding: ItemHomeRecyclerviewWaitingBinding
    val ticketList = mutableListOf<TicketData>()

    override fun getItemViewType(position: Int): Int {
        return if (ticketList[position].isStarted) {
            TICKET_STARTED
        } else {
            TICKET_WAITING
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
            else -> {
                throw RuntimeException("알 수 없는 viewType error")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeRecyclerViewHolder) {
            holder.onBind(ticketList[position])
        } else if (holder is HomeRecyclerViewWaitingHolder) {
            holder.onBind(ticketList[position])
        }
    }

    override fun getItemCount(): Int = ticketList.size

    inner class HomeRecyclerViewHolder(val binding: ItemHomeRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TicketData) {
            binding.ticketData = data
        }

        init {
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context,HabitActivity::class.java).apply {
                    this.putExtra("roomId",ticketList[adapterPosition].roomId)
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                startActivity(binding.root.context,intent,null)
            }
        }
    }

    inner class HomeRecyclerViewWaitingHolder(val binding: ItemHomeRecyclerviewWaitingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TicketData) {
            binding.ticketData = data
        }
    }

}