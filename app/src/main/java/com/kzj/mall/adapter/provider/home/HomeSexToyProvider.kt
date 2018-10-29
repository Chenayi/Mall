package com.kzj.mall.adapter.provider.home

import android.content.Intent
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.entity.SexToyEntity
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

/**
 * 情趣用品
 */
class HomeSexToyProvider : BaseItemProvider<SexToyEntity, BaseViewHolder>() {
    var isInitialized = false
    var rv: MultiSnapRecyclerView? = null

    override fun layout(): Int {
        return R.layout.item_home_sex_toy_list
    }

    override fun viewType(): Int {
        return IHomeEntity.SEX_TOY
    }

    override fun convert(helper: BaseViewHolder?, data: SexToyEntity?, position: Int) {
        if (isInitialized == false) {
            rv = helper?.getView(R.id.rv_sex_toy_sale)
            rv?.setFocusableInTouchMode(false);
            rv?.requestFocus();
            val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            rv?.setLayoutManager(layoutManager)
            data?.qingqu?.let {
                val myAdapter = MyAdapter(it)
                myAdapter?.setOnItemClickListener { adapter, view, position ->
                    val intent = Intent(mContext, GoodsDetailActivity::class.java)
                    intent?.putExtra(C.GOODS_INFO_ID, myAdapter?.getItem(position)?.goodsInfoId)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext?.startActivity(intent)
                }
                rv?.setAdapter(myAdapter)
            }
            isInitialized = true
        }
    }


    inner class MyAdapter
    constructor(val sexToyDatas: MutableList<SexToyEntity.SexToys>)
        : BaseAdapter<SexToyEntity.SexToys, BaseViewHolder>(R.layout.item_home_sex_toy, sexToyDatas) {
        override fun convert(helper: BaseViewHolder?, item: SexToyEntity.SexToys?) {
            val linearLayout = helper?.getView<LinearLayout>(R.id.ll_item)
            var params: RelativeLayout.LayoutParams = linearLayout?.layoutParams as RelativeLayout.LayoutParams

            params.leftMargin = SizeUtils.dp2px(8f)
            if (helper?.layoutPosition == data?.size - 1) {
                params.rightMargin = SizeUtils.dp2px(8f)
            } else {
                params.rightMargin = 0
            }
            linearLayout.layoutParams = params

            helper?.setText(R.id.tv_goods_name, item?.goodsName)
                    ?.setText(R.id.tv_goods_price, "¥" + item?.goodsPrice)
                    ?.setText(R.id.tv_goods_market_price, "¥" + item?.marketPrice)
            helper?.getView<TextView>(R.id.tv_goods_market_price)?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            GlideApp.with(mContext)
                    .load(item?.imgUrl)
                    .centerCrop()
                    .placeholder(R.color.gray_default)
                    .into(helper?.getView(R.id.iv_goods) as ImageView)
        }
    }
}