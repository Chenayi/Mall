package com.kzj.mall.adapter.provider

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeAdvBannerEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.transformer.ScaleInTransformer
import com.tmall.ultraviewpager.UltraViewPager

/**
 * 穿插广告
 */
class HomeAdvBannerProvider : BaseItemProvider<HomeAdvBannerEntity, BaseViewHolder>() {
    var isInitialized = false
    var viewPager: UltraViewPager? = null

    override fun layout(): Int {
        return R.layout.item_home_adv_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.ADV_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: HomeAdvBannerEntity?, position: Int) {
        if (isInitialized == false) {
            viewPager = helper?.getView(R.id.vp)
            viewPager?.viewPager?.pageMargin = 20
            viewPager?.viewPager?.offscreenPageLimit = 3

            data?.banners?.let {
                viewPager?.adapter = MyAdapter(it)
                viewPager?.setPageTransformer(true, ScaleInTransformer());
            }
            isInitialized = true
        }
    }

    inner class MyAdapter constructor(val advDatas : MutableList<HomeAdvBannerEntity.Banners>) : PagerAdapter() {
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