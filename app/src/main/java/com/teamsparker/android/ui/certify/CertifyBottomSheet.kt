package com.teamsparker.android.ui.certify

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
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
import com.teamsparker.android.R
import com.teamsparker.android.databinding.BottomSheetCertifyBinding
import com.teamsparker.android.ui.auth.profile.ProfileBottomSheet.Companion.REQUEST_CAMERA_PERMISSION
import com.teamsparker.android.ui.auth.profile.ProfileBottomSheet.Companion.REQUEST_READ_STORAGE_PERMISSION
import com.teamsparker.android.ui.certify.viewmodel.CertifyViewModel
import com.teamsparker.android.ui.auth.profile.ProfileBottomSheet.Companion.REQUEST_CAMERA_PERMISSION_UNDER_Q
import com.teamsparker.android.util.getImgUri
import com.teamsparker.android.util.getPathFromUri
import timber.log.Timber
import java.io.File
import java.lang.NullPointerException

class CertifyBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetCertifyBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private var imgUri: Uri? = null
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
    ) {
        imgUri?.let { uri ->
            if (File(getPathFromUri(requireContext(), uri)).exists()) {
                certifyViewModel.initImgUri(uri)
                showCertifyActivity()
            }
        }
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetCertifyBinding.inflate(inflater, container, false)
        savedInstanceState?.let {
            imgUri = it.getParcelable(IMG_URI)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(IMG_URI, imgUri)
    }

    private fun initArgumentsData() {
        arguments?.getInt("roomId")?.let { certifyViewModel.initRoomId(it) }
        arguments?.getString("roomName")?.let { certifyViewModel.initRoomName(it) }
        arguments?.getString("profileImgUrl")?.let { certifyViewModel.initProfileImg(it) }
        arguments?.getString("nickname")?.let { certifyViewModel.initNickName(it) }
        arguments?.getInt("certifyMode")?.let { certifyViewModel.initCertifyMode(it) }
        arguments?.getBoolean("onlyCameraInitial")?.let {
            certifyViewModel.initOnlyCameraInitial(it)
        }
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
                        REQUEST_READ_STORAGE_PERMISSION
                    )
                }
            }
        }
    }

    private fun initFromCameraBtnClickListener() {
        binding.tvCertifyCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (checkCameraPermission()) {
                    takePicture()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                }
            } else {
                if (checkCameraPermissionUnderQ()) {
                    takePicture()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        REQUEST_CAMERA_PERMISSION_UNDER_Q
                    )
                }
            }
        }
    }

    private fun checkCameraPermission() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED


    private fun checkCameraPermissionUnderQ() =
        checkCameraPermission() && ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun takePicture() {
        try {
            imgUri = getImgUri(requireContext().contentResolver)
                ?: throw NullPointerException()
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                it.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
                fromCameraActivityLauncher.launch(it)
            }
        } catch (e: NullPointerException) {
            Timber.tag("CertifyBottomSheet").d("ImgUri Null 에러")
        }
    }

    private fun showCertifyActivity() {
        if (certifyViewModel.certifyMode.value == CertifyMode.ONLY_CAMERA_MODE) {
            if (certifyViewModel.onlyCameraInitial.value == true) {
                val intent = Intent(context, CertifyActivity::class.java)
                intent.apply {
                    putExtra("timerRecord", certifyViewModel.timerRecord.value)
                    putExtra("nickname", certifyViewModel.nickName.value)
                    putExtra("roomName", certifyViewModel.roomName.value)
                    putExtra("roomId", certifyViewModel.roomId.value)
                    putExtra("certifyMode", CertifyMode.ONLY_CAMERA_MODE)
                    putExtra("onlyCameraInitial", false)
                    putExtra("profileImgUrl", certifyViewModel.profileImg.value)
                    putExtra("imgUri", certifyViewModel.imgUri.value)
                    arguments?.getInt("leftDay")?.let { putExtra("leftDay", it) }
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

    companion object {
        private const val IMG_URI = "imgUri"
    }
}
