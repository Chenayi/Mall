package com.kzj.mall.adapter.provider.home

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeHeaderBannerEntity
import com.kzj.mall.entity.home.IHomeEntity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.transformer.ScaleInTransformer
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.kzj.mall.widget.IndictorView
import com.makeramen.roundedimageview.RoundedImageView
import com.tmall.ultraviewpager.UltraViewPager


class HeaderBannerProvider : BaseItemProvider<HomeHeaderBannerEntity, BaseViewHolder> {
    private var useRoundedCorners = true
    private var bannerPlaying = true
    private var onBannerPageChangeListener: OnBannerPageChangeListener? = null
    private var ultraViewPager: UltraViewPager? = null

    private var mColors: MutableMap<Int, Int>? = null
    private var initFirstColor = true

    var refresh = true

    constructor(useRoundedCorners: Boolean) {
        this.useRoundedCorners = useRoundedCorners
        mColors = HashMap()
    }

    override fun layout(): Int {
        return R.layout.home_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.HEADER_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: HomeHeaderBannerEntity?, position: Int) {
        if (refresh == true) {
            val banners = data?.adss
            val indictorView = helper?.getView<IndictorView>(R.id.indicator)
            indictorView?.setIndicatorsSize(banners?.size!!)
            indictorView?.setSelectIndex(0)
            ultraViewPager = helper?.getView(R.id.banner)
            ultraViewPager?.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
            val adapter = UltraPagerAdapter(banners, helper?.getView(R.id.tv_header))
            ultraViewPager?.viewPager?.pageMargin = SizeUtils.dp2px(10f)
            ultraViewPager?.viewPager?.offscreenPageLimit = banners?.size!!
            ultraViewPager?.setInfiniteLoop(true);
            ultraViewPager?.setAutoScroll(5000)
            ultraViewPager?.setAdapter(adapter);
            ultraViewPager?.setPageTransformer(true, ScaleInTransformer());

            ultraViewPager?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    val realPosition = position % banners?.size
                    mColors?.get(realPosition)?.let {
                        helper?.getView<TextView>(R.id.tv_header)?.setBackgroundColor(it)
                        onBannerPageChangeListener?.onBannerPageSelected(position, it)
                    }
                    indictorView?.setSelectIndex(realPosition)
                }

            })
            refresh = false
        }
    }

    fun setOnBannerPageChangeListener(onBannerPageChangeListener: OnBannerPageChangeListener) {
        this.onBannerPageChangeListener = onBannerPageChangeListener
    }

    fun startBanner() {
        if (bannerPlaying == false) {
            ultraViewPager?.setAutoScroll(5000)
        }
        bannerPlaying = true
    }

    fun pauseBanner() {
        if (bannerPlaying == true) {
            ultraViewPager?.disableAutoScroll()
        }
        bannerPlaying = false
    }

    interface OnBannerPageChangeListener {
        fun onBannerPageSelected(position: Int?, colorRes: Int?)
    }

    /**
     * 跳转商品详情
     */
    protected fun jumpGoodsDetail(goodsInfoId: String?) {
        val intent = Intent(mContext, GoodsDetailActivity::class.java)
        intent?.putExtra(C.GOODS_INFO_ID, goodsInfoId)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }


    inner class UltraPagerAdapter constructor(val advDatas: MutableList<HomeHeaderBannerEntity.Adds>?, val backGround: TextView?) : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return advDatas?.size!!
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, null, false)
            val imageView = view?.findViewById<RoundedImageView>(R.id.iv_image)
            if (useRoundedCorners) {
                imageView?.cornerRadius = SizeUtils.dp2px(8f).toFloat()
            } else {
                imageView?.cornerRadius = 0f
            }

            imageView?.setOnClickListener {
                jumpGoodsDetail(advDatas?.get(position)?.goodsInfoId)
            }

            val palette = GlidePalette.with(advDatas?.get(position)?.adCode)
                    .use(BitmapPalette.Profile.MUTED)
                    .intoCallBack {
                        val mutedColor = it?.getDominantColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                        mutedColor?.let {
                            if (mColors?.containsKey(position) == false){
                                mColors?.put(position, mutedColor)
                            }
                            if (initFirstColor && position == 0) {
                                backGround?.setBackgroundColor(it)
                                onBannerPageChangeListener?.onBannerPageSelected(position, it)
                                initFirstColor = false
                            }
                        }
                    }

            GlideApp.with(mContext)
                    .load(advDatas?.get(position)?.adCode)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .listener(palette)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(imageView!!)
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