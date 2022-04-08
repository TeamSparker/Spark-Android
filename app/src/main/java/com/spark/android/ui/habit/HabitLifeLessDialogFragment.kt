package com.spark.android.ui.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.spark.android.R
import com.spark.android.databinding.DialogHabitLifeLessBinding
import com.spark.android.ui.habit.viewmodel.HabitViewModel
import com.spark.android.ui.storage.viewmodel.StorageViewModel


class HabitLifeLessDialogFragment : DialogFragment() {
    private var _binding: DialogHabitLifeLessBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogHabitLifeLessBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDismissButton()

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

    private fun initDismissButton(){
        binding.btnHabitLifeLessChecked.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}