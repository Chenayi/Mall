package com.kzj.mall.ui.fragment

import android.widget.RelativeLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
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
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {
        val layoutParams = mBinding?.ivMsg?.layoutParams as RelativeLayout.LayoutParams
        layoutParams.rightMargin = SizeUtils.dp2px(14f)
        layoutParams.topMargin = BarUtils.getStatusBarHeight() + SizeUtils.dp2px(14f)
        mBinding?.ivMsg?.requestLayout()
    }
}