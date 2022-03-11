package com.spark.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentHomeMainBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.home.adapter.HomeRecyclerViewAdapter
import com.spark.android.ui.home.viewmodel.HomeMainViewModel
import com.spark.android.ui.mypage.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMainFragment : BaseFragment<FragmentHomeMainBinding>(R.layout.fragment_home_main) {

    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    private val homeMainViewModel by viewModels<HomeMainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeViewModel = homeMainViewModel


        initHomeRecyclerViewAdapter()
        updateHomeRecyclerViewAdapter()
        initMyPageBtnClickListener()

    }



    private fun updateHomeRecyclerViewAdapter(){
        homeMainViewModel.getHomeAllRoom(-1,100)
        homeMainViewModel.roomList.observe(viewLifecycleOwner) {
            homeRecyclerViewAdapter.updateHomeList(it)
            homeMainViewModel.updateIsLoading()
        }
    }

    private fun initHomeRecyclerViewAdapter() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter()
        binding.rvHomeTicket.adapter = homeRecyclerViewAdapter
    }

    private fun initMyPageBtnClickListener() {
        binding.btnHomeToolBarMyPage.setOnClickListener {
            startActivity(Intent(requireContext(), MyPageActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            })
        }
    }

}
