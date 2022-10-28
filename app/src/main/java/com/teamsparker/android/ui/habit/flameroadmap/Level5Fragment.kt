package com.teamsparker.android.ui.habit.flameroadmap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentLevel5Binding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel

class Level5Fragment : BaseFragment<FragmentLevel5Binding>(R.layout.fragment_level5){

    private val habitViewModel by activityViewModels<HabitViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.habitViewMdoel = habitViewModel
    }
}
