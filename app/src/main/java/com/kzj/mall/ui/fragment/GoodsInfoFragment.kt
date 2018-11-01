package com.kzj.mall.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.*
import android.view.View
import android.widget.*
import cn.bingoogolapple.bgabanner.BGABanner
import com.blankj.utilcode.util.ScreenUtils
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsInfoBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.event.*
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.kzj.mall.ui.activity.login.LoginActivity
import com.kzj.mall.ui.dialog.GoodsSpecDialog
import com.kzj.mall.widget.ObservableScrollView
import com.kzj.mall.widget.SlideDetailsLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import com.kzj.mall.ui.activity.PhotosActivity
import com.kzj.mall.ui.dialog.ServiceNoteDialog
import com.kzj.mall.utils.PriceUtils
import com.kzj.mall.widget.CenterAlignImageSpan


class GoodsInfoFragment : BaseFragment<IPresenter, FragmentGoodsInfoBinding>(), View.OnClickListener {
    /**
     * 商品价格
     */
    private var tvGoodsPrice: TextView? = null
    /**
     * 商品市场价
     */
    private var tvGoodsMarketPrice: TextView? = null
    /**
     * 月销量
     */
    private var tvMonthSalesNum: TextView? = null
    /**
     * 关注区域
     */
    private var llFollow: LinearLayout? = null
    private var ivFollow: ImageView? = null
    private var tvFollow: TextView? = null

    /**
     * 商品名称
     */
    private var tvGoodsName: TextView? = null

    /**
     * 商品描述
     */
    private var tvGoodsInfoSubtitle: TextView? = null

    /**
     * 已选规格
     */
    private var tvCheckSpec: TextView? = null

    /**
     * 服务说明
     */
    private var llServiceNote: LinearLayout? = null

    /**
     * 特价
     */
    private var tvTejie: TextView? = null


    private var barHeight = 0
    private var bannerHeight = 0
    private var alpha = 0.0f
    private var isLoadDetailFragment = false

    /**
     * 商品数量
     */
    private var mGoodsNum = 1

    /**
     * 是否添加关注
     */
    private var isFollow = false

    private var goodsDetailEntity: GoodsDetailEntity? = null


    private var advDatas: MutableList<String>? = null

    /**
     * 疗程套装选中位置
     */
    private var groupPosition = 0

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


    override fun enableEventBus() = true

    override fun initData() {
        arguments?.getInt("barHeight")?.let {
            barHeight = it
        }

        tvTejie = view?.findViewById(R.id.tv_tejie)
        tvGoodsPrice = view?.findViewById(R.id.tv_goods_price)
        tvGoodsMarketPrice = view?.findViewById(R.id.tv_goods_market_price)
        tvMonthSalesNum = view?.findViewById(R.id.tv_month_sales_num)
        tvFollow = view?.findViewById(R.id.tv_follow)
        ivFollow = view?.findViewById(R.id.iv_follow)
        llFollow = view?.findViewById(R.id.ll_follow)
        llFollow?.isEnabled = false
        llFollow?.setOnClickListener(this)
        tvGoodsName = view?.findViewById(R.id.tv_goods_name)
        tvGoodsInfoSubtitle = view?.findViewById(R.id.tv_goods_info_subtitle)
        tvGoodsMarketPrice = view?.findViewById(R.id.tv_goods_market_price)
        tvCheckSpec = view?.findViewById(R.id.tv_check_spec)

        llServiceNote = view?.findViewById(R.id.ll_service_note)
        llServiceNote?.setOnClickListener(this)


        mBinding?.fabUpSlide?.hide()

        //banner
        initBanner()

        mBinding?.slideDetailsLayout?.setOnSlideDetailsListener(object : SlideDetailsLayout.OnSlideDetailsListener {
            override fun onStatucChanged(status: SlideDetailsLayout.Status?) {
                if (status == SlideDetailsLayout.Status.CLOSE) {
                    EventBus.getDefault().post(ScrollChangedEvent(status, alpha))
                    mBinding?.fabUpSlide?.hide()
                    mBinding?.ivArrow?.setImageResource(R.mipmap.detail_down)
                    mBinding?.tvDetailTips?.text = "上拉查看图文详情"
                } else if (status == SlideDetailsLayout.Status.OPEN) {
                    EventBus.getDefault().post(ScrollChangedEvent(status, 1.0f))
                    mBinding?.ivArrow?.setImageResource(R.mipmap.detail_up)
                    mBinding?.fabUpSlide?.show()
                    mBinding?.tvDetailTips?.text = "下拉收起图文详情"

                    if (findChildFragment(GoodsDetailBottomFragment::class.java) == null) {
                        loadRootFragment(R.id.fl_content, GoodsDetailBottomFragment.newInstance(barHeight, goodsDetailEntity))
                        isLoadDetailFragment = true
                    }
                }
            }
        })

        mBinding?.osv?.setOnScrollChangedListener(object : ObservableScrollView.OnScrollChangedListener {
            override fun onScrollChanged(who: ObservableScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                val i = y.toFloat() / bannerHeight.toFloat()
                alpha = if (i < 1f) i else 1f
                EventBus.getDefault().post(ScrollChangedEvent(SlideDetailsLayout.Status.CLOSE, alpha))
            }
        })


        mBinding?.detailSpec?.isEnabled = false
        mBinding?.ivNumBg?.alpha = 0.3f
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
                    .placeholder(R.mipmap.goods_default)
                    .centerCrop()
                    .dontAnimate()
                    .into(itemView)

            itemView.setOnClickListener {
                advDatas?.let {
                    val intent = Intent(context, PhotosActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putStringArrayListExtra("advDatas", it as ArrayList<String>)
                    intent.putExtra("position", position)
                    startActivity(intent)
                }
            }
        })
        mBinding?.banner?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBinding?.tvBannerNum?.setText((position + 1).toString())
                if (position == 0) {
                    mBinding?.rlNotice?.visibility = View.VISIBLE
                } else {
                    mBinding?.rlNotice?.visibility = View.INVISIBLE
                }
            }

        })
        mBinding?.banner?.setData(ArrayList(), null)
    }

    /**
     * 商品数量
     */
    @Subscribe
    fun goodsNumChange(goodsNumChangeEvent: GoodsNumChangeEvent) {
        mGoodsNum = goodsNumChangeEvent?.goodsNum
    }

    /**
     * 更新关注状态
     */
    fun updateFollowStatus(goodsDetailEntity: GoodsDetailEntity?) {
        goodsDetailEntity?.let {
            isFollow = it?.is_follow?.equals("1") == true
            //是否关注
            if (it?.is_follow?.equals("1") == true) {
                ivFollow?.setImageResource(R.mipmap.saved)
                tvFollow?.setText("已关注")
            } else {
                ivFollow?.setImageResource(R.mipmap.sc)
                tvFollow?.setText("关注")
            }
        }
    }

    /**
     * 更新数据
     */
    fun updateDatas(goodsDetailEntity: GoodsDetailEntity?) {
        goodsDetailEntity?.let {
            this@GoodsInfoFragment.goodsDetailEntity = it
            //是否关注
            llFollow?.isEnabled = true
            isFollow = it?.is_follow?.equals("1") == true
            if (it?.is_follow?.equals("1") == true) {
                ivFollow?.setImageResource(R.mipmap.saved)
                tvFollow?.setText("已关注")
            } else {
                ivFollow?.setImageResource(R.mipmap.sc)
                tvFollow?.setText("关注")
            }

            //活动
            it?.promotionalAd?.wap_promotional_title?.let {
                if (!TextUtils.isEmpty(it)) {
                    mBinding?.tvFunction?.setText(it)
                    mBinding?.rlNotice?.visibility = View.VISIBLE
                } else {
                    mBinding?.rlNotice?.visibility = View.GONE
                }
            }

            //产品信息
            it?.gn?.let {
                //图片
                val goodsImgs = it?.goodsImgs
                advDatas = ArrayList<String>()
                for (i in 0 until goodsImgs?.size!!) {
                    advDatas?.add(goodsImgs?.get(i).imageArtworkName!!)
                }
                mBinding?.banner?.setData(advDatas, null)
                if (advDatas?.size!! > 0) {
                    mBinding?.tvBannerNum?.setText("1")
                    mBinding?.tvBannerCount?.text = " / " + advDatas?.size
                }
                //价格
                val goodsPrice = "¥" + it?.goodsPrice
                tvGoodsPrice?.setText(PriceUtils.format(goodsPrice))
                tvGoodsMarketPrice?.setText("¥" + it?.goodsMarketPrice)
                tvGoodsMarketPrice?.getPaint()?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvMonthSalesNum?.setText("已销售" + it?.goodsSole + "件")

                if (it?.goodsPrice?.toFloat()!! < it?.goodsMarketPrice?.toFloat()!!) {
                    tvTejie?.visibility = View.VISIBLE
                } else {
                    tvTejie?.visibility = View.GONE
                }

                //处方
                if (it.goodsType.equals("0")) {

                    val sp = SpannableString("  " + it?.goodsName)
                    val drawable = ContextCompat.getDrawable(context!!, R.mipmap.rx_red);
                    drawable?.setBounds(0, 0, drawable?.getMinimumWidth(), drawable.getMinimumHeight());
                    val imageSpan = CenterAlignImageSpan(drawable)
                    sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
                    tvGoodsName?.setText(sp)


                    mBinding?.llChufang?.visibility = View.VISIBLE
                    var t1 = "如需协助可在线咨询药师或拨打热线 "
                    var t2 = C.CUSTOMER_TEL

                    val spanText = SpannableString(t1 + t2)
                    spanText.setSpan(object : ClickableSpan() {

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.color = Color.parseColor("#004098")       //设置文件颜色
                            ds.isUnderlineText = true      //设置下划线
                        }

                        override fun onClick(view: View) {
                            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + t2))
                            startActivity(dialIntent)
                        }
                    }, t1.length, (t1 + t2).length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    mBinding?.tvRxTips?.text = spanText
                    mBinding?.tvRxTips?.setMovementMethod(LinkMovementMethod.getInstance());
                }

                //红色OTC
                else if (it.goodsType.equals("1")) {

                    val sp = SpannableString("  " + it?.goodsName)
                    val drawable = ContextCompat.getDrawable(context!!, R.mipmap.otc_red);
                    drawable?.setBounds(0, 0, drawable?.getMinimumWidth(), drawable.getMinimumHeight());
                    val imageSpan = CenterAlignImageSpan(drawable)
                    sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
                    tvGoodsName?.setText(sp)

                    mBinding?.llChufang?.visibility = View.GONE
                }
                //绿色OTC
                else {

                    val sp = SpannableString("  " + it?.goodsName)
                    val drawable = ContextCompat.getDrawable(context!!, R.mipmap.otc_green);
                    drawable?.setBounds(0, 0, drawable?.getMinimumWidth(), drawable.getMinimumHeight());
                    val imageSpan = CenterAlignImageSpan(drawable)
                    sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
                    tvGoodsName?.setText(sp)

                    mBinding?.llChufang?.visibility = View.GONE
                }
            }

            //商品信息
            it?.gin?.let {
                if (!TextUtils.isEmpty(it?.goods_info_subtitle)) {
                    tvGoodsInfoSubtitle?.visibility = View.VISIBLE
                    tvGoodsInfoSubtitle?.setText(Html.fromHtml(it?.goods_info_subtitle))
                } else {
                    tvGoodsInfoSubtitle?.visibility = View.GONE
                }
            }

            //规格
            it?.gn?.goodsSpec?.let {
                mBinding?.detailSpec?.isEnabled = true
                tvCheckSpec?.setText(it + "；一盒标准装")
            }

            //套餐
            it?.combinationList?.let {
                if (it?.size > 0) {
                    mBinding?.detailGroup?.visibility = View.VISIBLE

                    var isShowAddCart = false

                    //非处方药 不缺货才显示加入购物车
                    if (goodsDetailEntity?.gn?.goodsType?.equals("0") == false && goodsDetailEntity?.gn?.goodsStock!! > 0) {
                        isShowAddCart = true
                    }

                    mBinding?.goodsGroupView?.setNewDatas(isShowAddCart, it)
                }
            }

            this.goodsDetailEntity = goodsDetailEntity
        }
        mBinding?.osv?.scrollTo(0, 0)
    }

    /**
     * 规格弹窗
     */
    fun showSpecDialog() {
        GoodsSpecDialog.newInstance(mGoodsNum, groupPosition, goodsDetailEntity)
                .setShowBottom(true)
                .show(childFragmentManager)
    }

    /**
     * 疗程切换
     */
    @Subscribe
    fun packageListChange(packageListEvent: PackageListEvent) {
        groupPosition = packageListEvent?.position
        tvGoodsPrice?.setText(PriceUtils.format("¥${packageListEvent.goodsPrice}"))
        tvGoodsMarketPrice?.setText("¥" + packageListEvent?.goodsMarketPrice)
        tvGoodsMarketPrice?.getPaint()?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        //规格
        goodsDetailEntity?.gn?.goodsSpec?.let {
            mBinding?.detailSpec?.isEnabled = true
            tvCheckSpec?.setText(it + "；" + packageListEvent?.combinaName)
        }
    }

    /**
     * 套餐切换
     */
    @Subscribe
    fun combinationChange(combinationEvent: CombinationEvent) {
        val combination = combinationEvent?.combinationList
        groupPosition = combinationEvent?.position
        tvGoodsPrice?.setText(PriceUtils.format("¥${combination?.combination_price}"))
        tvGoodsMarketPrice?.setText("¥" + combination?.sumOldPrice)
        tvGoodsMarketPrice?.getPaint()?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        //规格
        goodsDetailEntity?.gn?.goodsSpec?.let {
            mBinding?.detailSpec?.isEnabled = true
            tvCheckSpec?.setText(it + "；" + combination?.combination_name)
        }
    }

    /**
     * 规格切换
     */
//    @Subscribe
//    fun specChange(goodSpecChangeEvent: GoodSpecChangeEvent) {
//        updateDatas(goodSpecChangeEvent?.goodsDetailEntity)
//    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.detail_spec -> {
                showSpecDialog()
            }
            R.id.fab_up_slide -> {
                mBinding?.slideDetailsLayout?.smoothClose(true)
            }
            R.id.ll_follow -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    return
                }
                addOrCancelFollow()
            }
            R.id.ll_service_note -> {
                ServiceNoteDialog.newInstance()
                        .setShowBottom(true)
                        .show(childFragmentManager)
            }
        }
    }

    private fun addOrCancelFollow() {
        llFollow?.isEnabled = false
        (activity as GoodsDetailActivity).addOrCancelFollow(isFollow)
    }

    fun colllectSuccess() {
        tvFollow?.setText("已关注")
        ivFollow?.setImageResource(R.mipmap.saved)
        llFollow?.isEnabled = true
        this@GoodsInfoFragment.isFollow = true
    }

    fun cancelCollectSuccess() {
        tvFollow?.setText("关注")
        ivFollow?.setImageResource(R.mipmap.sc)
        llFollow?.isEnabled = true
        this@GoodsInfoFragment.isFollow = false
    }

    fun onBackClick() {
        if (mBinding?.slideDetailsLayout?.status == SlideDetailsLayout.Status.OPEN) {
            mBinding?.slideDetailsLayout?.smoothClose(true)
        } else {
            activity?.finish()
        }
    }
}