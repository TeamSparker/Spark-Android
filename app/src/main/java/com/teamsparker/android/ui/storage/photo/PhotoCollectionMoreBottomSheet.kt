package com.teamsparker.android.ui.storage.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamsparker.android.databinding.BottomSheetPhotoCollectionMoreBinding

class PhotoCollectionMoreBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetPhotoCollectionMoreBinding? = null
    val binding get() = _binding ?: error("Binding not initialized to referenxe the view.")
    private var changePhotoBtnClickListener: (() -> Unit)? = null

    fun setChangePhotoBtnClickListener(listener: (() -> Unit)) {
        changePhotoBtnClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetPhotoCollectionMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initChangePhotoBtnClickListener()
    }

    private fun initChangePhotoBtnClickListener() {
        binding.tvMainPhotoPickMoreChangePhoto.setOnClickListener {
            changePhotoBtnClickListener?.invoke()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
