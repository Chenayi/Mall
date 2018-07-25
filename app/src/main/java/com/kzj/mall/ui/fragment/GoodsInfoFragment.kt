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

    override fun initData() {
        arguments?.getInt("barHeight")?.let {
            barHeight = it
        }

        //banner
        initBanner()
        //套餐组合
        initGroupData()

        mBinding?.slideDetailsLayout?.setOnSlideDetailsListener(object : SlideDetailsLayout.OnSlideDetailsListener {
            override fun onStatucChanged(status: SlideDetailsLayout.Status?) {
                if (status == SlideDetailsLayout.Status.CLOSE) {
                    EventBus.getDefault().post(ScrollChangedEvent(status, alpha))
                    mBinding?.ivArrow?.setImageResource(R.mipmap.up)
                    mBinding?.tvDetailTips?.text = "上拉查看图文详情"
                } else if (status == SlideDetailsLayout.Status.OPEN) {
                    EventBus.getDefault().post(ScrollChangedEvent(status, 1.0f))
                    mBinding?.ivArrow?.setImageResource(R.mipmap.down)
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

        val advDatas = advDatas()
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
                mBinding?.tvBannerNum?.setText((position + 1).toString() + "/" + advDatas.size)
            }

        })
        mBinding?.tvBannerNum?.setText("1/" + advDatas.size)
        mBinding?.banner?.setData(advDatas, null)
    }

    private fun advDatas(): MutableList<String> {
        var banners = ArrayList<String>()
        val banner1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861413&di=c9f7439a5a5d4c57435e5eb7f2772817&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a0d4582d8320a84a0e282be8a02e.jpg"
        val banner3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530523369994&di=60f87ef08f23f8dab2b36d5ed57f5dcd&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ac39597adf9da8012193a352df31.jpg"
        val banner2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861412&di=c51db760c9ecc789cdae3b334715aef6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0161c95690b86032f87574beaa54c2.jpg"
        banners.add(banner1)
        banners.add(banner3)
        banners.add(banner2)
        banners.add(banner3)
        return banners
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