package com.spark.android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.spark.android.R
import com.spark.android.databinding.DialogUtilBinding

class DialogUtil(private val dialogMode: Int, private val doAfterConfirm: () -> Unit) :
    DialogFragment() {
    private var _binding: DialogUtilBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_util, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
        setCancelBtnVisibility()
        setMessage()
        setConfirmText()
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
                    (resources.displayMetrics.widthPixels * 0.99).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    private fun setCancelBtnVisibility() {
//        binding.tvDialogUtilCancel.visibility = when (dialogMode) {
//            mode -> View.INVISIBLE
//            else -> View.VISIBLE
//        }
    }

    private fun setMessage() {
        binding.tvDialogUtilContent.text = when (dialogMode) {
            STOP_SIGNUP_MODE -> getString(R.string.profile_dialog_content)
            STOP_CERTIFY_PHOTO -> getString(R.string.certify_dialog_content)
            STOP_CERTIFY_TIMER -> getString(R.string.timer_dialog_stop_content)
            STOP_TIMER -> getString(R.string.timer_dialog_stop_timer_content)
            else -> throw IllegalStateException()
        }
    }

    private fun setConfirmText() {
        binding.confirmText = when (dialogMode) {
            STOP_SIGNUP_MODE -> getString(R.string.profile_dialog_stop_signup)
            STOP_CERTIFY_PHOTO, STOP_CERTIFY_TIMER -> getString(R.string.certify_dialog_stop_certify)
            STOP_TIMER -> getString(R.string.timer_dialog_stop_timer)
            else -> throw IllegalStateException()
        }
    }

    private fun setConfirmTextClickListener() {
        binding.tvDialogUtilConfirm.setOnClickListener {
            doAfterConfirm()
            dismiss()
        }
    }

    private fun setCancelTextClickListener() {
        binding.tvDialogUtilCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val STOP_SIGNUP_MODE = 0
        const val STOP_CERTIFY_PHOTO = 1
        const val STOP_CERTIFY_TIMER = 2
        const val STOP_TIMER = 3
    }
}
