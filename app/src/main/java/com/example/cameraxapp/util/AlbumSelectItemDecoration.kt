package com.example.cameraxapp.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxapp.util.Constants.Companion.buttonViewList

class AlbumSelectItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

//    var buttonViewList = ArrayList<Boolean>()

    init {
        for (i in 0 until 16) {
            buttonViewList.add(false)
        }
    }

    fun drawView(c: Canvas, v: View) {
        c.save()
        v.draw(c)
    }

    fun dpToPx(context: Context, dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (buttonViewList[position]) {
            outRect.top = dpToPx(context, 50f).toInt() * -1
//            outRect.bottom = dpToPx(context, 50f).toInt() * - 1
        } else {
            outRect.top = dpToPx(context, 10f).toInt()
            outRect.bottom = dpToPx(context, 10f).toInt()
        }

    }


//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//
//        super.onDrawOver(c, parent, state)
//        val itemCount = parent.childCount
//        for (i in 0 until itemCount) {
//            val child = parent.getChildAt(i)
//
//            val params = child.layoutParams as RecyclerView.LayoutParams
//
//            val cTop = child.top
//            val cRight = child.right
//            val cBottom = child.bottom
//            val cLeft = child.left
//
//
//            val textView = TextView(parent.context)
//            textView.text = "kejfaiwejfjdkafjiwjeifji"
//            textView.setBackgroundColor(Color.RED)
//            textView.layout(cLeft, cTop, cRight, cBottom)
//
//            c.translate(cLeft.toFloat(), cTop.toFloat())
//
//            textView.draw(c)
////            drawView(c, textView)
//        }
//    }
}