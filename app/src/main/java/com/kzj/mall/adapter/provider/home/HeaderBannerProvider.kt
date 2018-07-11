package com.kzj.mall.adapter.provider.home

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.transformer.ScaleInTransformer
import com.tmall.ultraviewpager.UltraViewPager

class HeaderBannerProvider : BaseItemProvider<IHomeEntity, BaseViewHolder> {

    var ultraViewPager: UltraViewPager? = null
    var isInitialized = false
    var headerColor = 0

    constructor() : this(0)
    constructor(headerColor: Int) {
        this.headerColor = headerColor
    }

    override fun layout(): Int {
        return R.layout.home_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.HEADER_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        if (isInitialized == false) {
            ultraViewPager = helper?.getView(R.id.banner)
            ultraViewPager?.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
            val adapter = UltraPagerAdapter(advDatas())
            ultraViewPager?.viewPager?.pageMargin = SizeUtils.dp2px(10f)
            ultraViewPager?.viewPager?.offscreenPageLimit = 3
            ultraViewPager?.setAdapter(adapter)
            ultraViewPager?.setInfiniteLoop(true)
            ultraViewPager?.setAdapter(adapter)
            ultraViewPager?.setPageTransformer(true, ScaleInTransformer())
            isInitialized = true
        }


        if (headerColor != 0) {
            helper?.setImageResource(R.id.iv_header, headerColor)
        }
    }

    private fun advDatas(): MutableList<HomeEntity.AdvBanner> {
        var banners = ArrayList<HomeEntity.AdvBanner>()
        val banner1 = HomeEntity().AdvBanner()
        banner1.bannerUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861413&di=c9f7439a5a5d4c57435e5eb7f2772817&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a0d4582d8320a84a0e282be8a02e.jpg"

        val banner3 = HomeEntity().AdvBanner()
        banner3.bannerUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530523369994&di=60f87ef08f23f8dab2b36d5ed57f5dcd&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ac39597adf9da8012193a352df31.jpg"

        val banner2 = HomeEntity().AdvBanner()
        banner2.bannerUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861412&di=c51db760c9ecc789cdae3b334715aef6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0161c95690b86032f87574beaa54c2.jpg"
        banners.add(banner1)
        banners.add(banner3)
        banners.add(banner2)
        banners.add(banner3)
        return banners
    }

    fun startBanner() {
        ultraViewPager?.setAutoScroll(3000)
    }

    fun pauseBanner() {
        ultraViewPager?.disableAutoScroll()
    }

    inner class UltraPagerAdapter constructor(val advDatas: MutableList<HomeEntity.AdvBanner>) : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return advDatas?.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_home_header_banner, null, false)
            val ivImage = view.findViewById<ImageView>(R.id.iv_image)
            GlideApp
                    .with(mContext)
                    .load(advDatas?.get(position).bannerUrl)
                    .centerCrop()
                    .placeholder(R.color.gray_default)
                    .into(ivImage)
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