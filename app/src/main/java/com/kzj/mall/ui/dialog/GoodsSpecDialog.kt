package com.kzj.mall.ui.dialog

import com.blankj.utilcode.util.ScreenUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogGoodsSpecBinding
import com.kzj.mall.di.component.AppComponent

class GoodsSpecDialog : BaseDialog<IPresenter, DialogGoodsSpecBinding>() {

    companion object {
        fun newInstance(): GoodsSpecDialog {
            val goodsSpecDialog = GoodsSpecDialog()
            return goodsSpecDialog
        }
    }

    override fun initData() {
        mBinding?.rlRoot?.layoutParams?.height = (ScreenUtils.getScreenHeight() * 0.65f).toInt()
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_goods_spec
    }

}