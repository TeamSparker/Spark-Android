package com.spark.android.ui.certify

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetCertifyBinding
import com.spark.android.ui.auth.profile.ProfileBottomSheet.Companion.REQUEST_CAMERA_PERMISSION
import com.spark.android.ui.auth.profile.ProfileBottomSheet.Companion.REQUEST_STORAGE_PERMISSION
import com.spark.android.ui.certify.viewmodel.CertifyViewModel

class CertifyBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetCertifyBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val certifyViewModel by activityViewModels<CertifyViewModel>()

    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let { intent ->
            intent.data?.let { uri ->
                certifyViewModel.initImgUri(uri)
                showCertifyActivity()
                dismiss()
            }
        }
    }

    private val fromCameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let { intent ->
            intent.extras?.let { extras ->
                val photo = extras.get("data") as Bitmap
                certifyViewModel.initImgBitmap(photo)
                showCertifyActivity()
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetCertifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initArgumentsData()
        initFromAlbumBtnClickListener()
        initFromCameraBtnClickListener()
    }

    private fun initArgumentsData() {
        arguments?.getInt("roomId")?.let { certifyViewModel.initRoomId(it) }
        arguments?.getString("roomName")?.let { certifyViewModel.initRoomName(it) }
        arguments?.getString("profileImg")?.let { certifyViewModel.initProfileImg(it) }
        arguments?.getString("nickName")?.let { certifyViewModel.initNickName(it) }
        arguments?.getInt("certifyMode")?.let { certifyViewModel.initCertifyMode(it) }
        arguments?.getBoolean("onlyCameraInitial")
            ?.let { certifyViewModel.initOnlyCameraInitial(it) }
    }

    private fun initFromAlbumBtnClickListener() {
        binding.tvCertifyAlbum.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                -> {
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
        binding.tvCertifyCamera.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ),
                -> {
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

    private fun showCertifyActivity() {
        if (certifyViewModel.certifyMode.value == CertifyMode.ONLY_CAMERA_MODE) {
            if (certifyViewModel.onlyCameraInitial.value == true) {
                val intent = Intent(context, CertifyActivity::class.java)
                intent.apply {
                    putExtra("timerRecord", certifyViewModel.timerRecord.value)
                    putExtra("roomName", certifyViewModel.roomName.value)
                    putExtra("roomId", certifyViewModel.roomId.value)
                    putExtra("certifyMode", CertifyMode.ONLY_CAMERA_MODE)
                    putExtra("onlyCameraInitial", false)
                    putExtra("imgUri", certifyViewModel.imgUri.value)
                    putExtra("imgBitmap", certifyViewModel.imgBitmap.value)
                }
                startActivity(intent)
            }
        } else {
            certifyViewModel.initCertifyMode(CertifyMode.NORMAL_MODE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}