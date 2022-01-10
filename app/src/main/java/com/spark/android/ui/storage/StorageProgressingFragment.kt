package com.spark.android.ui.storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageProgressingBinding
import com.spark.android.ui.base.BaseFragment

class StorageProgressingFragment :
    BaseFragment<FragmentStorageProgressingBinding>(R.layout.fragment_storage_progressing) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage_progressing, container, false)
    }

}