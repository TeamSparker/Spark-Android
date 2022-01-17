package com.spark.android.ui.joincode.inputcode

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentInputCodeDialogBinding
import com.spark.android.ui.joincode.JoinCodeActivity
import com.spark.android.ui.joincode.inputcode.viewModel.InputCodeFragmentDialogViewModel
import com.spark.android.util.KeyBoardUtil


class InputCodeFragmentDialog : DialogFragment() {

    private var _binding: FragmentInputCodeDialogBinding? = null
    private val binding get() = _binding!!
    private val inputCodeFragmentDialogViewModel by viewModels<InputCodeFragmentDialogViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_input_code_dialog, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputCodeFragmentDialogViewModel = inputCodeFragmentDialogViewModel
        initButtonClickListener()
        initEditTextClearFocus()
        initClearErrorMessage()
    }

    private fun initButtonClickListener() {
        binding.btnInputCodeCheck.setOnClickListener {
            binding.etInputCodeContent.clearFocus()

            val intent = Intent(requireActivity(), JoinCodeActivity::class.java).apply {
                this.putExtra("roomCode", inputCodeFragmentDialogViewModel.roomCode.value)
            }
            startActivity(intent)
        }
    }


    private fun initEditTextClearFocus() {
        binding.layoutInputCodeDialog.setOnClickListener {
            binding.etInputCodeContent.clearFocus()
        }
    }

    private fun initClearErrorMessage() {
        binding.etInputCodeContent.setOnFocusChangeListener { view, focused ->
            if(!focused){inputCodeFragmentDialogViewModel.clearErrorMessage()}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}