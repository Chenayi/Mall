package com.kzj.mall.ui.dialog

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import com.kzj.mall.R
import razerdp.basepopup.BasePopupWindow

class DetailMorePop constructor(val mContext: Context) : BasePopupWindow(mContext), View.OnClickListener {

    companion object {
        val MSG = 0
        val HOME = 1
        val CART = 2
        val PERSON = 3
    }

    private var onItemClickLinstener: OnItemClickLinstener? = null

    init {
        findViewById(R.id.ll_msg)?.setOnClickListener(this)
        findViewById(R.id.ll_home)?.setOnClickListener(this)
        findViewById(R.id.ll_cart)?.setOnClickListener(this)
        findViewById(R.id.ll_person)?.setOnClickListener(this)
    }

    override fun getClickToDismissView(): View {
        return popupWindowView
    }

    override fun initShowAnimation(): Animation {
        val set = AnimationSet(false)
        val shakeAnimaw = TranslateAnimation(0f, 0f, -300f, 0f)
        shakeAnimaw.duration = 200
//        set.addAnimation(defaultAlphaAnimation)
        set.addAnimation(shakeAnimaw)
        return set
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.pop_detail_more)
    }

    override fun initAnimaView(): View {
        return findViewById(R.id.ll_content) as LinearLayout
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_msg -> {
                onItemClickLinstener?.onItemClick(MSG)
                dismiss()
            }
            R.id.ll_home -> {
                onItemClickLinstener?.onItemClick(HOME)
                dismiss()
            }
            R.id.ll_cart -> {
                onItemClickLinstener?.onItemClick(CART)
                dismiss()
            }
            R.id.ll_person -> {
                onItemClickLinstener?.onItemClick(PERSON)
                dismiss()
            }
        }
    }

    fun setOnItemClickLinstener(onItemClickLinstener: OnItemClickLinstener) {
        this.onItemClickLinstener = onItemClickLinstener
    }

    interface OnItemClickLinstener {
        fun onItemClick(p: Int)
    }
}