package com.teamsparker.android.ui.makeroom.namesetting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentNameSettingBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.makeroom.namesetting.viewmodel.NameSettingViewModel
import com.teamsparker.android.util.EditTextUtil
import com.teamsparker.android.util.KeyBoardUtil

class NameSettingFragment : BaseFragment<FragmentNameSettingBinding>(R.layout.fragment_name_setting) {

    private val nameSettingViewModel by viewModels<NameSettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameSettingViewModel = nameSettingViewModel
        initEditTextClearFocus()
        initNextButton()
        initBackButton()
        EditTextUtil.focusedEditText(binding.etMakeRoomNameSettingHabitName,binding.viewNameSetting)
    }

    private fun initEditTextClearFocus() {
        binding.layoutNameSetting.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun initNextButton(){
        binding.btnMakeRoomNameSettingNext.setOnClickListener {
            val action = NameSettingFragmentDirections.actionNameSettingFragmentToSelectConfirmMethodFragment(nameSettingViewModel.habitName.value)
            findNavController().navigate(action)
        }
    }

    private fun initBackButton(){
        binding.btnMakeRoomNameSettingBack.setOnClickListener {
            requireActivity().finish()
        }
    }
}
