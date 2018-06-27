package com.kzj.mall.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.HomeBannerBinding
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zhouwei.mzbanner.holder.MZViewHolder
import android.view.LayoutInflater


class HomeBanner : BaseRelativeLayout<HomeBannerBinding> {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun getLayoutId(): Int {
        return R.layout.home_banner
    }

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        mBinding?.banner?.setIndicatorRes(R.drawable.indicator_default, R.drawable.indicator_sel)
    }

    fun setBanners(banners: MutableList<String>) {
        mBinding?.banner?.setPages(banners, 0, object : MZHolderCreator<BannerViewHolder> {
            override fun createViewHolder(): BannerViewHolder {
                return BannerViewHolder()
            }
        })
        mBinding?.banner?.setDelayedTime(3000)
        mBinding?.banner?.start()

    }

    fun setBannerPadding(left: Int, top: Int, right: Int, bottom: Int) {
        mBinding?.rlBannerRoot?.setPadding(left, top, right, bottom)
    }

    inner class BannerViewHolder : MZViewHolder<String> {
        private var mImageView: ImageView? = null

        override fun onBind(p0: Context?, p1: Int, p2: String?) {
            if (p1 % 2 == 0) {
                mImageView?.setImageResource(R.color.colorPrimaryDark)
            } else {
                mImageView?.setImageResource(R.color.colorAccent)
            }
        }

        override fun createView(p0: Context?): View {
            val view = LayoutInflater.from(context).inflate(R.layout.item_home_banner, null)
            mImageView = view?.findViewById(R.id.banner_image)
            return view
        }

    }

    fun pause() {
        mBinding?.banner?.pause()
    }

    fun start() {
        mBinding?.banner?.start()
    }
}