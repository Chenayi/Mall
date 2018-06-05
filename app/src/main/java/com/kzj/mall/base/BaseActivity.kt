package com.kzj.mall.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<P : IPresenter, D : ViewDataBinding> : AppCompatActivity() {
    @Inject
    lateinit var mPresenter: P

    protected var mBinding: D? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
}