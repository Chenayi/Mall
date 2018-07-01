package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.IHomeEntity
import com.tmall.ultraviewpager.UltraViewPager
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.transformer.ScaleInTransformer


/**
 * 男科广告banner
 */
class AndrologyAdvBannerProvider : BaseItemProvider<IHomeEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_adv_banner_list
    }

    override fun viewType(): Int {
        return IHomeEntity.ADV_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        val ultraViewPager = helper?.getView<UltraViewPager>(R.id.ultra_viewpager)
        ultraViewPager?.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        val adapter = UltraPagerAdapter(advDatas())
        ultraViewPager?.viewPager?.pageMargin =SizeUtils.dp2px(10f)
        ultraViewPager?.viewPager?.offscreenPageLimit = 3
        ultraViewPager?.setAdapter(adapter)
        ultraViewPager?.setInfiniteLoop(true);
        ultraViewPager?.setAdapter(adapter);
        ultraViewPager?.setPageTransformer(true, ScaleInTransformer());
    }

    private fun advDatas(): MutableList<HomeEntity.AdvBanner> {
        var datas = ArrayList<HomeEntity.AdvBanner>()
        for (i in 0..6) {
            datas.add(HomeEntity().AdvBanner())
        }
        return datas
    }

    inner class UltraPagerAdapter constructor(val advDatas: MutableList<HomeEntity.AdvBanner>) : PagerAdapter() {
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