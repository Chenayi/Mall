package com.kzj.mall.adapter.provider

import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.HomeEntity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.IHomeEntity


class FlashSaleProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_flash_sale_list
    }

    override fun viewType(): Int {
        return IHomeEntity.FLASH_SALE
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        var rv = helper?.getView<RecyclerView>(R.id.rv_flash_sale)
        var adapter = FlashSaleAdapter(flashSaleData())
        val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
        rv?.setLayoutManager(layoutManager)
        rv?.setAdapter(adapter)
    }

    fun flashSaleData(): MutableList<HomeEntity.FlashSale> {
        var datas = ArrayList<HomeEntity.FlashSale>()
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        datas.add(HomeEntity().FlashSale())
        return datas
    }

    inner class FlashSaleAdapter
    constructor(val flashSaleDatas: MutableList<HomeEntity.FlashSale>)
        : BaseAdapter<HomeEntity.FlashSale, BaseViewHolder>(R.layout.item_home_flash_sale, flashSaleDatas) {
        override fun convert(helper: BaseViewHolder?, item: HomeEntity.FlashSale?) {
            var ivGoods = helper?.getView<ImageView>(R.id.iv_goods)
            var params : RelativeLayout.LayoutParams = ivGoods?.layoutParams as RelativeLayout.LayoutParams
            if (helper?.layoutPosition == datas?.size - 1){
                params.rightMargin = 0
            }else{
                params.rightMargin = SizeUtils.dp2px(12f)
            }
            ivGoods.layoutParams = params
        }

    }
}