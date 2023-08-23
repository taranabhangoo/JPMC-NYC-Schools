package com.jpmc.nycschools.ui.schoolslist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.jpmc.nycschools.R
import com.jpmc.nycschools.common.dpToPx


class VerticalItemDecoration(
    private val context: Context,
    private val spacing: Int = 8,
    private val lineHeight: Int = 1,
    private val lineColor: Int = ContextCompat.getColor(context, R.color.line_color)
) : ItemDecoration() {

    private val linePaint = Paint().apply {
        color = lineColor
        strokeWidth = lineHeight.toFloat()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val top = child.bottom + spacing.dpToPx(context)
            val bottom = top + lineHeight.dpToPx(context)
            canvas.drawLine(
                left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(),
                linePaint!!
            )
        }
    }
}