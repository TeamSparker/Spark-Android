package com.teamsparker.android.ui.home.finishroomdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentFinishRoomDialogBinding
import dagger.hilt.android.AndroidEntryPoint

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
                    (resources.displayMetrics.widthPixels * 0.91).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_spark_white_fill_2_rect)
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
