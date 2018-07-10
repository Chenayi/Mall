package com.kzj.mall.adapter.provider.cart

import android.graphics.Color
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartRecommendEntity
import com.kzj.mall.entity.cart.ICart

class CartRecommendsProvider : BaseItemProvider<CartRecommendEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item__recommend_goods
    }

    override fun viewType(): Int {
        return ICart.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: CartRecommendEntity?, position: Int) {
        data?.isShowRecommendText?.let {
            helper?.setGone(R.id.ll_recommend, it)
        }

        val bg = helper?.getView<LinearLayout>(R.id.ll_bg)
        if (data?.isBackgroundCorners == true){
            bg?.setBackgroundResource(R.drawable.background_white_corners_8)
        }else{
            bg?.setBackgroundColor(Color.WHITE)
        }
    }

}