package com.the.war.of.thewarofstars.util

/**
 * Created by sangyoon on 2021/07/24
 */

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils


class CirclePagerIndicatorDecoration : RecyclerView.ItemDecoration() {
    private val colorActive = 0xFFFFFFFF
    private val colorInactive = 0xFF8F8F8F

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private val mIndicatorHeight = (DP * 20).toInt()

    /**
     * Indicator stroke width.
     */
    private val mIndicatorStrokeWidth = DP * 4

    /**
     * Indicator width.
     */
    private val mIndicatorItemLength = DP * 4

    /**
     * Padding between indicators.
     */
    private val mIndicatorItemPadding = DP * 12

    /**
     * Some more natural animation interpolation
     */
    private val mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
    private val mPaint: Paint = Paint()

    val TAG = "CirclePagerIndicatorDecorationLog"
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = parent.adapter!!.itemCount


        // center horizontally, calculate width and subtract half from center
        val totalLength = mIndicatorItemLength * itemCount
        val paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        // center vertically in the allotted space
        val indicatorPosY = parent.height - mIndicatorHeight / 2f
//        Log.i(TAG, "Density : $DENSITY, WithPixels : $WITHPIXELS, Dp : $DP, DPI : $DPI, indicatorPosY : $indicatorPosY")
        Log.i(TAG, "recyclerViewHeight : ${parent.height}, indicatorPosY : $indicatorPosY, dpToPx : ${DensityUtils.dpToPx(12)}, mIndicatorHeight : $mIndicatorHeight")
        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        val activePosition = layoutManager!!.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild: View? = layoutManager.findViewByPosition(activePosition)
        var left: Int
        var width: Int
        var right: Int
        var progress: Float

        activeChild?.let {
            left = activeChild.left
            width= activeChild.width
            right = activeChild.right

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())
            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress)
        }

    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        mPaint.color = colorInactive.toInt()

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float
    ) {
        mPaint.color = colorActive.toInt()

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
        } else {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress
            c.drawCircle(
                highlightStart + partialLength,
                indicatorPosY,
                mIndicatorItemLength / 2f,
                mPaint
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight

    }

    companion object {
//        private val DENSITY: Float = Resources.getSystem().displayMetrics.density
//        private val WITHPIXELS: Int = Resources.getSystem().displayMetrics.widthPixels
//        private val DPI: Int = Resources.getSystem().displayMetrics.densityDpi
//        private val DP = WITHPIXELS / DENSITY

        private val DP = Resources.getSystem().displayMetrics.density
    }

    init {
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }
}