package com.kzj.mall.ui.activity

import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityPersonInfoBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerPersonInfoComponent
import com.kzj.mall.di.module.PersonInfoModule
import com.kzj.mall.entity.CustomerEntity
import com.kzj.mall.event.LogoutEvent
import com.kzj.mall.mvp.contract.PersonInfoContract
import com.kzj.mall.mvp.presenter.PersonInfoPresenter
import org.greenrobot.eventbus.EventBus

class PersonInfoActivity : BaseActivity<PersonInfoPresenter, ActivityPersonInfoBinding>(), View.OnClickListener
        , PersonInfoContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_person_info
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerPersonInfoComponent.builder()
                .appComponent(appComponent)
                .personInfoModule(PersonInfoModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
                ?.init()
    }

    override fun initData() {
        mBinding?.tvLogout?.setOnClickListener(this)

        mPresenter?.customerInfo()
    }

    override fun showPersonInfo(info: CustomerEntity.CustAllInfo?) {
        info?.customer_img?.let {
            GlideApp.with(this)
                    .load(it)
                    .centerCrop()
                    .into(mBinding?.ivAvatar!!)
        }

        info?.customer_nickname?.let {
            mBinding?.tvNickName?.text = it
        }

        info?.info_mobile?.let {
            mBinding?.tvMobile?.text = it
        }

        info?.info_gender?.let {
            //男
            if (it.equals("1")){
                mBinding?.sexMan?.isChecked = true
            }
            //女
            else if(it?.equals("2")){
                mBinding?.sexWoman?.isChecked = true
            }
            //保密
            else{
                mBinding?.sexNor?.isChecked = true
            }
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_logout -> {
                C.TOKEN = ""
                SPUtils.getInstance()?.put(C.SP_TOKEN, "")
                C.IS_LOGIN = false
                EventBus.getDefault().post(LogoutEvent())
                finish()
            }
        }
    }
}