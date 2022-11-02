package com.teamsparker.android.ui.habit.flameroadmap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentLevel2Binding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel

class Level2Fragment : BaseFragment<FragmentLevel2Binding>(R.layout.fragment_level2){

    private val habitViewModel by activityViewModels<HabitViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.habitViewMdoel = habitViewModel
    }
}
