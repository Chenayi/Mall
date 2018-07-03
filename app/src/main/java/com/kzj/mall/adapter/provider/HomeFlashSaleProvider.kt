package com.kzj.mall.adapter.provider

import android.graphics.Paint
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.home.HomeFlashSaleEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

/**
 * 每日闪购
 */
class HomeFlashSaleProvider : BaseItemProvider<HomeFlashSaleEntity, BaseViewHolder>() {

    var isInitialized = false
    var rv: MultiSnapRecyclerView? = null

    override fun layout(): Int {
        return R.layout.item_home_flash_sale_list
    }

    override fun viewType(): Int {
        return IHomeEntity.FLASH_SALE
    }

    override fun convert(helper: BaseViewHolder?, data: HomeFlashSaleEntity?, position: Int) {
        if (isInitialized == false) {
            val flashSaleListData = data?.flashSaleListData
            flashSaleListData?.let {
                rv = helper?.getView(R.id.rv_flash_sale)
                var adapter = FlashSaleAdapter(it)
                val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                rv?.setLayoutManager(layoutManager)
                rv?.setAdapter(adapter)
            }
            isInitialized = true
        }
    }

    inner class FlashSaleAdapter
    constructor(val flashSaleDatas: MutableList<HomeFlashSaleEntity.FlashSaleListData>)
        : BaseAdapter<HomeFlashSaleEntity.FlashSaleListData, BaseViewHolder>(R.layout.item_home_flash_sale, flashSaleDatas) {
        override fun convert(helper: BaseViewHolder?, item: HomeFlashSaleEntity.FlashSaleListData?) {
            var ivGoods = helper?.getView<LinearLayout>(R.id.ll_item)
            var params: RelativeLayout.LayoutParams = ivGoods?.layoutParams as RelativeLayout.LayoutParams

            params.leftMargin = SizeUtils.dp2px(12f)
            if (helper?.layoutPosition == datas?.size - 1) {
                params.rightMargin = SizeUtils.dp2px(12f)
            } else {
                params.rightMargin = 0
            }
            ivGoods.layoutParams = params

            helper?.getView<TextView>(R.id.tv_old_price)?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
        }

    }
}