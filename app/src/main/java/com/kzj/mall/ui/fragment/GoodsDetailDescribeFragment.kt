package com.kzj.mall.ui.fragment

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailDescribeBinding

/**
 * 图文描述
 */
class GoodsDetailDescribeFragment : BaseFragment<IPresenter, FragmentGoodsDetailDescribeBinding>() {

    companion object {
        fun newInstance(): GoodsDetailDescribeFragment {
            val goodeDetailDescribeFragment = GoodsDetailDescribeFragment()
            return goodeDetailDescribeFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail_describe
    }

    override fun initData() {
    }

}