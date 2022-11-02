package com.teamsparker.android.ui.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentFlameRoadMapDialogBinding
import com.teamsparker.android.ui.habit.adapter.FlameRoadMapAdapter
import com.teamsparker.android.ui.habit.flameroadmap.*
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class FlameRoadMapDialogFragment : DialogFragment() {

    private val habitViewModel by activityViewModels<HabitViewModel>()
    private lateinit var flameRoadMapAdapter: FlameRoadMapAdapter

    private var _binding: FragmentFlameRoadMapDialogBinding? = null
    private val binding
        get() = _binding ?: error(getString(com.teamsparker.android.R.string.binding_error))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFlameRoadMapDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.habitViewModel = habitViewModel
        initViewPagerAdapter()
        setViewPagerOption()
        initViewPagerPositionListener()
        initCheckButtonOnclickListener()
        initViewPagerPosition()
    }

    override fun onStart() {
        super.onStart()
        setLayout()
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

    private fun initViewPagerAdapter() {
        val flakeList = listOf(
            Level1Fragment(),
            Level2Fragment(),
            Level3Fragment(),
            Level4Fragment(),
            Level5Fragment(),
            Level6Fragment()
        )

        flameRoadMapAdapter = FlameRoadMapAdapter(this)
        flameRoadMapAdapter.fragments.addAll(flakeList)
        binding.vpFlameRoadmap.adapter = flameRoadMapAdapter
    }

    private fun setViewPagerOption() {
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.flameRoadMapPageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.flameRoadMapPagerWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.vpFlameRoadmap.apply {
            offscreenPageLimit = 3
            setPageTransformer { page, position ->
                // 미리보기 살짝 보이기
                page.translationX = position * -offsetPx
                // 미리보기 크기 조절
                var focusedPageDistanceRatio = 1 - abs(position)
                page.scaleY = 0.7f + focusedPageDistanceRatio * 0.3f
                page.scaleX = 0.7f + focusedPageDistanceRatio * 0.3f
            }
        }
    }

    private fun initViewPagerPositionListener() {
        binding.vpFlameRoadmap.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.level = position + 1
                }
            })
    }

    private fun initCheckButtonOnclickListener() {
        binding.checkButton.setOnClickListener {
            dismiss()
        }
    }

    private fun initViewPagerPosition() {
        binding.vpFlameRoadmap.currentItem
        when (habitViewModel.habitInfo.value?.leftDay) {
            0 -> binding.vpFlameRoadmap.currentItem = 5
            in 1..6 -> binding.vpFlameRoadmap.currentItem = 4
            in 7..32 -> binding.vpFlameRoadmap.currentItem = 3
            in 33..58 -> binding.vpFlameRoadmap.currentItem = 2
            in 59..62 -> binding.vpFlameRoadmap.currentItem = 1
            in 63..65 -> binding.vpFlameRoadmap.currentItem = 0
            else -> throw IllegalArgumentException("leftDay 범위 에러")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
