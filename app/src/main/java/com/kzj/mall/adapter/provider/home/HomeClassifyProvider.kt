package com.kzj.mall.adapter.provider.home

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeClassifyEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 *  分类
 */
class HomeClassifyProvider(val context: Context) : BaseItemProvider<HomeClassifyEntity, BaseViewHolder>() {
    private var animView: ImageView? = null

    private var animPlaying = false

    override fun layout(): Int {
        return R.layout.item_home_classify
    }

    override fun viewType(): Int {
        return IHomeEntity.CLASSIFY
    }

    override fun convert(helper: BaseViewHolder?, data: HomeClassifyEntity?, position: Int) {
        animView = helper?.getView(R.id.iv_scale_anim)

        helper?.addOnClickListener(R.id.iv_2369)
                ?.addOnClickListener(R.id.ll_classify)
                ?.addOnClickListener(R.id.ll_ask)
                ?.addOnClickListener(R.id.ll_ziyin)
                ?.addOnClickListener(R.id.ll_qingqu)

        val screenWidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(22f)
        val h = 251f * screenWidth / 1016f
        val imageView = helper?.getView<ImageView>(R.id.iv_2369)
        val layoutParams = imageView?.layoutParams as RelativeLayout.LayoutParams
        layoutParams.width = screenWidth
        layoutParams.height = h?.toInt()
        imageView?.requestLayout()
    }

    fun startAnim() {
        if (!animPlaying) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.scale_qianggou_anim)
            animView?.startAnimation(animation)
            animPlaying = true
        }
    }

    fun stopAnim() {
        animView?.clearAnimation()
        animPlaying = false
    }
}