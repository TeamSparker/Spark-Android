package com.teamsparker.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teamsparker.android.R
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.storage.StorageMode.Companion.COMPLETE
import com.teamsparker.android.ui.storage.StorageMode.Companion.INCOMPLETE
import com.teamsparker.android.ui.storage.StorageMode.Companion.PROGRESSING
import com.teamsparker.android.ui.storage.adapter.StorageViewPagerOutAdapter
import com.teamsparker.android.ui.storage.viewmodel.StorageViewModel
import android.animation.ObjectAnimator
import androidx.navigation.fragment.navArgs
import com.teamsparker.android.databinding.FragmentStorageBinding
import kotlin.IllegalStateException

class StorageFragment : BaseFragment<FragmentStorageBinding>(R.layout.fragment_storage) {
    private lateinit var viewPagerOutAdapter: StorageViewPagerOutAdapter
    private val storageViewModel by activityViewModels<StorageViewModel>()
    private val args: StorageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelForDataBinding()
        when (args.cardType) {
            "progressingCard" -> storageViewModel.initProgressMode()
            "completeCard" -> storageViewModel.initCompleteMode()
            "incompleteCard" -> storageViewModel.initIncompleteMode()
            else -> throw IllegalStateException()
        }
        initVpAdapter()
        setVpAdapterCardList()

        observeModeForChangingTab()
        initVpDisableUserScroll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        storageViewModel.initFirstLoading(false)
        storageViewModel.initProgressMode()
    }

    private fun initVpDisableUserScroll() {
        binding.vpStorageOut.isUserInputEnabled = false

    }

    private fun initViewModelForDataBinding() {
        binding.storageViewModel = storageViewModel
    }

    private fun initVpAdapter() {
        viewPagerOutAdapter = StorageViewPagerOutAdapter(requireActivity())
        binding.vpStorageOut.adapter = viewPagerOutAdapter
    }

    private fun setVpAdapterCardList() {
        val cardFragmentList = listOf(
            StorageProgressingFragment(),
            StorageCompleteFragment(),
            StorageIncompleteFragment()
        )
        viewPagerOutAdapter.fragments.clear()
        viewPagerOutAdapter.fragments.addAll(cardFragmentList)
    }

    private fun indicatorBarAnimator(indicator: View) {
        val indicatorAnim = ObjectAnimator.ofFloat(indicator, "scaleX", 0f, 1.0f)
        indicator.pivotX = 0f
        indicatorAnim.duration = 150
        indicatorAnim.start()
    }

    private fun observeModeForChangingTab() {
        storageViewModel.storageMode.observe(viewLifecycleOwner) { mode ->
            when (mode) {
                PROGRESSING -> {
                    binding.vpStorageOut.currentItem = 0
                    viewPagerOutAdapter.notifyDataSetChanged()
                    storageViewModel.initVpInnerMode("progressingCard")
                    indicatorBarAnimator(binding.viewStorageProgressingIndicator)
                    if (!storageViewModel.isInitProgressing) {
                        storageViewModel.initStorageNetwork(PROGRESSING, -1, 30)
                    }
                }
                COMPLETE -> {
                    binding.vpStorageOut.currentItem = 1
                    viewPagerOutAdapter.notifyDataSetChanged()
                    storageViewModel.initVpInnerMode("completeCard")
                    indicatorBarAnimator(binding.viewStorageCompleteIndicator)
                    if (!storageViewModel.isInitComplete) {
                        storageViewModel.initStorageNetwork(COMPLETE, -1, 30)
                    }
                }
                INCOMPLETE -> {
                    binding.vpStorageOut.currentItem = 2
                  viewPagerOutAdapter.notifyDataSetChanged()
                    storageViewModel.initVpInnerMode("incompleteCard")
                    indicatorBarAnimator(binding.viewStorageIncompleteIndicator)
                    if (!storageViewModel.isInitIncomplete) {
                        storageViewModel.initStorageNetwork(INCOMPLETE, -1, 30)
                    }
                }
                else -> throw IllegalStateException()
            }
        }
    }
}
