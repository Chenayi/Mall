package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.JsonBean

interface CreateAddressContract {
    interface View : IView {
        fun retundArea(options1Items: MutableList<String>, options2Items: MutableList<MutableList<String>>, options3Items: MutableList<MutableList<MutableList<String>>>)
    }

    interface Model : IModel {
    }
}