package com.kzj.mall.ui.activity

import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMainBinding

class MainActivity : BaseActivity<IPresenter, ActivityMainBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        mBinding?.hello = "hello HaHa ... "
    }
}
