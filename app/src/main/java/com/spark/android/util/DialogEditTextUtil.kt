package com.spark.android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.spark.android.R
import com.spark.android.databinding.DialogEditTextUtilBinding

class DialogEditTextUtil(
    private val dialogMode: Int,
    private val roomName: String,
    private val doAfterConfirm: () -> Unit,
) :
    DialogFragment() {
    private var _binding: DialogEditTextUtilBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_edit_text_util, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
        setMessage()
        setEditTextHint()
        setConfirmText()
        setConfirmTextClickListener()
        setCancelTextClickListener()
        initEditTextFocusListener()
        initEditTextListener()
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

    private fun setMessage() {
        binding.tvDialogEditTextUtilContent.text = when (dialogMode) {
            EXIT_HABIT_ROOM -> getString(R.string.habit_more_exit_room_dialog_content)
            else -> throw IllegalStateException()
        }
    }

    private fun setEditTextHint() {
        binding.roomName = roomName
    }

    private fun setConfirmText() {
        binding.confirmText = when (dialogMode) {
            EXIT_HABIT_ROOM -> getString(R.string.habit_more_exit_room_dialog_confirm)
            else -> throw IllegalStateException()
        }
    }

    private fun setConfirmTextClickListener() {
        binding.tvDialogEditTextUtilConfirm.setOnClickListener {
            doAfterConfirm()
            dismiss()
        }
    }

    private fun setCancelTextClickListener() {
        binding.tvDialogEditTextUtilCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initEditTextFocusListener() {
        binding.etDialogEditTextUtil.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewDialogEditTextUtilUnderBar.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.viewDialogEditTextUtilUnderBar.context,
                        R.color.spark_pinkred
                    )
                )
            }
        }
    }

    private fun initEditTextListener() {
        binding.etDialogEditTextUtil.doAfterTextChanged {
            if (it.toString() == roomName) {
                binding.tvDialogEditTextUtilConfirm.apply {
                    isEnabled = true
                    setTextColor(
                        ContextCompat.getColor(
                            binding.tvDialogEditTextUtilConfirm.context,
                            R.color.spark_dark_pinkred
                        )
                    )
                }
            } else {
                binding.tvDialogEditTextUtilConfirm.apply {
                    isEnabled = false
                    setTextColor(
                        ContextCompat.getColor(
                            binding.tvDialogEditTextUtilConfirm.context,
                            R.color.spark_dark_gray
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXIT_HABIT_ROOM = 0
    }
}
