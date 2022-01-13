package com.spark.android.ui.feed.adapter

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.response.FeedListItem
import com.spark.android.databinding.ItemFeedHeaderBinding

class FeedHeaderDecoration(private val stickyHeaderResolver: StickyHeaderResolver) :
    RecyclerView.ItemDecoration() {
    private var currentHeaderPair: Pair<View, FeedListItem>? = null

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) return

        val header = getHeaderViewForPos(topChildPosition, parent)
        fixLayoutSize(parent, header)

        val childInContact = getChildInContact(parent, header.bottom) ?: return

        val childAdapterPosition = parent.getChildAdapterPosition(childInContact)
        if (childAdapterPosition == -1) return

        if (this.stickyHeaderResolver.isHeader(childAdapterPosition)) {
            moveHeader(c, header, childInContact)
            return
        }

        drawHeader(c, header)
    }

    private fun getHeaderViewForPos(itemPosition: Int, parent: RecyclerView): View {
        val item = stickyHeaderResolver.getItemHeaderForPos(itemPosition)

        // Return same view instance if necessary,
        // avoiding multiple bindings since #onDrawOver is called multiple times
        currentHeaderPair?.let {
            if (item == it.second) return it.first
        }

        val binding =
            ItemFeedHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // Binding item
        with(stickyHeaderResolver) { binding.bindHeader(item) }
        binding.executePendingBindings()

        val view = binding.root
        currentHeaderPair = view to item

        return view
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        // Specs for parent (RecyclerView)
        val parentWidth = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val parentHeight = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)

        // Specs for children (headers)
        val headerWidth = ViewGroup.getChildMeasureSpec(
            parentWidth,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val headerHeight = ViewGroup.getChildMeasureSpec(
            parentHeight,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(headerWidth, headerHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}
