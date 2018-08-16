package com.kzj.mall.base

interface IView {
    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 出错
     */
    fun onError(code: Int, msg: String?)
}