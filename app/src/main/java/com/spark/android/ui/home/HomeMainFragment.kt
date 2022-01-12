package com.spark.android.ui.home

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentHomeMainBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.home.adapter.HomeRecyclerViewAdapter
import com.spark.android.ui.home.data.TicketData


class HomeMainFragment : BaseFragment<FragmentHomeMainBinding>(R.layout.fragment_home_main) {

    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHomeRecyclerViewAdapter()
    }

    private fun initHomeRecyclerViewAdapter(){
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter()

        binding.rvHomeTicket.adapter = homeRecyclerViewAdapter

        homeRecyclerViewAdapter.ticketList.addAll(
            listOf(
                TicketData(1,"일기 쓰기","D-63",null,3,false,true,7,3),
                TicketData(2,"일기 쓰기","D-21",null,3,true,true,7,3),
                TicketData(3,"일기 쓰기","D-43",null,3,false,true,7,3),
                TicketData(3,"일기 쓰기","D-2",null,3,true,true,7,3)
            )
        )

        homeRecyclerViewAdapter.notifyDataSetChanged()

    }
}