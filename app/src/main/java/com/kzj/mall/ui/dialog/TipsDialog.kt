package com.kzj.mall.ui.dialog

import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogTipsBinding

class TipsDialog : BaseDialog<IPresenter, DialogTipsBinding>() {

    companion object {
        fun newInstance(): TipsDialog {
            return TipsDialog()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_tips
    }

    override fun initData() {

    }

}