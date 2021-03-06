package com.kzj.mall.ui.activity

import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.BuildConfig
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.RequestCode
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.anim.AniManager
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityGoodsDetailsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerGoodsDetailComponent
import com.kzj.mall.di.module.GoodsDetailModule
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.event.*
import com.kzj.mall.mvp.contract.GoodsDetailContract
import com.kzj.mall.mvp.presenter.GoodsDetailPresenter
import com.kzj.mall.ui.activity.login.LoginActivity
import com.kzj.mall.ui.dialog.DetailMorePop
import com.kzj.mall.ui.fragment.GoodsDetailFragment
import com.kzj.mall.ui.fragment.GoodsInfoFragment
import com.kzj.mall.ui.fragment.GoodsZizhiFragment
import com.kzj.mall.widget.GoodsDetailTitleBar
import com.kzj.mall.widget.SlideDetailsLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class GoodsDetailActivity : BaseActivity<GoodsDetailPresenter, ActivityGoodsDetailsBinding>(), View.OnClickListener, GoodsDetailContract.View {
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null
    private var barAlpha = 0.0f
    private var mViewPagerIndex = 0

    /**
     * 商品id
     */
    private var mGoodsInfoId: String? = null
    private var goodsDetailEntity: GoodsDetailEntity? = null

    /**
     * 组合套餐id
     */
    private var mCombinationId: String? = null

    /**
     * 是否组合套餐
     */
    private var isCombination = false

    /**
     * 商品数量
     */
    private var mGoodsNum = 1


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

    /**
     * 直降商品：34204
     * 折扣商品：40068
     * 满赠商品：29921
     * 满减商品：870
     */
    override fun initData() {
//        mGoodsInfoId = "29921"
        mGoodsInfoId = intent?.getStringExtra(C.GOODS_INFO_ID)
        if (BuildConfig.DEBUG) {
            LogUtils.e("goodsInfoId ===> " + mGoodsInfoId)
        }

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

        mBinding?.llPhone?.setOnClickListener(this)
        mBinding?.tvBuy?.setOnClickListener(this)
        mBinding?.tvAddCart?.setOnClickListener(this)
        mBinding?.tvRequestCheckin?.setOnClickListener(this)
        mBinding?.goodsDetailBar?.setVp(mBinding?.vpGoodsDetail)
        mBinding?.llCart?.setOnClickListener(this)


        mPresenter?.requesrGoodsDetail(mGoodsInfoId)
        if (C.IS_LOGIN){
            mPresenter?.cartCount()
        }
    }

    /**
     * 滑动监听
     */
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

    /**
     * 登录成功
     */
    @Subscribe
    fun loginSuccess(loginSuccessEvent: LoginSuccessEvent){
        ToastUtils.showShort("登录成功")
        mPresenter?.followStatus(mGoodsInfoId)
        mPresenter.cartCount()
    }

    /**
     * 加入购物车
     */
    @Subscribe
    fun addGroupCartEvent(addCartEvent: AddGroupCartEvent) {
        goodsDetailEntity?.gn?.goodsStock?.let {
            if (it > 0) {
                startAddCartAnim(null, addCartEvent.combinationId, "2", true, addCartEvent?.startView!!, mBinding?.ivCart!!)
            }
        }
    }

    /**
     * 疗程切换
     */
    @Subscribe
    fun packageListChange(packageListEvent: PackageListEvent) {
        mGoodsInfoId = packageListEvent?.goodsInfoId
        isCombination = packageListEvent?.isCombination
    }

    /**
     * 套餐切换
     */
    @Subscribe
    fun combinationChange(combinationEvent: CombinationEvent) {
        val combinations = combinationEvent?.combinationList
        mGoodsInfoId = combinations?.goods_info_id
        mCombinationId = combinations?.combination_id
        isCombination = combinationEvent?.isCombination
    }

    /**
     * 规格切换
     */
    @Subscribe
    fun specChange(goodSpecChangeEvent: GoodSpecChangeEvent) {
        val goodsDetailEntity = goodSpecChangeEvent?.goodsDetailEntity
        mGoodsInfoId = goodsDetailEntity?.gin?.goods_info_id
        showGoodsDetail(goodsDetailEntity)
    }


    /**
     * 商品数量
     */
    @Subscribe
    fun goodsNumChange(goodsNumChangeEvent: GoodsNumChangeEvent) {
        mGoodsNum = goodsNumChangeEvent?.goodsNum
    }

    /**
     * 加入购物车
     */
    @Subscribe
    fun addCart(addCartEvent: AddCartEvent) {
        //加入购物车
        var carType = "0"
        if (isCombination) {
            carType = "2"
        }
        mPresenter?.addCar(carType, mGoodsNum, mGoodsInfoId, mCombinationId)
    }

    /**
     * 立即购买
     */
    @Subscribe
    fun buyNow(buyNowEvent: BuyNowEvent) {
        var carType = "0"
        if (isCombination) {
            carType = "2"
        }
        mPresenter?.buyNow(carType, mGoodsNum, mGoodsInfoId, mCombinationId)
    }

    /**
     * 提交处方登记
     */
    @Subscribe
    fun submitDemand(submitDemandEvent: SubmitDemandEvent) {
        var goodsType = "0"
        if (isCombination) {
            goodsType = "2"
        }
        mPresenter?.demandRecord(goodsType, mGoodsInfoId, mCombinationId)
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
        detailMorePop?.setOffsetY(-SizeUtils.dp2px(6f))
        detailMorePop?.showPopupWindow(mBinding?.goodsDetailBar)
    }

    /**
     * 加入购物车动画
     */
    private fun startAddCartAnim(goodsInfoId: String?, combinationId: String?, carType: String, isGroup: Boolean, startView: View, endView: View) {
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
                //加入购物车
                mPresenter?.addCar(carType, mGoodsNum, goodsInfoId, combinationId)
            }

        })
        if (isGroup) {
            aniManager?.startGroupCartAnim(cartImageView, startLocation, endLocation)
        } else {
            aniManager?.startCartAnim(cartImageView, startView, startLocation, endLocation)
        }
    }

    override fun addCartError() {
    }

    override fun updateFollowStatus(goodsDetailEntity: GoodsDetailEntity?) {
        val goodsInfoFragment = fragments?.get(0) as GoodsInfoFragment
        goodsInfoFragment?.updateFollowStatus(goodsDetailEntity)
    }

    override fun showCartCount(count: Int?) {
        count?.let {
            if (it > 0) {
                mBinding?.tvCartNum?.visibility = View.VISIBLE
                mBinding?.tvCartNum?.text = count?.toString()
            }
        }
    }

    /**
     * 商品详情信息
     */
    override fun showGoodsDetail(goodsDetailEntity: GoodsDetailEntity?) {
        this.goodsDetailEntity = goodsDetailEntity

        val goodsInfoFragment = fragments?.get(0) as GoodsInfoFragment
        goodsInfoFragment?.updateDatas(goodsDetailEntity)

        (fragments?.get(1) as GoodsDetailFragment)?.updateDatas(goodsDetailEntity)

        val gn = goodsDetailEntity?.gn

        gn?.goodsType?.let {
            //处方
            if (it.equals("0")) {
                mBinding?.llBuy?.visibility = View.GONE
                mBinding?.tvRequestCheckin?.visibility = View.VISIBLE

                mBinding?.tvNoStock?.visibility = View.GONE
            }

            //非处方
            else {
                mBinding?.llBuy?.visibility = View.VISIBLE
                mBinding?.tvRequestCheckin?.visibility = View.GONE

                if (gn?.goodsStock!! <= 0) {
                    mBinding?.tvNoStock?.visibility = View.VISIBLE
                } else {
                    mBinding?.tvNoStock?.visibility = View.GONE
                }
            }
        }
    }

    /**
     * 立即购买
     */
    override fun buyNow(buyEntity: BuyEntity?) {
        val intent = Intent(this, ConfirmOrderActivity::class.java)
        intent?.putExtra("buyEntity", buyEntity)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    /**
     * 处方登记
     */
    override fun demandRecord(buyEntity: BuyEntity?) {


        var rxRecordType = "1"
        var goodsinfoid = mGoodsInfoId
        if (isCombination) {
            rxRecordType = "2"
            goodsinfoid = mCombinationId
        }

        val intent = Intent(this, DemandRegistrationActivity::class.java)
        intent?.putExtra("buyEntity", buyEntity)
        intent?.putExtra("goodsNum", mGoodsNum)
        intent?.putExtra("rxRecordType", rxRecordType)
        intent?.putExtra("goodsinfoid", goodsinfoid)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
        //商品已下架
        if (code == 3002) {
            finish()
        }
        //商品不存在
        else if (code == 3001) {
            finish()
        }
    }


    /**
     * 关注或取消关注
     */
    fun addOrCancelFollow(isFollow: Boolean) {
        mPresenter?.saveGoodsAtte(mGoodsInfoId, !isFollow)
    }


    override fun colllectSuccess() {
        (fragments?.get(0) as GoodsInfoFragment).colllectSuccess()
    }

    override fun cancelCollectSuccess() {
        (fragments?.get(0) as GoodsInfoFragment).cancelCollectSuccess()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_add_cart -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivityForResult(intent, RequestCode.LOGIN)
                    return
                }
                var carType = "0"
                if (isCombination) {
                    carType = "2"
                }
                startAddCartAnim(mGoodsInfoId, mCombinationId, carType, false, mBinding?.tvAddCart!!, mBinding?.ivCart!!)
            }
            R.id.tv_buy -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivityForResult(intent, RequestCode.LOGIN)
                    return
                }
                var carType = "0"
                if (isCombination) {
                    carType = "2"
                }
                mPresenter?.buyNow(carType, mGoodsNum, mGoodsInfoId, mCombinationId)
            }

            R.id.tv_request_checkin -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivityForResult(intent, RequestCode.LOGIN)
                    return
                }

                var goodsType = "0"
                if (isCombination) {
                    goodsType = "2"
                }
                mPresenter?.demandRecord(goodsType, mGoodsInfoId, mCombinationId)
            }

            R.id.ll_cart -> {
                EventBus.getDefault().post(CloseActivityEvent())
                EventBus.getDefault().post(BackCartEvent())
            }

            R.id.ll_phone -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + C.CUSTOMER_TEL))
                startActivity(dialIntent)
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