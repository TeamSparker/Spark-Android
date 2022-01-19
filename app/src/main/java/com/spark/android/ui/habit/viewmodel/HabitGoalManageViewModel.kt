package com.spark.android.ui.habit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitGoalManageViewModel : ViewModel() {
    val time = MutableLiveData("")
    val goal = MutableLiveData("")
}