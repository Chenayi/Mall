package com.kzj.mall.adapter.provider.home

import android.content.Intent
import android.graphics.Paint
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.home.HomeFlashSaleEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
import com.takusemba.multisnaprecyclerview.OnSnapListener

/**
 * 每日闪购
 */
class HomeFlashSaleProvider : BaseItemProvider<HomeFlashSaleEntity, BaseViewHolder>() {
    var rv: MultiSnapRecyclerView? = null

    override fun layout(): Int {
        return R.layout.item_home_flash_sale_list
    }

    override fun viewType(): Int {
        return IHomeEntity.FLASH_SALE
    }

    override fun convert(helper: BaseViewHolder?, data: HomeFlashSaleEntity?, position: Int) {
        val dailyBuys = data?.dailyBuy
        dailyBuys?.let {
            rv = helper?.getView(R.id.rv_flash_sale)
            rv?.setFocusableInTouchMode(false);
            rv?.requestFocus();
            var flashSaleAdapter = FlashSaleAdapter(it)
            val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            rv?.setLayoutManager(layoutManager)
            rv?.setAdapter(flashSaleAdapter)
            rv?.scrollToPosition(data?.position)
            rv?.setOnSnapListener(object : OnSnapListener {
                override fun snapped(position: Int) {
                    data?.position = position
                }
            })
            flashSaleAdapter.setOnItemClickListener { adapter, view, position ->
                var intent = Intent(mContext, GoodsDetailActivity::class.java)
                intent?.putExtra(C.GOODS_INFO_ID, flashSaleAdapter?.getItem(position)?.goodsInfoId)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(intent)
            }
        }
    }

    inner class FlashSaleAdapter
    constructor(val flashSaleDatas: MutableList<HomeFlashSaleEntity.DailyBuy>)
        : BaseAdapter<HomeFlashSaleEntity.DailyBuy, BaseViewHolder>(R.layout.item_home_flash_sale, flashSaleDatas) {
        override fun convert(helper: BaseViewHolder?, item: HomeFlashSaleEntity.DailyBuy?) {
            var ivGoods = helper?.getView<LinearLayout>(R.id.ll_item)
            var params: RelativeLayout.LayoutParams = ivGoods?.layoutParams as RelativeLayout.LayoutParams

            params.leftMargin = SizeUtils.dp2px(12f)
            if (helper?.layoutPosition == data?.size - 1) {
                params.rightMargin = SizeUtils.dp2px(12f)
            } else {
                params.rightMargin = 0
            }
            ivGoods.layoutParams = params

            helper?.setText(R.id.tv_goods_name, item?.goodsName)
                    ?.setText(R.id.tv_price, item?.goodsPrice)
                    ?.setText(R.id.tv_market_price, "¥" + item?.marketPrice)

            helper?.getView<TextView>(R.id.tv_market_price)?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG

            GlideApp.with(mContext)
                    .load(item?.imgUrl)
                    .centerCrop()
                    .placeholder(R.color.gray_default)
                    .into(helper?.getView(R.id.iv_goods) as ImageView)
        }

    }
}