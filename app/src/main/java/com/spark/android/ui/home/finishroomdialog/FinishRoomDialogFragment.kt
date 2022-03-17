package com.spark.android.ui.home.finishroomdialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentFinishRoomDialogBinding
import com.spark.android.databinding.FragmentInputCodeDialogBinding
import com.spark.android.ui.home.viewmodel.HomeMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.sql.ClientInfoStatus

@AndroidEntryPoint
class FinishRoomDialogFragment : DialogFragment() {

    private var _binding: FragmentFinishRoomDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var myStatus: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_finish_room_dialog,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMyStatus()
        binding.myStatus = myStatus
        initDismissButton()

    }

    override fun onStart() {
        super.onStart()
        setLayout()
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.99).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    private fun initMyStatus(){
        myStatus = requireNotNull(arguments?.getString("myStatus", "FAIL"))
    }

    private fun initDismissButton(){
        binding.btnHomeFinishDialogCheck.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}