package com.spark.android.ui.habit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.R
import com.spark.android.databinding.ItemHabitSendSparkMessageBinding

class HabitSendSparkRecyclerViewAdapter(private val postSendSpark: (String) -> Unit, private val initIsTyping: (Boolean) -> Unit) :
    RecyclerView.Adapter<HabitSendSparkRecyclerViewAdapter.HabitSendSparkViewHolder>() {
    class HabitSendSparkViewHolder(
        private val binding: ItemHabitSendSparkMessageBinding,
        private val postSendSpark: (String) -> Unit,
        private val initIsTyping: (Boolean) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(holder: HabitSendSparkViewHolder, position: Int) {
            var isTyPing = false

            binding.position = position

            binding.layoutItemHabitSendSparkMessage.setOnClickListener {
                lateinit var content: String
                when (position) {
                    FIRST_ITEM -> {
                        isTyPing = !isTyPing
                        initIsTyping(isTyPing)
                    }

                    SECOND_ITEM -> {
                        content =
                            holder.itemView.context.getString(R.string.habit_send_spark_message_first_content)
                        postSendSpark(content)
                    }

                    THIRD_ITEM -> {
                        content =
                            holder.itemView.context.getString(R.string.habit_send_spark_message_second_content)
                        postSendSpark(content)
                    }

                    FOURTH_ITEM -> {
                        content =
                            holder.itemView.context.getString(R.string.habit_send_spark_message_third_content)
                        postSendSpark(content)
                    }

                    FIFTH_ITEM -> {
                        content =
                            holder.itemView.context.getString(R.string.habit_send_spark_message_fourth_content)
                        postSendSpark(content)
                    }

                    else -> {
                        content = "error"
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitSendSparkViewHolder {
        val binding =
            ItemHabitSendSparkMessageBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return HabitSendSparkViewHolder(binding, postSendSpark, initIsTyping)
    }

    override fun onBindViewHolder(holder: HabitSendSparkViewHolder, position: Int) {
        holder.onBind(holder, position)
    }

    override fun getItemCount(): Int {
        return 5
    }

    companion object {
        const val FIRST_ITEM = 0
        const val SECOND_ITEM = 1
        const val THIRD_ITEM = 2
        const val FOURTH_ITEM = 3
        const val FIFTH_ITEM = 4
    }
}