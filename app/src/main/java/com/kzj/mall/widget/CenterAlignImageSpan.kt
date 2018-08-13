package com.kzj.mall.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import android.support.constraint.solver.widgets.WidgetContainer.getBounds
import android.R.attr.y



class CenterAlignImageSpan : ImageSpan {

    constructor(d: Drawable?) : super(d)
    constructor(context: Context?, b: Bitmap?) : super(context, b)

    override fun draw(canvas: Canvas?, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint?) {
        val fm = paint?.getFontMetricsInt()
        val transY = (y + fm?.descent!! + y + fm.ascent) / 2f - drawable.getBounds().bottom / 2f
        canvas?.save()
        canvas?.translate(x, transY)
        drawable.draw(canvas);
        canvas?.restore()
    }
}