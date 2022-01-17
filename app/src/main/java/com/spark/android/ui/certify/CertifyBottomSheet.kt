package com.spark.android.ui.certify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetCertifyBinding

class CertifyBottomSheet() : BottomSheetDialogFragment() {
    private var _binding: BottomSheetCertifyBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetCertifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initCameraBtnClickListener()
        initAlbumBtnClickListener()
    }

    private fun initCameraBtnClickListener() {
        binding.tvCertifyCamera.setOnClickListener {
            // 카메라
            dismiss()
        }
    }

    private fun initAlbumBtnClickListener() {
        binding.tvCertifyAlbum.setOnClickListener {
            // 앨범
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}