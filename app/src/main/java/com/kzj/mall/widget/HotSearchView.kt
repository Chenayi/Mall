package com.kzj.mall.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.HotSearchViewBinding

class HotSearchView : BaseRelativeLayout<HotSearchViewBinding>, View.OnClickListener {
    private var datas: MutableList<String>? = null
    private var onTagClickListener: OnTagClickListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId() = R.layout.hot_search_view
    override fun init(attrs: AttributeSet, defStyleAttr: Int) {

    }


    fun setDatas(datas: MutableList<String>) {
        mBinding?.flow?.removeAllViews()
        datas?.let {
            for (i in 0 until it.size) {
                val tv = TextView(context)
                tv?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                tv?.setPadding(SizeUtils.dp2px(12f), SizeUtils.dp2px(8f), SizeUtils.dp2px(12f), SizeUtils.dp2px(8f))
                if (i == 0 || i == 1 || i == 2) {
                    tv?.setBackgroundResource(R.drawable.background_green_corners_9999)
                    tv?.setTextColor(Color.WHITE)
                } else {
                    tv?.setBackgroundResource(R.drawable.background_f0_corners_9999)
                    tv?.setTextColor(Color.parseColor("#2E3033"))
                }
                tv?.setText(it.get(i))
                tv?.setTag(i)
                tv?.setOnClickListener(this)
                mBinding?.flow?.addView(tv)
            }
        }

        this.datas = datas
    }

    override fun onClick(v: View?) {
        if (v is TextView) {
            val i = v?.tag as Int
            onTagClickListener?.onTagClick(i, datas?.get(i))
        }
    }

    fun setOnTagClickListener(onTagClickListener: OnTagClickListener) {
        this.onTagClickListener = onTagClickListener
    }


    interface OnTagClickListener {
        fun onTagClick(position: Int, tag: String?)
    }
}