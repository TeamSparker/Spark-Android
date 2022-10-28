package com.teamsparker.android.ui.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentFlameRoadMapDialogBinding
import com.teamsparker.android.ui.habit.adapter.FlameRoadMapAdapter
import com.teamsparker.android.ui.habit.flameroadmap.*
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        initViewPagerAdapter()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
