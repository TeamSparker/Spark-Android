package com.spark.android.ui.joincode.inputcode

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentInputCodeDialogBinding
import com.spark.android.ui.joincode.JoinCodeActivity
import com.spark.android.ui.joincode.JoinCodeActivity.Companion.GO_TO_WAITING_ROOM
import com.spark.android.ui.joincode.inputcode.viewModel.InputCodeFragmentDialogViewModel
import com.spark.android.util.EventObserver
import com.spark.android.util.FirebaseLogUtil
import com.spark.android.util.FirebaseLogUtil.CLICK_OK_INPUT_CODE
import com.spark.android.util.KeyboardVisibilityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputCodeFragmentDialog : DialogFragment() {

    private var _binding: FragmentInputCodeDialogBinding? = null
    private val binding get() = _binding!!
    private val inputCodeFragmentDialogViewModel by viewModels<InputCodeFragmentDialogViewModel>()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private lateinit var getResultState: ActivityResultLauncher<Intent>

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

        setLayout()
        binding.inputCodeFragmentDialogViewModel = inputCodeFragmentDialogViewModel
        initRegisterForActivityResult()
        initButtonClickListener()
        initClearErrorMessage()
        initKeyBoardEvent()

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

    private fun initRegisterForActivityResult() {
        getResultState = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val state = result.data?.getBooleanExtra("finishState", GO_TO_WAITING_ROOM)
                if (state == GO_TO_WAITING_ROOM) {
                    dismiss()
                }
            }
        }
    }

    private fun initButtonClickListener() {
        binding.btnInputCodeCheck.setOnClickListener {
            binding.etInputCodeContent.clearFocus()

            inputCodeFragmentDialogViewModel.roomCode.value?.let { roomCode ->
                inputCodeFragmentDialogViewModel.getJoinCodeRoomInfo(
                    roomCode
                )
            }

            //GA부분
            FirebaseLogUtil.logClickEvent(CLICK_OK_INPUT_CODE)

            inputCodeFragmentDialogViewModel.roomInfo.observe(viewLifecycleOwner, EventObserver() {
                val intent = Intent(requireActivity(), JoinCodeActivity::class.java).apply {
                    putExtra(
                        "roomInfo",
                        requireNotNull(inputCodeFragmentDialogViewModel.roomInfo.value).peekContent()
                    )
                }
                getResultState.launch(intent)
            })
        }
    }

    private fun initClearErrorMessage() {
        binding.etInputCodeContent.setOnFocusChangeListener { _, focused ->
            if (focused) {
                inputCodeFragmentDialogViewModel.clearErrorMessage()
                binding.etInputCodeContent.text.clear()
                binding.viewInputCode.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.viewInputCode.context,
                        R.color.spark_pinkred
                    )
                )
            } else {
                if (binding.etInputCodeContent.text.isEmpty()) {
                    binding.viewInputCode.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.viewInputCode.context,
                            R.color.spark_gray
                        )
                    )
                }
            }
        }
    }

    private fun initKeyBoardEvent() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window,
            onHideKeyboard = {
//              requireActivity().currentFocus?.clearFocus() 이거 원래 다른 프래그먼트에서는 되는데 여기서는 왜 안되는지 모르겠다 찾으면 적용하자
                binding.etInputCodeContent.clearFocus()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        keyboardVisibilityUtils.detachKeyboardListeners()
        _binding = null
    }

}
