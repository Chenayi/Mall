package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.IHomeEntity
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer



class AdvBannerProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_adv_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.ADV_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        val myAdapter = MyAdapter(advDatas())
        val scrollView = helper?.getView<DiscreteScrollView>(R.id.picker)
        scrollView?.adapter = myAdapter
        scrollView?.setItemTransformer(ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build())
    }

    private fun advDatas(): MutableList<HomeEntity.AdvBanner> {
        var datas = ArrayList<HomeEntity.AdvBanner>()
        for (i in 0..6) {
            datas.add(HomeEntity().AdvBanner())
        }
        return datas
    }


    inner class MyAdapter
    constructor(val advDatas: MutableList<HomeEntity.AdvBanner>)
        : BaseAdapter<HomeEntity.AdvBanner, BaseViewHolder>(R.layout.item_home_banner, advDatas) {
        override fun convert(helper: BaseViewHolder?, item: HomeEntity.AdvBanner?) {

        }
    }
}