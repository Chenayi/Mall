package com.kzj.mall.ui.activity

import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.anim.AniManager
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityGoodsDetailsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.event.*
import com.kzj.mall.ui.dialog.DetailMorePop
import com.kzj.mall.ui.fragment.GoodsDetailFragment
import com.kzj.mall.ui.fragment.GoodsInfoFragment
import com.kzj.mall.ui.fragment.GoodsZizhiFragment
import com.kzj.mall.widget.GoodsDetailTitleBar
import com.kzj.mall.widget.SlideDetailsLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class GoodsDetailActivity : BaseActivity<IPresenter, ActivityGoodsDetailsBinding>(), View.OnClickListener {
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_goods_details
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init();
    }

    override fun initData() {
        mBinding?.goodsDetailBar?.setTabAlpha(0f)
        val barHeight = SizeUtils.getMeasuredHeight(mBinding?.goodsDetailBar)
        fragments = ArrayList()
        fragments?.add(GoodsInfoFragment.newInstance(barHeight))
        fragments?.add(GoodsDetailFragment.newInstance())
        fragments?.add(GoodsZizhiFragment.newInstance())
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpGoodsDetail?.adapter = commomViewPagerAdapter
        mBinding?.vpGoodsDetail?.offscreenPageLimit = fragments?.size!!

        mBinding?.goodsDetailBar?.setOnMoreClickListener(object : GoodsDetailTitleBar.OnMoreClickListener {
            override fun onMoreClick() {
                showMorePop()
            }
        })

        mBinding?.tvAddCart?.setOnClickListener(this)
        mBinding?.goodsDetailBar?.setVp(mBinding?.vpGoodsDetail)
    }

    @Subscribe
    fun scrollChangedEvent(scrollChangedEvent: ScrollChangedEvent) {
        mBinding?.goodsDetailBar?.setTabAlpha(scrollChangedEvent.alpha)
        mImmersionBar
                ?.statusBarDarkFont(scrollChangedEvent.alpha > 0.5f, 0.5f)
                ?.init();
        if (scrollChangedEvent?.status == SlideDetailsLayout.Status.OPEN) {
            mBinding?.vpGoodsDetail?.setNoScroll(true)
            mBinding?.goodsDetailBar?.titleSwitch(true)
        } else if (scrollChangedEvent?.status == SlideDetailsLayout.Status.CLOSE) {
            mBinding?.vpGoodsDetail?.setNoScroll(false)
            mBinding?.goodsDetailBar?.titleSwitch(false)
        }
    }

    @Subscribe
    fun addCartEvent(addCartEvent: AddCartEvent) {
        startAddCartAnim(addCartEvent?.isGroup!!, addCartEvent?.startView!!, mBinding?.ivCart!!)
    }


    /**
     * 更多弹窗
     */
    private fun showMorePop() {
        val detailMorePop = DetailMorePop(this)
        detailMorePop?.setOnItemClickLinstener(object : DetailMorePop.OnItemClickLinstener {
            override fun onItemClick(p: Int) {
                when (p) {
                //消息
                    DetailMorePop.MSG -> {

                    }
                //首页
                    DetailMorePop.HOME -> {
                        EventBus.getDefault().post(CloseActivityEvent())
                        EventBus.getDefault().post(BackHomeEvent())
                    }
                //购物车
                    DetailMorePop.CART -> {
                        EventBus.getDefault().post(CloseActivityEvent())
                        EventBus.getDefault().post(BackCartEvent())
                    }
                //个人中心
                    DetailMorePop.PERSON -> {
                        EventBus.getDefault().post(CloseActivityEvent())
                        EventBus.getDefault().post(BackMinetEvent())
                    }
                }
            }
        })
        detailMorePop?.showPopupWindow(mBinding?.goodsDetailBar)
    }

    private fun startAddCartAnim(isGroup: Boolean, startView: View, endView: View) {
        // 动画开始的坐标
        val startLocation = IntArray(2)
        startView.getLocationInWindow(startLocation);

        // 动画结束的坐标
        val endLocation = IntArray(2)
        endView?.getLocationInWindow(endLocation)


        var cartImageView = ImageView(this)
        cartImageView?.setImageResource(R.drawable.circle_orange)

        val aniManager = AniManager(this)
        aniManager?.setOnAnimListener(object : AniManager.AnimListener {
            override fun setAnimBegin(a: AniManager?) {
            }

            override fun setAnimEnd(a: AniManager?) {
                var num = mBinding?.tvCartNum?.text.toString().toInt()
                mBinding?.tvCartNum?.text = (num + 1).toString()
            }

        })
        if (isGroup) {
            aniManager?.startGroupCartAnim(cartImageView, startLocation, endLocation)
        } else {
            aniManager?.startCartAnim(cartImageView, startView, startLocation, endLocation)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_add_cart -> {
                startAddCartAnim(false, mBinding?.tvAddCart!!, mBinding?.ivCart!!)
            }
        }
    }
}