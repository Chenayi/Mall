package com.kzj.mall.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.*
import cn.bingoogolapple.bgabanner.BGABanner
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.GoodsDetailGroupAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsInfoBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.event.*
import com.kzj.mall.ui.dialog.DetailMorePop
import com.kzj.mall.ui.dialog.GoodsSpecDialog
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.widget.ObservableScrollView
import com.kzj.mall.widget.SlideDetailsLayout
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
import me.yokeyword.fragmentation.ISupportFragment
import org.greenrobot.eventbus.EventBus

class GoodsInfoFragment : BaseFragment<IPresenter, FragmentGoodsInfoBinding>(), View.OnClickListener {
    private var tvGroupAddCart: TextView? = null
    private var goodsDetailGroupAdapter: GoodsDetailGroupAdapter? = null
    private var rvGroup: MultiSnapRecyclerView? = null

    private var barHeight = 0
    private var bannerHeight = 0
    private var alpha = 0.0f
    private var isLoadDetailFragment = false

    companion object {
        fun newInstance(barHeight: Int): Fragment {
            val goodsInfoDetailFragment = GoodsInfoFragment()
            var b = Bundle()
            b.putInt("barHeight", barHeight)
            goodsInfoDetailFragment?.arguments = b
            return goodsInfoDetailFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_info
    }
    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {
        arguments?.getInt("barHeight")?.let {
            barHeight = it
        }

        mBinding?.fabUpSlide?.hide()

        //banner
        initBanner()
        //套餐组合
        initGroupData()

        mBinding?.slideDetailsLayout?.setOnSlideDetailsListener(object : SlideDetailsLayout.OnSlideDetailsListener {
            override fun onStatucChanged(status: SlideDetailsLayout.Status?) {
                if (status == SlideDetailsLayout.Status.CLOSE) {
                    EventBus.getDefault().post(ScrollChangedEvent(status, alpha))
                    mBinding?.fabUpSlide?.hide()
                    mBinding?.ivArrow?.setImageResource(R.mipmap.up)
                    mBinding?.tvDetailTips?.text = "上拉查看图文详情"
                } else if (status == SlideDetailsLayout.Status.OPEN) {
                    EventBus.getDefault().post(ScrollChangedEvent(status, 1.0f))
                    mBinding?.ivArrow?.setImageResource(R.mipmap.down)
                    mBinding?.fabUpSlide?.show()
                    mBinding?.tvDetailTips?.text = "下拉收起图文详情"

                    if (!isLoadDetailFragment) {
                        loadRootFragment(R.id.fl_content, GoodsDetailBottomFragment.newInstance(barHeight))
                        isLoadDetailFragment = true
                    }
                }
            }
        })

        mBinding?.osv?.setOnScrollChangedListener(object : ObservableScrollView.OnScrollChangedListener {
            override fun onScrollChanged(who: NestedScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                val i = y.toFloat() / bannerHeight.toFloat()
                alpha = if (i < 1f) i else 1f
                EventBus.getDefault().post(ScrollChangedEvent(SlideDetailsLayout.Status.CLOSE, alpha))
            }
        })


        mBinding?.detailSpec?.setOnClickListener(this)
        mBinding?.fabUpSlide?.setOnClickListener(this)
    }


    /**
     * banner
     */
    private fun initBanner() {
        var screenWidth = ScreenUtils.getScreenWidth()
        var params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(screenWidth, screenWidth)
        mBinding?.rlBanner?.layoutParams = params
        mBinding?.rlBanner?.requestLayout()
        bannerHeight = screenWidth

        mBinding?.banner?.setAdapter(BGABanner.Adapter<ImageView, String> { banner, itemView, model, position ->
            GlideApp.with(this)
                    .load(model)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .dontAnimate()
                    .into(itemView)
        })
        mBinding?.banner?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBinding?.tvBannerNum?.setText((position + 1).toString() + "/" + mBinding?.banner?.views?.size?.toString())
            }

        })
        mBinding?.banner?.setData(ArrayList(), null)
    }

    /**
     * 更新数据
     */
    fun updateDatas(goodsDetailEntity: GoodsDetailEntity?){
        goodsDetailEntity?.let {
            val goodsImgs = it.gn?.goodsImgs
            val advDatas = ArrayList<String>()
            for (i in 0 until goodsImgs?.size!!){
                advDatas.add(goodsImgs?.get(i).imageArtworkName!!)
            }
            mBinding?.banner?.setData(advDatas, null)
            if (advDatas.size > 0){
                mBinding?.tvBannerNum?.setText("1/" + advDatas.size)
            }
        }
    }


    /**
     * 套餐组合
     */
    private fun initGroupData() {
        tvGroupAddCart = view?.findViewById(R.id.tv_group_add_cart)
        tvGroupAddCart?.setOnClickListener(this)
        goodsDetailGroupAdapter = GoodsDetailGroupAdapter(LocalDatas.goodsDetailGroups())
        rvGroup = view?.findViewById(R.id.rv_group)
        rvGroup?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvGroup?.adapter = goodsDetailGroupAdapter
    }

    /**
     * 规格弹窗
     */
    fun showSpecDialog() {
        GoodsSpecDialog.newInstance()
                .setShowBottom(true)
                .show(childFragmentManager)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.detail_spec -> {
                showSpecDialog()
            }
            R.id.tv_group_add_cart -> {
                EventBus.getDefault().post(AddCartEvent(true, tvGroupAddCart))
            }
            R.id.fab_up_slide->{
                mBinding?.slideDetailsLayout?.smoothClose(true)
            }
        }
    }

    fun onBackClick(){
        if (mBinding?.slideDetailsLayout?.status == SlideDetailsLayout.Status.OPEN){
            mBinding?.slideDetailsLayout?.smoothClose(true)
        }else{
            activity?.finish()
        }
    }
}