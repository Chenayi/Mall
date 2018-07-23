package com.kzj.mall.ui.fragment

import android.support.v4.app.Fragment
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsZizhiBinding

class GoodsZizhiFragment : BaseFragment<IPresenter, FragmentGoodsZizhiBinding>() {

    companion object {
        fun newInstance(): Fragment {
            val goodsZizhiFragment = GoodsZizhiFragment()
            return goodsZizhiFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_zizhi
    }

    override fun initData() {
    }
}