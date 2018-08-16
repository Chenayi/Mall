package com.kzj.mall.ui.activity

import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityPersonInfoBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.event.LogoutEvent
import org.greenrobot.eventbus.EventBus

class PersonInfoActivity : BaseActivity<IPresenter, ActivityPersonInfoBinding>(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_person_info
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
                ?.init()
    }

    override fun initData() {
        mBinding?.tvLogout?.setOnClickListener(this)
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