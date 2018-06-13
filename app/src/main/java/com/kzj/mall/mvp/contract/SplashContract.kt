package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView

interface SplashContract {
    interface View : IView {
        fun delayFinish();
    }

    interface Model : IModel {
    }
}