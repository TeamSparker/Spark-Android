package com.teamsparker.android.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.teamsparker.android.R
import com.teamsparker.android.databinding.DialogInstaShareBinding
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.CLICK_FEED
import com.teamsparker.android.util.FirebaseLogUtil.CLICK_SHARE_INSTAGRAM

class InstaShareDialogFragment : DialogFragment() {
    private var _binding: DialogInstaShareBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogInstaShareBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.day = 66 - (arguments?.getInt("leftDay") ?: -1)
        setConfirmTextClickListener()
        setCancelTextClickListener()
    }

    override fun onStart() {
        super.onStart()
        setLayout()
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setCancelable(false)
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.88).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_spark_white_fill_2_rect)
            }
        }
    }

    private fun setConfirmTextClickListener() {
        binding.btnInstaShare.setOnClickListener {
            FirebaseLogUtil.logClickEvent(CLICK_SHARE_INSTAGRAM)
            setFragmentResult(INSTA_DIALOG, bundleOf(SHARE_MODE to SHARE))
            dismiss()
        }
    }

    private fun setCancelTextClickListener() {
        binding.tvInstaCancel.setOnClickListener {
            FirebaseLogUtil.logClickEvent(CLICK_FEED)
            setFragmentResult(INSTA_DIALOG, bundleOf(SHARE_MODE to NO_SHARE))
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val INSTA_DIALOG = "InstaDialog"
        const val SHARE_MODE = "ShareMode"
        const val NO_SHARE = false
        const val SHARE = true
    }
}
