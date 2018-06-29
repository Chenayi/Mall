package com.kzj.mall.adapter.provider

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.IHomeEntity
import com.kzj.mall.transformer.ScaleInTransformer
import com.tmall.ultraviewpager.UltraViewPager


class AdvBannerProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_adv_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.ADV_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        val myAdapter = MyAdapter()
        val viewPager = helper?.getView<UltraViewPager>(R.id.vp)
        viewPager?.viewPager?.pageMargin =20
        viewPager?.viewPager?.offscreenPageLimit = 3
        viewPager?.adapter = myAdapter
        viewPager?.setPageTransformer(true, ScaleInTransformer());
    }

    private fun advDatas(): MutableList<HomeEntity.AdvBanner> {
        var datas = ArrayList<HomeEntity.AdvBanner>()
        for (i in 0..6) {
            datas.add(HomeEntity().AdvBanner())
        }
        return datas
    }


    inner class MyAdapter : PagerAdapter() {
        private val advDatas = advDatas()

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return advDatas?.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, null, false)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container?.removeView(`object` as View?)
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }
}