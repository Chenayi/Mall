package com.kzj.mall.ui.activity

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.AskAnswerDetailAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityAskAnswerDetailBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerMyAskAnswerDetailComponent
import com.kzj.mall.di.module.MyAskAnswerDetailModule
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import com.kzj.mall.mvp.contract.MyAskAnswerDetailContract
import com.kzj.mall.mvp.presenter.MyAskAnswerDetailPresenter
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.widget.CenterAlignImageSpan

class MyAskAnswerDetailActivity : BaseActivity<MyAskAnswerDetailPresenter, ActivityAskAnswerDetailBinding>(), MyAskAnswerDetailContract.View {
    private var qId: String? = null

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
        intent?.getStringExtra("qId")?.let {
            qId = it
        }


        mPresenter?.interlucationInfo(qId)
    }

    override fun myAskAnswer(askAnswerDetailEntity: AskAnswerDetailEntity?) {

        mBinding?.tvSex?.text = "性别："+askAnswerDetailEntity?.interlocution?.userSex
        mBinding?.tvAge?.text = "年龄："+askAnswerDetailEntity?.interlocution?.userAge
        mBinding?.tvAskTime?.text = TimeUtils.millis2String(askAnswerDetailEntity?.interlocution?.qAddTime!!)


        val sp = SpannableString("  "+askAnswerDetailEntity?.interlocution?.qName)
        val drawable = ContextCompat.getDrawable(this, R.mipmap.icon_ask);
        drawable?.setBounds(0, 0, drawable?.getMinimumWidth(), drawable.getMinimumHeight());
        val imageSpan = CenterAlignImageSpan(drawable)
        sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
        mBinding?.tvAskContent?.text = sp
        mBinding?.tvCatName?.text = askAnswerDetailEntity?.interlocution?.cat?.catName


        if (askAnswerDetailEntity?.interlocution?.qAnswer != null && askAnswerDetailEntity?.interlocution?.qAnswerTime != null) {
            mBinding?.llAnswer?.visibility = View.VISIBLE
            mBinding?.tvAnswerTime?.text = TimeUtils.millis2String(askAnswerDetailEntity?.interlocution?.qAnswerTime!!)

            val sp = SpannableString("  "+askAnswerDetailEntity?.interlocution?.qAnswer)
            val drawable = ContextCompat.getDrawable(this, R.mipmap.icon_answer);
            drawable?.setBounds(0, 0, drawable?.getMinimumWidth(), drawable.getMinimumHeight());
            val imageSpan = CenterAlignImageSpan(drawable)
            sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
            mBinding?.tvAnswerContent?.text = sp
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
}