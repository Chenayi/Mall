package com.kzj.mall.ui.fragment

import android.os.Bundle
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentMyCollectGoodsBinding
import com.kzj.mall.di.component.AppComponent

class MyCollectFragment : BaseFragment<IPresenter, FragmentMyCollectGoodsBinding>() {

    companion object {
        fun newInstance(): MyCollectFragment {
            val myCollectFragment = MyCollectFragment()
            val b = Bundle()
            myCollectFragment?.arguments = b
            return myCollectFragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_my_collect_goods

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}