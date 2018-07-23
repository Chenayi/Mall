package com.kzj.mall.ui.fragment

import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailBinding

class GoodsDetailFragment : BaseFragment<IPresenter, FragmentGoodsDetailBinding>() {

    companion object {
        fun newInstance(): GoodsDetailFragment {
            val goodsDetailFragment = GoodsDetailFragment()
            return goodsDetailFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail
    }

    override fun initData() {

        LogUtils.e("initData ... ")
    }
}