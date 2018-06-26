package com.kzj.mall.ui.fragment

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentHomeChildBinding

class HomeChildFragment : BaseFragment<IPresenter,FragmentHomeChildBinding>() {

    companion object {
        fun newInstance():HomeChildFragment{
            val homeChildFragment = HomeChildFragment()
            return homeChildFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_child
    }

    override fun initData() {
        var banners = ArrayList<String>()
        for (i in 0 until 4){
            banners.add("")
        }
        mBinding?.homeBanner?.setBanners(banners)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        mBinding?.homeBanner?.start()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        mBinding?.homeBanner?.pause()
    }
}