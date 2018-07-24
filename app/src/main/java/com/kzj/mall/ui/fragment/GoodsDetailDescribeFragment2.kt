package com.kzj.mall.ui.fragment

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailDescribe2Binding

/**
 * 图文描述
 */
class GoodsDetailDescribeFragment2 : BaseFragment<IPresenter, FragmentGoodsDetailDescribe2Binding>() {

    companion object {
        fun newInstance(): GoodsDetailDescribeFragment2 {
            val goodeDetailDescribeFragment = GoodsDetailDescribeFragment2()
            return goodeDetailDescribeFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail_describe2
    }

    override fun initData() {
    }

}