package com.kzj.mall.ui.fragment

import android.support.v4.app.Fragment
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailBinding

class GoodsDetailFragment : BaseFragment<IPresenter, FragmentGoodsDetailBinding>() {

    companion object {
        fun newInstance(): Fragment {
            val goodsDetailFragment = GoodsDetailFragment()
            return goodsDetailFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail
    }

    override fun initData() {
    }
}