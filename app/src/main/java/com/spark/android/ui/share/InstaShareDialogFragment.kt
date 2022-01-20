package com.spark.android.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.spark.android.R
import com.spark.android.databinding.DialogInstaShareBinding

class InstaShareDialogFragment : DialogFragment() {
    private var _binding: DialogInstaShareBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogInstaShareBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.85).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_spark_white_fill_2_rect)
            }
        }
    }

    private fun setConfirmTextClickListener() {
        binding.btnInstaShare.setOnClickListener {
            dismiss()
        }
    }

    private fun setCancelTextClickListener() {
        binding.tvInstaCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
