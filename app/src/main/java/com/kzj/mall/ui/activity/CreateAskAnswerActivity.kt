package com.kzj.mall.ui.activity

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityCreateAskAnswerBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerCreateAskAnswerComponent
import com.kzj.mall.di.module.CreateAskAnswerModule
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import com.kzj.mall.mvp.contract.CreateAskAnswerContract
import com.kzj.mall.mvp.presenter.CreateAskAnswerPresenter
import com.kzj.mall.ui.dialog.AskAnswerTypeDialog

class CreateAskAnswerActivity : BaseActivity<CreateAskAnswerPresenter, ActivityCreateAskAnswerBinding>(), View.OnClickListener, CreateAskAnswerContract.View {
    override fun getLayoutId() = R.layout.activity_create_ask_answer

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerCreateAskAnswerComponent.builder()
                .appComponent(appComponent)
                .createAskAnswerModule(CreateAskAnswerModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        mBinding?.rlType?.setOnClickListener(this)
        mBinding?.tvSubmit?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_type -> {
                AskAnswerTypeDialog.newInstance(ArrayList())
                        .show(supportFragmentManager)
            }

            R.id.tv_submit -> {

            }
        }
    }

    override fun showInterlucationType(types: MutableList<AskAnswerTypeEntity.CatList>?) {

    }

    override fun saceInterlucationSuccess() {
        finish()
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