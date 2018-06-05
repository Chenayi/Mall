package com.kzj.mall.base

interface IPresenter {
    /**
     * 做一些初始化工作
     */
    fun onStart()

    /**
     * 做一些销毁工作
     */
    fun onDestory()
}