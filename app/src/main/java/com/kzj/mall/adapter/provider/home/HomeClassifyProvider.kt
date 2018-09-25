package com.kzj.mall.adapter.provider.home

import android.widget.ImageView
import android.widget.LinearLayout
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
class HomeClassifyProvider : BaseItemProvider<HomeClassifyEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_classify
    }

    override fun viewType(): Int {
        return IHomeEntity.CLASSIFY
    }

    override fun convert(helper: BaseViewHolder?, data: HomeClassifyEntity?, position: Int) {
        helper?.addOnClickListener(R.id.iv_2369)

        val screenWidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(20f)
        val h = 270f * screenWidth / 1020f
        val imageView = helper?.getView<ImageView>(R.id.iv_2369)
        val layoutParams = imageView?.layoutParams as LinearLayout.LayoutParams
        layoutParams.width = screenWidth
        layoutParams.height = h?.toInt()
        imageView?.requestLayout()


        GlideApp.with(mContext)
                .asGif()
                .load(R.mipmap.function)
                .into(imageView)
    }
}