package com.kzj.mall.adapter.provider.home

import android.content.Context
import android.support.v4.view.ViewPager
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeHeaderBannerEntity
import com.kzj.mall.entity.home.IHomeEntity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.kzj.mall.GlideApp
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zhouwei.mzbanner.holder.MZViewHolder


class HeaderBannerProvider : BaseItemProvider<HomeHeaderBannerEntity, BaseViewHolder> {

    private var isInitialized = false
    private var useRoundedCorners = true
    private var bannerPlaying = true
    private var onBannerPageChangeListener: OnBannerPageChangeListener? = null
    private var mMZBanner: MZBannerView<HomeHeaderBannerEntity.Banners>? = null
    private var mColors: MutableMap<Int, Int>? = null

    private var headerColor = 0
    constructor(headerColor: Int, useRoundedCorners: Boolean) {
        mColors = HashMap()
        this.headerColor = headerColor
        this.useRoundedCorners = useRoundedCorners
    }

    override fun layout(): Int {
        return R.layout.home_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.HEADER_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: HomeHeaderBannerEntity?, position: Int) {
        if (isInitialized == false) {
            val banners = data?.banners
            mMZBanner = helper?.getView(R.id.banner)
            mMZBanner?.setIndicatorRes(R.drawable.indicator_default, R.drawable.indicator_sel)
            mMZBanner?.setPages(banners, object : MZHolderCreator<BannerViewHolder> {
                override fun createViewHolder(): BannerViewHolder {
                    val bannerViewHolder = BannerViewHolder(helper?.getView(R.id.tv_header))
                    return bannerViewHolder;
                }
            })
            mMZBanner?.addPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    val color = mColors?.get(position)
                    helper?.getView<TextView>(R.id.tv_header)?.setBackgroundColor(color!!)
                    onBannerPageChangeListener?.onBannerPageSelected(position, color)
                }

            })

            helper?.getView<TextView>(R.id.tv_header)?.setBackgroundColor(headerColor)
            onBannerPageChangeListener?.onBannerPageSelected(position, headerColor)
            mMZBanner?.start()
            isInitialized = true
        }
    }

    fun setOnBannerPageChangeListener(onBannerPageChangeListener: OnBannerPageChangeListener) {
        this.onBannerPageChangeListener = onBannerPageChangeListener
    }

    fun startBanner() {
        if (bannerPlaying == false) {
            mMZBanner?.start();
        }
        bannerPlaying = true
    }

    fun pauseBanner() {
        if (bannerPlaying == true) {
            mMZBanner?.pause()
        }
        bannerPlaying = false
    }

    interface OnBannerPageChangeListener {
        fun onBannerPageSelected(position: Int?, colorRes: Int?)
    }

    inner class BannerViewHolder constructor(val backGround: TextView?) : MZViewHolder<HomeHeaderBannerEntity.Banners> {
        private var mImageView: ImageView? = null
        private var initFirstColor = false
        override fun createView(context: Context): View {
            // 返回页面布局
            val view = LayoutInflater.from(context).inflate(R.layout.item_home_banner, null)
            mImageView = view.findViewById(R.id.iv_image) as ImageView
            return view
        }

        override fun onBind(context: Context, position: Int, data: HomeHeaderBannerEntity.Banners?) {
            // 数据绑定
            mImageView?.let {
                val palette = GlidePalette.with(data?.bannerUrl)
                        .use(BitmapPalette.Profile.MUTED)
                        .intoCallBack {
                            val mutedColor = it?.getMutedColor(headerColor)
                            mColors?.put(position, mutedColor!!)

                            if (initFirstColor == false && position == 0){
                                backGround?.setBackgroundColor(mutedColor!!)
                                onBannerPageChangeListener?.onBannerPageSelected(position, mutedColor)
                                initFirstColor = true
                            }
                        }

                GlideApp.with(context)
                        .load(data?.bannerUrl)
                        .listener(palette)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(it)
            }
        }
    }
}