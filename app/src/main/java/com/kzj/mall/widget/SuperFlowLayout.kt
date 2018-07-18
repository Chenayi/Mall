package com.kzj.mall.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.SuperFlowLayoutBinding

class SuperFlowLayout : BaseRelativeLayout<SuperFlowLayoutBinding>, View.OnClickListener {
    private var datas: MutableList<String>? = null
    private var textViews: MutableMap<Int, TextView>? = null
    private var curPosition = 0
    private var onTagClickListener: OnTagClickListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId(): Int {
        return R.layout.super_flow_layout
    }

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        textViews = HashMap()
    }

    fun setDatas(datas: MutableList<String>) {
        mBinding?.flow?.removeAllViews()
        datas?.let {
            for (i in 0 until it.size) {
                val tv = TextView(context)
                tv?.setPadding(SizeUtils.dp2px(12f), SizeUtils.dp2px(8f), SizeUtils.dp2px(12f), SizeUtils.dp2px(8f))
                if (i == 0) {
                    tv?.setBackgroundResource(R.drawable.background_green_stroke_corners_9999)
                    tv?.setTextColor(Color.parseColor("#48B828"))
                } else {
                    tv?.setBackgroundResource(R.drawable.background_f0_corners_9999)
                    tv?.setTextColor(Color.parseColor("#2E3033"))
                }
                tv?.setText(it.get(i))
                tv?.setTag(i)
                tv?.setOnClickListener(this)
                textViews?.put(i, tv)
                mBinding?.flow?.addView(tv)
            }
        }

        this.datas = datas
    }

    override fun onClick(v: View?) {
        if (v is TextView) {
            var tag = v?.tag as Int

            if (tag != curPosition) {
                switchTag(tag)
                curPosition = tag
            }
        }
    }

    /**
     * 选择
     */
    fun switchTag(position: Int) {
        for (i in 0 until textViews?.size!!) {
            val tv = textViews?.get(i)
            tv?.setPadding(SizeUtils.dp2px(12f), SizeUtils.dp2px(8f), SizeUtils.dp2px(12f), SizeUtils.dp2px(8f))
            if (position == i) {
                tv?.setBackgroundResource(R.drawable.background_green_stroke_corners_9999)
                tv?.setTextColor(Color.parseColor("#48B828"))
            } else {
                tv?.setBackgroundResource(R.drawable.background_f0_corners_9999)
                tv?.setTextColor(Color.parseColor("#2E3033"))
            }
        }

        onTagClickListener?.onTagClick(position, datas?.get(position))
    }

    /**
     * 重置
     */
    fun reset(){
        for (i in 0 until textViews?.size!!) {
            val tv = textViews?.get(i)
            tv?.setPadding(SizeUtils.dp2px(12f), SizeUtils.dp2px(8f), SizeUtils.dp2px(12f), SizeUtils.dp2px(8f))
            if (i==0) {
                tv?.setBackgroundResource(R.drawable.background_green_stroke_corners_9999)
                tv?.setTextColor(Color.parseColor("#48B828"))
            } else {
                tv?.setBackgroundResource(R.drawable.background_f0_corners_9999)
                tv?.setTextColor(Color.parseColor("#2E3033"))
            }
        }
        curPosition = 0
    }


    fun setOnTagClickListener(onTagClickListener: OnTagClickListener) {
        this.onTagClickListener = onTagClickListener
    }


    interface OnTagClickListener {
        fun onTagClick(position: Int, tag: String?)
    }
}