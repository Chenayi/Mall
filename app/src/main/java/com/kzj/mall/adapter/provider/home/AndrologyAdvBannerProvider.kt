package com.kzj.mall.adapter.provider.home

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.IHomeEntity
import com.tmall.ultraviewpager.UltraViewPager
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.entity.home.AndrologyAdvBannerEntity
import com.kzj.mall.transformer.ScaleInTransformer


/**
 * 男科广告banner
 */
class AndrologyAdvBannerProvider : BaseItemProvider<AndrologyAdvBannerEntity, BaseViewHolder>() {
    var isInitialized = false
    var ultraViewPager: UltraViewPager? = null

    override fun layout(): Int {
        return R.layout.item_andrology_adv_banner_list
    }

    override fun viewType(): Int {
        return IHomeEntity.MALE_ADV_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: AndrologyAdvBannerEntity?, position: Int) {
        if (isInitialized == false) {
            ultraViewPager = helper?.getView(R.id.ultra_viewpager)
            ultraViewPager?.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
            val adapter = UltraPagerAdapter(data?.banners)
            ultraViewPager?.viewPager?.pageMargin = SizeUtils.dp2px(10f)
            ultraViewPager?.viewPager?.offscreenPageLimit = 3
            ultraViewPager?.setAdapter(adapter)
            ultraViewPager?.setInfiniteLoop(true);
            ultraViewPager?.setAdapter(adapter);
            ultraViewPager?.setPageTransformer(true, ScaleInTransformer());
            isInitialized = true
        }
    }

    inner class UltraPagerAdapter constructor(val advDatas: MutableList<AndrologyAdvBannerEntity.Banners>?) : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return advDatas?.size!!
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