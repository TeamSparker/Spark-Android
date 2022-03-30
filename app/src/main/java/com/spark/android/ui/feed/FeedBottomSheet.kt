package com.spark.android.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetFeedBinding
import com.spark.android.ui.feedreport.FeedReportActivity

class FeedBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFeedBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private var feedItemId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(FEED) { requestKey, bundle ->
            feedItemId = bundle.get(FEED_ITEM_ID) as Int
            feedItemId
        }
        initReportBtnClickListener()
    }

    private fun initReportBtnClickListener() {
        binding.tvFeedBottomReport.setOnClickListener {
            startActivity(Intent(requireContext(), FeedReportActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                putExtra(FEED_ITEM_ID, feedItemId)
            })
            dismiss()
        }
    }

    companion object {
        const val FEED = "feed"
        const val FEED_ITEM_ID = "feedItemId"
    }
}
