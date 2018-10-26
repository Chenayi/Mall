package com.kzj.mall.ui.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.PaySuccessAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityPaySuccessBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerPaySuccessComponent
import com.kzj.mall.di.module.PaySuccessModule
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.event.BackHomeEvent
import com.kzj.mall.event.CloseActivityEvent
import com.kzj.mall.mvp.contract.PaySuccessContract
import com.kzj.mall.mvp.presenter.PaySuccessPresenter
import org.greenrobot.eventbus.EventBus

class PaySuccessActivity : BaseActivity<PaySuccessPresenter, ActivityPaySuccessBinding>(), View.OnClickListener, PaySuccessContract.View {
    private var orderId: String? = null
    private var payType: String? = null
    private var orderPrice: String? = null

    private var payAdapter: PaySuccessAdapter? = null
    private var headerView: View? = null
    private var headerView2: View? = null
    private var footerView: View? = null
    private var tvPayType: TextView? = null
    private var tvOrderPrice: TextView? = null
    private var tvSeeOrder: TextView? = null
    private var tvHome: TextView? = null

    override fun getLayoutId() = R.layout.activity_pay_success

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.white)
                ?.statusBarDarkFont(true, 0.5f)
                ?.keyboardEnable(keyboardEnable())
                ?.keyboardMode(getKeyboardMode())
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerPaySuccessComponent.builder()
                .appComponent(appComponent)
                .paySuccessModule(PaySuccessModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        intent?.getStringExtra("orderId")?.let {
            orderId = it
        }

        intent?.getStringExtra("payType")?.let {
            payType = it
        }

        intent?.getStringExtra("orderPrice")?.let {
            orderPrice = it
        }

        payAdapter = PaySuccessAdapter(ArrayList())

        headerView = layoutInflater.inflate(R.layout.header_pay_success, mBinding?.rv?.parent as ViewGroup, false)
        tvPayType = headerView?.findViewById(R.id.tv_pay_type)
        tvOrderPrice = headerView?.findViewById(R.id.tv_order_price)
        tvSeeOrder = headerView?.findViewById(R.id.tv_see_order)
        tvHome = headerView?.findViewById(R.id.tv_home)
        tvPayType?.text = "支付方式：" + payType
        tvOrderPrice?.text = "¥" + orderPrice
        tvSeeOrder?.setOnClickListener(this)
        tvHome?.setOnClickListener(this)

        headerView2 = layoutInflater.inflate(R.layout.item_recommend_text, mBinding?.rv?.parent as ViewGroup, false)
        footerView = layoutInflater.inflate(R.layout.footer_kzj, mBinding?.rv?.parent as ViewGroup, false)

        payAdapter?.addHeaderView(headerView)
        payAdapter?.addHeaderView(headerView2)
        payAdapter?.addFooterView(footerView)

        mBinding?.rv?.layoutManager = GridLayoutManager(this, 2)
        mBinding?.rv?.adapter = payAdapter

        payAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this, GoodsDetailActivity::class.java)
            intent?.putExtra(C.GOODS_INFO_ID, payAdapter?.getItem(position)?.goods_info_id)
            startActivity(intent)
        }

        mPresenter?.loadRecommend(1, C.PAGE_SIZE)
    }

    override fun loadRecommendDatas(recommends: MutableList<HomeRecommendEntity.Data>?) {
        payAdapter?.setNewData(recommends)
        payAdapter?.loadMoreEnd()
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_see_order -> {
                val intent = Intent(this, OrderDetailActivity::class.java)
                intent.putExtra("orderId", orderId)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            R.id.tv_home -> {
                EventBus.getDefault().post(CloseActivityEvent())
                EventBus.getDefault().post(BackHomeEvent())
            }
        }
    }
}