package com.spark.android.ui.auth.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetProfileBinding
import com.spark.android.ui.auth.profile.ProfileFragment.Companion.PROFILE_IMG
import android.graphics.Bitmap
import androidx.fragment.app.setFragmentResultListener
import com.spark.android.ui.auth.profile.ProfileFragment.Companion.REQUEST_PROFILE_DELETE
import com.spark.android.ui.auth.profile.ProfileFragment.Companion.REQUEST_PROFILE_IMG_BITMAP
import com.spark.android.ui.auth.profile.ProfileFragment.Companion.REQUEST_PROFILE_IMG_URI

class ProfileBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetProfileBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                setFragmentResult(REQUEST_PROFILE_IMG_URI, bundleOf(PROFILE_IMG to it.data))
                dismiss()
            }
        }
    }

    private val fromCameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            it.extras?.let { extras ->
                val photo = extras.get("data") as Bitmap
                setFragmentResult(REQUEST_PROFILE_IMG_BITMAP, bundleOf(PROFILE_IMG to photo))
                dismiss()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(REQUEST_PROFILE_DIALOG) { requestKey, bundle ->
            binding.deleteMode = bundle.get(DELETE_MODE) as Boolean
        }
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        initFromAlbumBtnClickListener()
        initFromCameraBtnClickListener()
        initDeleteImgBtnClickListener()
    }

    private fun initFromAlbumBtnClickListener() {
        binding.tvProfileBottomFromAlbum.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    fromAlbumActivityLauncher.launch(
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                    )
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_STORAGE_PERMISSION
                    )
                }
            }
        }
    }

    private fun initFromCameraBtnClickListener() {
        binding.tvProfileBottomFromCamera.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) -> {
                    fromCameraActivityLauncher.launch(
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    )
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                }
            }
        }
    }

    private fun initDeleteImgBtnClickListener() {
        binding.tvProfileBottomDelete.setOnClickListener {
            setFragmentResult(REQUEST_PROFILE_DELETE, bundleOf(PROFILE_IMG to null))
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_STORAGE_PERMISSION = 1
        const val REQUEST_CAMERA_PERMISSION = 2
        const val REQUEST_PROFILE_DIALOG = "requestProfileDialog"
        const val DELETE_MODE = "deleteMode"
    }
}
