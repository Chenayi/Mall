package com.kzj.mall.adapter.provider.home

import android.content.Context
import android.support.v4.content.ContextCompat
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
    private var headerColor = R.color.colorPrimary
    private var useRoundedCorners = true
    private var bannerPlaying = true
    private var onBannerPageChangeListener: OnBannerPageChangeListener? = null
    private var mMZBanner: MZBannerView<String>? = null
    private var headerBannerView: View? = null

    constructor() : this(R.color.colorPrimary, true)
    constructor(headerColor: Int, useRoundedCorners: Boolean) {
        this.headerColor = headerColor
        this.useRoundedCorners = useRoundedCorners
    }

    override fun layout(): Int {
        return R.layout.home_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.HEADER_BANNER
    }

    fun setHeaderBannerView(headerBannerView:View?){
        this.headerBannerView = headerBannerView
    }

    fun getHeaderBannerView() = headerBannerView

    override fun convert(helper: BaseViewHolder?, data: HomeHeaderBannerEntity?, position: Int) {
        if (isInitialized == false) {
            val banners = data?.banners
            val bannerUrls = ArrayList<String>()
            for (i in 0 until banners?.size!!) {
                bannerUrls.add(banners?.get(i).bannerUrl!!)
            }

            mMZBanner = helper?.getView(R.id.banner)
            mMZBanner?.setPages(bannerUrls, object : MZHolderCreator<BannerViewHolder> {
                override fun createViewHolder(): BannerViewHolder {
                    return BannerViewHolder(helper?.getView(R.id.tv_header));
                }
            })
            mMZBanner?.setIndicatorRes(R.drawable.indicator_default, R.drawable.indicator_sel)
            mMZBanner?.addPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                }

            })
            mMZBanner?.start()
            isInitialized = true
        }

        helper?.setBackgroundColor(R.id.tv_header, ContextCompat.getColor(mContext, headerColor))
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
        fun onBannerPageSelected(position: Int?, bannerUrl: String?)
    }


    inner class BannerViewHolder constructor(val tv:TextView?) : MZViewHolder<String> {
        private var mImageView: ImageView? = null
        override fun createView(context: Context): View {
            // 返回页面布局
            val view = LayoutInflater.from(context).inflate(R.layout.item_home_banner, null)
            mImageView = view.findViewById(R.id.iv_image) as ImageView
            return view
        }

        override fun onBind(context: Context, position: Int, data: String?) {
            // 数据绑定
            mImageView?.run {
                val palette = GlidePalette.with(data)

                palette.use(BitmapPalette.Profile.MUTED)
                        .crossfade(true)
                        .intoBackground(tv)

                if (headerBannerView!=null){
                    palette.use(BitmapPalette.Profile.MUTED)
                            .crossfade(true)
                            .intoBackground(headerBannerView)
                }

                GlideApp.with(context)
                        .load(data)
                        .listener(palette)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(this)
            }
        }
    }
}