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
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.kzj.mall.GlideApp
import com.kzj.mall.widget.IndictorView
import com.makeramen.roundedimageview.RoundedImageView
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zhouwei.mzbanner.holder.MZViewHolder


class HeaderBannerProvider : BaseItemProvider<HomeHeaderBannerEntity, BaseViewHolder> {
    private var initFirstColor = false
    private var isInitialized = false
    private var useRoundedCorners = true
    private var bannerPlaying = true
    private var onBannerPageChangeListener: OnBannerPageChangeListener? = null
    private var mMZBanner: MZBannerView<HomeHeaderBannerEntity.Adds>? = null
    private var mColors: MutableMap<Int, Int>? = null

    constructor(useRoundedCorners: Boolean) {
        mColors = HashMap()
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
            val banners = data?.adss
            mMZBanner = helper?.getView(R.id.banner)
            mMZBanner?.setIndicatorVisible(false)
            val indictorView = helper?.getView<IndictorView>(R.id.indicator)
            indictorView?.setIndicatorsSize(banners?.size!!)
            indictorView?.setSelectIndex(0)
            mMZBanner?.setPages(banners, object : MZHolderCreator<BannerViewHolder> {
                override fun createViewHolder(): BannerViewHolder {
                    val bannerViewHolder = BannerViewHolder(banners?.size, helper?.getView(R.id.tv_header))
                    return bannerViewHolder;
                }
            })
            mMZBanner?.addPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    indictorView?.setSelectIndex(position)
                    val color = mColors?.get(position)
                    color?.let {
                        helper?.getView<TextView>(R.id.tv_header)?.setBackgroundColor(it)
                        onBannerPageChangeListener?.onBannerPageSelected(position, it)
                    }
                }

            })

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

    inner class BannerViewHolder constructor(val bannersSize: Int?, val backGround: TextView?) : MZViewHolder<HomeHeaderBannerEntity.Adds> {
        private var mImageView: RoundedImageView? = null
        override fun createView(context: Context): View {
            // 返回页面布局
            val view = LayoutInflater.from(context).inflate(R.layout.item_home_banner, null)
            mImageView = view.findViewById(R.id.iv_image)
            return view
        }

        override fun onBind(context: Context, position: Int, data: HomeHeaderBannerEntity.Adds?) {
            // 数据绑定
            mImageView?.let {

                if (useRoundedCorners) {
                    mImageView?.cornerRadius = SizeUtils.dp2px(8f).toFloat()
                } else {
                    mImageView?.cornerRadius = 0f
                }

                val realPosition = position % bannersSize!!
                val palette = GlidePalette.with(data?.adCode)
                        .use(BitmapPalette.Profile.MUTED)
                        .intoCallBack {
                            val mutedColor = it?.getMutedColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                            mutedColor?.let {
                                mColors?.put(realPosition, it)
                                if (initFirstColor == false && realPosition == 0) {
                                    backGround?.setBackgroundColor(it)
                                    onBannerPageChangeListener?.onBannerPageSelected(realPosition, it)
                                    initFirstColor = true
                                }
                            }
                        }

                GlideApp.with(context)
                        .load(data?.adCode)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .listener(palette)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(it)
            }
        }
    }
}