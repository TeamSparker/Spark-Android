package com.spark.android.ui.home

import android.content.Intent
import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.R
import com.spark.android.databinding.FragmentHomeMainBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.home.adapter.HomeRecyclerViewAdapter
import com.spark.android.ui.home.finishroomdialog.FinishRoomDialogFragment
import com.spark.android.ui.home.viewmodel.HomeMainViewModel
import com.spark.android.ui.mypage.MyPageActivity
import com.spark.android.util.AnimationUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMainFragment : BaseFragment<FragmentHomeMainBinding>(R.layout.fragment_home_main) {

    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    private val homeMainViewModel by viewModels<HomeMainViewModel>()
    private lateinit var toastAnimation: Animator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeViewModel = homeMainViewModel
        homeMainViewModel.getHomeAllRoom()

        initHomeRecyclerViewAdapter()
        addScrollListenerToHomeRv()
        initHomeListObserver()
        initMyPageBtnClickListener()

    }

    override fun onResume() {
        super.onResume()
        showToastMessage()
    }


    private fun addScrollListenerToHomeRv() {
        binding.rvHomeTicket.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvHomeTicket.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                if (homeMainViewModel.hasNextPage) {
                    if (!homeMainViewModel.isAddLoading && layoutManager.itemCount <= lastPosition + LOAD_POSITION &&
                        !binding.rvHomeTicket.canScrollVertically(STATE_LOWEST)
                    ) {

                        homeMainViewModel.getHomeAllRoom()
                    }
                }
            }
        })
    }

    private fun initHomeListObserver() {
        homeMainViewModel.roomList.observe(viewLifecycleOwner){
            homeRecyclerViewAdapter.updateHomeList(it)
            if(homeMainViewModel.isLoading.value == true){
                homeMainViewModel.updateIsLoading(false)
            }
        }
    }

    private fun initHomeRecyclerViewAdapter() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(::finishRoomEvent)
        binding.rvHomeTicket.adapter = homeRecyclerViewAdapter
    }


    private fun initMyPageBtnClickListener() {
        binding.btnHomeToolBarMyPage.setOnClickListener {
            startActivity(Intent(requireContext(), MyPageActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            })
        }
    }

    private fun showToastMessage() {
        if (homeMainViewModel.getHomeToastMessageState()) {
            homeMainViewModel.updateToastMessage(homeMainViewModel.getHomeToastMessage())
            binding.tvHomeToast.visibility = View.VISIBLE
            toastAnimation =
                requireNotNull(AnimationUtil.grayBoxToastAnimation(binding.tvHomeToast)).apply {
                    doOnStart {
                        homeMainViewModel.setHomeToastMessage("")
                        homeMainViewModel.setHomeToastMessageState(false)
                    }
                    doOnEnd {
                        homeMainViewModel.updateToastMessage("")
                        binding.tvHomeToast.visibility = View.GONE
                    }
                    start()
                }
        }
    }

    private fun finishRoomEvent(roomId: Int, myStatus: String) {
        homeMainViewModel.readFinishHabitRoom(roomId)
        val finishRoomDialog = FinishRoomDialogFragment()
        val bundle = Bundle()
        bundle.putString("myStatus", myStatus)
        finishRoomDialog.arguments = bundle
        finishRoomDialog.show(
            requireActivity().supportFragmentManager, "FinishRoomDialog"
        )
        homeMainViewModel.recoverHomeAllRoom(homeMainViewModel.roomList.value?.size?.minus(1) ?: 100)
    }


    override fun onPause() {
        super.onPause()
        if (::toastAnimation.isInitialized) {
            toastAnimation.cancel()
        }
    }

    companion object {
        private const val LOAD_POSITION = 3
        private const val STATE_LOWEST = 1
    }
}
