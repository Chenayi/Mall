package com.kzj.mall.ui.dialog

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.widget.RelativeLayout
import com.kzj.mall.R
import razerdp.basepopup.BasePopupWindow
import android.view.animation.CycleInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.AnimationSet



class HomeTabClassifyPop constructor(val context : Activity) : BasePopupWindow(context) {
    override fun getClickToDismissView(): View {
        return popupWindowView
    }

    override fun initShowAnimation(): Animation {
        val set = AnimationSet(false)
        val shakeAnima = RotateAnimation(0f, 15f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        shakeAnima.interpolator = CycleInterpolator(5f)
        shakeAnima.duration = 400
        set.addAnimation(defaultAlphaAnimation)
        set.addAnimation(shakeAnima)
        return set
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.pop_home_tab_classify)
    }

    override fun initAnimaView(): View {
        return findViewById(R.id.tl_top) as RelativeLayout
    }

}