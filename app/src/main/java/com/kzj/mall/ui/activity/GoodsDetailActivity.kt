package com.kzj.mall.ui.activity

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.anim.AniManager
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityGoodsDetailsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerGoodsDetailComponent
import com.kzj.mall.di.module.GoodsDetailModule
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.event.*
import com.kzj.mall.mvp.contract.GoodsDetailContract
import com.kzj.mall.mvp.presenter.GoodsDetailPresenter
import com.kzj.mall.ui.dialog.DetailMorePop
import com.kzj.mall.ui.fragment.GoodsDetailFragment
import com.kzj.mall.ui.fragment.GoodsInfoFragment
import com.kzj.mall.ui.fragment.GoodsZizhiFragment
import com.kzj.mall.widget.GoodsDetailTitleBar
import com.kzj.mall.widget.SlideDetailsLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class GoodsDetailActivity : BaseActivity<GoodsDetailPresenter, ActivityGoodsDetailsBinding>(), View.OnClickListener,GoodsDetailContract.View {
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null
    private var barAlpha = 0.0f
    private var mViewPagerIndex = 0
    private var mGoodsInfoId: String? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_goods_details
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerGoodsDetailComponent.builder()
                .appComponent(appComponent)
                .goodsDetailModule(GoodsDetailModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.statusBarDarkFont(true, 0.5f)
                ?.init();
    }

    override fun initData() {
        mGoodsInfoId = "29921"
//        mGoodsInfoId = intent?.getStringExtra(C.GOODS_INFO_ID)

        mBinding?.goodsDetailBar?.setTabAlpha(barAlpha)
        val barHeight = SizeUtils.getMeasuredHeight(mBinding?.goodsDetailBar)
        fragments = ArrayList()
        fragments?.add(GoodsInfoFragment.newInstance(barHeight))
        fragments?.add(GoodsDetailFragment.newInstance(barHeight))
        fragments?.add(GoodsZizhiFragment.newInstance(barHeight))
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpGoodsDetail?.adapter = commomViewPagerAdapter
        mBinding?.vpGoodsDetail?.offscreenPageLimit = fragments?.size!!

        mBinding?.vpGoodsDetail?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == 1) {
                    mViewPagerIndex = mBinding?.vpGoodsDetail?.currentItem!!
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                if (positionOffset != 0f) {
                    //向左
                    if (mViewPagerIndex == position) {
                        if (mViewPagerIndex == 0) {
                            var alpha = barAlpha + positionOffset
                            if (alpha > 1f) {
                                alpha = 1f
                            } else if (alpha < 0f) {
                                alpha = 0f
                            }
                            mBinding?.goodsDetailBar?.setTabAlpha(alpha)
                        }
                    }
                    //向右
                    else {
                        if (mViewPagerIndex == 1) {
                            var alpha = positionOffset
                            if (alpha < barAlpha) {
                                alpha = barAlpha
                            } else if (alpha > 1f) {
                                alpha = 1f
                            }
                            mBinding?.goodsDetailBar?.setTabAlpha(alpha)
                        }
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    mBinding?.goodsDetailBar?.setTabAlpha(barAlpha)
                } else {
                    mBinding?.goodsDetailBar?.setTabAlpha(1f)
                }
            }

        })

        mBinding?.goodsDetailBar?.setOnMoreClickListener(object : GoodsDetailTitleBar.OnMoreClickListener {
            override fun onMoreClick() {
                showMorePop()
            }
        })

        mBinding?.goodsDetailBar?.setOnBackClickListener(object : GoodsDetailTitleBar.OnBackClickListener {
            override fun onBackClick() {
                onBackPressedSupport()
            }
        })

        mBinding?.tvBuy?.setOnClickListener(this)
        mBinding?.tvAddCart?.setOnClickListener(this)
        mBinding?.goodsDetailBar?.setVp(mBinding?.vpGoodsDetail)


        mPresenter?.requesrGoodsDetail(mGoodsInfoId)
    }

    @Subscribe
    fun scrollChangedEvent(scrollChangedEvent: ScrollChangedEvent) {
        barAlpha = scrollChangedEvent.alpha
        mBinding?.goodsDetailBar?.setTabAlpha(barAlpha)
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

    /**
     * 加入购物车动画
     */
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


    override fun showGoodsDetail(goodsDetailEntity: GoodsDetailEntity?) {
        if (mBinding?.vpGoodsDetail?.currentItem == 0){
            val goodsInfoFragment = fragments?.get(0) as GoodsInfoFragment
            goodsInfoFragment?.updateDatas(goodsDetailEntity)
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }


    /**
     * 关注或取消关注
     */
    fun addOrCancelFollow(isFollow : Boolean?,c:OnFollowCallBack){
        LogUtils.e("关注或者取消关注...")
        isFollow?.let {
            c?.onFollowCallBack(!it)
        }
    }


    interface OnFollowCallBack{
        fun onFollowCallBack(isFollow:Boolean?)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_add_cart -> {
                startAddCartAnim(false, mBinding?.tvAddCart!!, mBinding?.ivCart!!)
            }
            R.id.tv_buy -> {
                val intent = Intent(this, ConfirmOrderActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressedSupport() {
        val currentItem = mBinding?.vpGoodsDetail?.currentItem
        if (currentItem != 0) {
            mBinding?.vpGoodsDetail?.currentItem = 0
        } else {
            val goodsInfoFragment = commomViewPagerAdapter?.fragments?.get(currentItem) as GoodsInfoFragment
            goodsInfoFragment.onBackClick()
        }
    }
}