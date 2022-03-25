package com.spark.android.ui.habit.userguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.spark.android.R
import com.spark.android.databinding.FragmentUserGuideDialogBinding
import com.spark.android.ui.habit.userguide.adapter.UserGuideAdapter


class UserGuideFragmentDialog : DialogFragment() {

    private var _binding: FragmentUserGuideDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var userGuideAdapter: UserGuideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_user_guide_dialog,container,false)
        initUserGuideAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        initUserGuideAdapter()
        initUserGuideDismissButton()
        initScrollListener()
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.91).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_spark_white_fill_2_rect)
            }
        }
    }

    private fun initUserGuideAdapter(){
        val fragmentList = listOf(UserGuideFirstFragment(),UserGuideSecondFragment(),UserGuideThirdFragment())

        userGuideAdapter =  UserGuideAdapter(this)
        userGuideAdapter.fragments.addAll(fragmentList)

        binding.vpUserGuide.adapter = userGuideAdapter
    }

    private fun initUserGuideDismissButton(){
        binding.tvUserGuideDismissButton.setOnClickListener {
            dismiss()
        }
    }

    private fun initScrollListener(){
        binding.vpUserGuide.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.position = position
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}