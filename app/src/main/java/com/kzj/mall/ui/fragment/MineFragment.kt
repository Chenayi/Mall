package com.kzj.mall.ui.fragment

import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentMineBinding
import com.kzj.mall.di.component.AppComponent

class MineFragment : BaseFragment<IPresenter, FragmentMineBinding>() {

    companion object {
        fun newInstance(): MineFragment {
            val mineFragment = MineFragment()
            return mineFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun isImmersionBarEnabled(): Boolean {
        return true
    }

    override fun initImmersionBar() {
        immersionBarColor = R.color.fb
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, immersionBarColor)
                ?.statusBarDarkFont(true,0.5f)
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {
    }
}