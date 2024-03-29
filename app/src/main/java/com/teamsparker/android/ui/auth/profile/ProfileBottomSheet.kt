package com.teamsparker.android.ui.auth.profile

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
import com.teamsparker.android.R
import com.teamsparker.android.databinding.BottomSheetProfileBinding
import com.teamsparker.android.ui.auth.profile.ProfileFragment.Companion.PROFILE_IMG
import android.net.Uri
import android.os.Build
import androidx.fragment.app.setFragmentResultListener
import com.teamsparker.android.ui.auth.profile.ProfileFragment.Companion.REQUEST_PROFILE_DELETE
import com.teamsparker.android.ui.auth.profile.ProfileFragment.Companion.REQUEST_PROFILE_IMG_FROM_ALBUM
import com.teamsparker.android.ui.auth.profile.ProfileFragment.Companion.REQUEST_PROFILE_IMG_FROM_CAMERA
import com.teamsparker.android.util.getImgUri
import com.teamsparker.android.util.getPathFromUri
import timber.log.Timber
import java.io.File
import java.lang.NullPointerException

class ProfileBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetProfileBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private var imgUri: Uri? = null

    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                setFragmentResult(REQUEST_PROFILE_IMG_FROM_ALBUM, bundleOf(PROFILE_IMG to it.data))
                dismiss()
            }
        }
    }

    private val fromCameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        imgUri?.let { uri ->
            Thread.sleep(700)
            if (File(getPathFromUri(requireContext(), uri)).exists()) {
                setFragmentResult(REQUEST_PROFILE_IMG_FROM_CAMERA, bundleOf(PROFILE_IMG to uri))
            }
        }
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savedInstanceState?.let {
            imgUri = it.getParcelable(IMG_URI)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(IMG_URI, imgUri)
    }

    private fun initFromAlbumBtnClickListener() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        binding.tvProfileBottomFromAlbum.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission,
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
                        arrayOf(permission),
                        REQUEST_READ_STORAGE_PERMISSION
                    )
                }
            }
        }
    }

    private fun initFromCameraBtnClickListener() {
        binding.tvProfileBottomFromCamera.setOnClickListener {
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
            Timber.tag("ProfileBottomSheet").e("ImgUri Null 에러")
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
        private const val IMG_URI = "imgUri"
        const val REQUEST_READ_STORAGE_PERMISSION = 1
        const val REQUEST_CAMERA_PERMISSION = 2
        const val REQUEST_CAMERA_PERMISSION_UNDER_Q = 3
        const val REQUEST_PROFILE_DIALOG = "requestProfileDialog"
        const val DELETE_MODE = "deleteMode"
    }
}
