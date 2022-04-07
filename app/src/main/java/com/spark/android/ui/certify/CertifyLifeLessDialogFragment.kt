package com.spark.android.ui.certify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spark.android.R
import com.spark.android.databinding.DialogCertifyLifeLessBinding


class CertifyLifeLessDialogFragment : Fragment() {

    private var _binding: DialogCertifyLifeLessBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCertifyLifeLessBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}