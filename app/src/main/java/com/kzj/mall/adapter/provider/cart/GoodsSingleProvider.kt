package com.kzj.mall.adapter.provider.cart

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.CartEntity
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.cart.GoodsSingleEntity
import com.kzj.mall.entity.cart.ICart
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.utils.PriceUtils

class GoodsSingleProvider : BaseItemProvider<CartSingleEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_goods_list_single
    }

    override fun viewType(): Int {
        return ICart.SINGLE
    }

    override fun convert(helper: BaseViewHolder?, data: CartSingleEntity?, position: Int) {

        val allPrice = data?.goods_price?.toFloat()!!
        val num = data?.goods_num!!
        val singlePrice = allPrice / num

        helper?.setGone(R.id.ll_top, data?.shopping_cart_type.equals("1"))
                ?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_num, "x" + data?.goods_num)
        data?.shopping_cart_type?.let {
            //疗程
            if (it.equals("1")) {
                helper?.getView<TextView>(R.id.tv_goods_price)?.textSize = 12f
                helper?.setText(R.id.tv_combination_name, data?.combination_name)
                        ?.setText(R.id.tv_goods_price, "¥" + FloatUtils.format(singlePrice))
                        ?.setText(R.id.tv_all_price, PriceUtils.split11sp("¥" + FloatUtils.format(data?.goods_price?.toFloat()!!)))
                        ?.setTextColor(R.id.tv_goods_price, ContextCompat.getColor(mContext, R.color.gray_6A6E75))

                helper?.getView<TextView>(R.id.tv_goods_price)?.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            }
            //单品
            else {
                helper?.getView<TextView>(R.id.tv_goods_price)?.textSize = 13f
                helper?.setText(R.id.tv_goods_price, PriceUtils.split11sp("¥" + FloatUtils.format(singlePrice)))
                        ?.setTextColor(R.id.tv_goods_price, ContextCompat.getColor(mContext, R.color.colorPrimary))
                helper?.getView<TextView>(R.id.tv_goods_price)?.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            }
        }

        val rvZP = helper?.getView<RecyclerView>(R.id.rv_zp)
        rvZP?.setFocusableInTouchMode(false);
        rvZP?.requestFocus();
        val promotionMap = data?.promotionMap
        if (promotionMap != null && promotionMap?.promotion_type == 4) {
            val mzList = promotionMap?.mzList
            if (mzList != null && mzList?.size > 0){
                rvZP?.visibility = View.VISIBLE
                rvZP?.layoutManager = LinearLayoutManager(mContext)
                val zpAdapter = ZPAdapter(promotionMap?.mzList!!)
                rvZP?.adapter = zpAdapter
            }else{
                rvZP?.visibility = View.GONE
            }
        } else {
            rvZP?.visibility = View.GONE
        }

        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }

    class ZPAdapter(mzLists: MutableList<CartEntity.MZList>) : BaseAdapter<CartEntity.MZList, BaseViewHolder>(R.layout.item_zp, mzLists) {
        override fun convert(helper: BaseViewHolder?, item: CartEntity.MZList?) {

            val layoutPosition = helper?.layoutPosition!!
            val llCotainer = helper?.getView<LinearLayout>(R.id.ll_container)
            if (layoutPosition <= 0) {
                llCotainer?.setPadding(0, 0, 0, 0)
            } else {
                llCotainer?.setPadding(0, SizeUtils.dp2px(6f), 0, 0)
            }

            helper?.setText(R.id.tv_goods_name, item?.goods_name)
                    ?.setText(R.id.tv_goods_num, "x${item?.goodsNum}")
                    ?.setGone(R.id.iv_down, false)
                    ?.setGone(R.id.iv_goods, false)
                    ?.addOnClickListener(R.id.iv_down)
//            GlideApp.with(mContext)
//                    .load(item?.goods_img)
//                    .centerCrop()
//                    .placeholder(R.color.gray_f0)
//                    .into(helper?.getView(R.id.iv_goods) as ImageView)
        }
    }

}