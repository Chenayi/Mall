package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.AskAnswerDetailAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityAskAnswerDetailBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerMyAskAnswerDetailComponent
import com.kzj.mall.di.module.MyAskAnswerDetailModule
import com.kzj.mall.mvp.contract.MyAskAnswerDetailContract
import com.kzj.mall.mvp.presenter.MyAskAnswerDetailPresenter
import com.kzj.mall.utils.LocalDatas

class MyAskAnswerDetailActivity : BaseActivity<MyAskAnswerDetailPresenter, ActivityAskAnswerDetailBinding>(), MyAskAnswerDetailContract.View {
    private var askAnswerDetailAdapter: AskAnswerDetailAdapter? = null


    override fun getLayoutId() = R.layout.activity_ask_answer_detail
    override fun setupComponent(appComponent: AppComponent?) {
        DaggerMyAskAnswerDetailComponent.builder()
                .appComponent(appComponent)
                .myAskAnswerDetailModule(MyAskAnswerDetailModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
                ?.init()
    }

    override fun initData() {
        askAnswerDetailAdapter = AskAnswerDetailAdapter(LocalDatas.askDetails())
        askAnswerDetailAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp, mBinding?.rvAskDetail?.parent as ViewGroup, false))
        mBinding?.rvAskDetail?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvAskDetail?.adapter = askAnswerDetailAdapter
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
}