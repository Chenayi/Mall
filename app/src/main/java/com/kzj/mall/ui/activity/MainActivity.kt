package com.kzj.mall.ui.activity

import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter

class MainActivity : BaseActivity<IPresenter>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
