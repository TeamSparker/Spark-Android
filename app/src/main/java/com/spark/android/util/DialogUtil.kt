package com.spark.android.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.spark.android.R
import com.spark.android.databinding.DialogUtilBinding

class DialogUtil(private val dialogMode: Int, private val doAfterConfirm: () -> Unit) :
    DialogFragment() {
    private var _binding: DialogUtilBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogUtilBinding.inflate(requireActivity().layoutInflater)
        setCancelBtnVisibility()
        setMessage()
        setConfirmText()
        setConfirmTextClickListener()
        setCancelTextClickListener()
        return activity?.let {
            val dialog = AlertDialog.Builder(it).create()
            dialog.setView(binding.root)
            dialog
        } ?: throw IllegalStateException()
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
            else -> throw IllegalStateException()
        }
    }

    private fun setConfirmText() {
        binding.confirmText = when (dialogMode) {
            STOP_SIGNUP_MODE -> getString(R.string.profile_dialog_stop_signup)
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
    }
}
