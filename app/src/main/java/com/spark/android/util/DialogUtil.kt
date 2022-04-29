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
        setCancelable()
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
                    (resources.displayMetrics.widthPixels * 0.91).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_spark_white_fill_2_rect)
            }
        }
    }

    private fun setCancelable() {
        isCancelable = when (dialogMode) {
            UPDATE_CHECK -> false
            else -> true
        }
    }

    private fun setCancelBtnVisibility() {
        binding.tvDialogUtilCancel.visibility = when (dialogMode) {
            UPDATE_CHECK -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }

    private fun setMessage() {
        binding.tvDialogUtilContent.text = when (dialogMode) {
            UPDATE_CHECK -> getString(R.string.intro_update_check_dialog_content)
            STOP_SIGNUP_MODE -> getString(R.string.profile_dialog_content)
            STOP_CERTIFY_PHOTO -> getString(R.string.certify_dialog_content)
            STOP_CERTIFY_TIMER -> getString(R.string.timer_dialog_stop_content)
            STOP_TIMER -> getString(R.string.timer_dialog_stop_timer_content)
            CHECK_CONFIRM_MODE -> getString(R.string.select_confirm_method_dialog_title)
            HABIT_REST -> getString(R.string.habit_today_rest_dialog)
            WAITING_ROOM_BOTTOM_SHEET_HOST -> getString(R.string.waiting_room_bottom_sheet_delete_room_host)
            WAITING_ROOM_BOTTOM_SHEET_GUEST -> getString(R.string.waiting_room_bottom_sheet_delete_room_guest)
            STOP_MODIFY_PROFILE -> getString(R.string.profile_modify_dialog_content)
            WITHDRAWAL -> getString(R.string.withdrawal_dialog_content)
            else -> throw IllegalStateException()
        }
    }

    private fun setConfirmText() {
        binding.confirmText = when (dialogMode) {
            UPDATE_CHECK -> getString(R.string.intro_update_check_dialog_confirm)
            STOP_SIGNUP_MODE, STOP_MODIFY_PROFILE -> getString(R.string.profile_dialog_stop_signup)
            STOP_CERTIFY_PHOTO, STOP_CERTIFY_TIMER -> getString(R.string.certify_dialog_stop_certify)
            STOP_TIMER -> getString(R.string.timer_dialog_stop_timer)
            CHECK_CONFIRM_MODE -> getString(R.string.select_confirm_method_dialog_done)
            HABIT_REST -> getString(R.string.habit_today_rest_confirm)
            WAITING_ROOM_BOTTOM_SHEET_HOST -> getString(R.string.waiting_room_bottom_sheet_delete_room_host_button)
            WAITING_ROOM_BOTTOM_SHEET_GUEST -> getString(R.string.waiting_room_bottom_sheet_delete_room_guest_button)
            WITHDRAWAL -> getString(R.string.withdrawal_withdraw)
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
        const val UPDATE_CHECK = 0
        const val STOP_SIGNUP_MODE = 1
        const val STOP_CERTIFY_PHOTO = 2
        const val STOP_CERTIFY_TIMER = 3
        const val STOP_TIMER = 4
        const val CHECK_CONFIRM_MODE = 5
        const val HABIT_REST = 6
        const val WAITING_ROOM_BOTTOM_SHEET_HOST = 7
        const val WAITING_ROOM_BOTTOM_SHEET_GUEST = 8
        const val STOP_MODIFY_PROFILE = 9
        const val WITHDRAWAL = 10
    }
}
