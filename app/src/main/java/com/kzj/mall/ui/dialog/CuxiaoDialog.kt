package com.kzj.mall.ui.dialog

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogCuxiaoBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.utils.NameUtils

/**
 * 促销
 */
class CuxiaoDialog : BaseDialog<IPresenter, DialogCuxiaoBinding>() {

    private var goodsDetailEntity: GoodsDetailEntity? = null

    companion object {
        fun newInstance(goodsDetailEntity: GoodsDetailEntity?): CuxiaoDialog {
            val cuxiaoDialog = CuxiaoDialog()
            val b = Bundle()
            b?.putSerializable("goodsDetailEntity", goodsDetailEntity)
            cuxiaoDialog?.arguments = b
            return cuxiaoDialog
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId() = R.layout.dialog_cuxiao

    override fun initData() {
        goodsDetailEntity = arguments?.getSerializable("goodsDetailEntity") as GoodsDetailEntity

        //满赠、折扣、直降、满减
        val promotionmap = goodsDetailEntity?.promotionmap
        if (promotionmap != null) {
            val promotionType = promotionmap?.promotion_type
            when (promotionType) {
                1 -> {
                    mBinding?.tvManType?.text = "直降"
                    mBinding?.tvManName?.text = "活动期间购买此商品直降${promotionmap?.promotion_zjprice}元，预购从速！"
                }
                2 -> {
                    mBinding?.tvManType?.text = "折扣"
                    mBinding?.tvManName?.text = "活动期间购买此商品${promotionmap?.promotion_discount}折优惠！"
                }
                3 -> {
                    mBinding?.tvManType?.text = "满减"
                    mBinding?.tvManName?.text = NameUtils.manjianName(promotionmap?.promotion_mjprice!!)
                }
                4 -> {
                    mBinding?.tvManType?.text = "满赠"
                    mBinding?.tvManName?.text = "【送完即止】活动期间购买此商品满${promotionmap?.promotion_mzprice}元送${promotionmap?.mzgoodsnamestr}"
                }
            }

            if (promotionType == 4) {
                mBinding?.rvZengpin?.visibility = View.VISIBLE
                mBinding?.rvZengpin?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                val zengPinAdapter = ZengPinAdapter(promotionmap.mzlist!!)
                mBinding?.rvZengpin?.adapter = zengPinAdapter
            } else {
                mBinding?.rvZengpin?.visibility = View.GONE
            }
            mBinding?.tvEndTime1?.text = "${promotionmap?.promotion_endtime_str} 结束"
            mBinding?.llMan?.visibility = View.VISIBLE
        } else {
            mBinding?.llMan?.visibility = View.GONE
        }


        //全场满减
        val orderPromotion = goodsDetailEntity?.orderPromotion
        if (orderPromotion != null) {
            mBinding?.llManjian?.visibility = View.VISIBLE
            mBinding?.tvAllManName?.text = NameUtils.manjianName(orderPromotion?.promotion_mjprice!!)
            mBinding?.tvEndTime2?.text = "${orderPromotion?.promotion_endtime_str} 结束"
        } else {
            mBinding?.llManjian?.visibility = View.GONE
        }


        mBinding?.ivClose?.setOnClickListener {
            dismiss()
        }
    }

    class ZengPinAdapter(mzList: MutableList<GoodsDetailEntity.MZList>) : BaseAdapter<GoodsDetailEntity.MZList, BaseViewHolder>(R.layout.item_zengpin, mzList) {
        override fun convert(helper: BaseViewHolder?, item: GoodsDetailEntity.MZList?) {
            val llContainer = helper?.getView<LinearLayout>(R.id.ll_container)
            if (helper?.layoutPosition!! <= 0){
                llContainer?.setPadding(0,0,SizeUtils.dp2px(9f),0)
            }else{
                llContainer?.setPadding(SizeUtils.dp2px(9f),0,SizeUtils.dp2px(9f),0)
            }

            helper?.setText(R.id.tv_z_name, item?.goods_name)
            GlideApp.with(mContext)
                    .load(item?.goods_img)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_goods) as ImageView)
        }
    }
}