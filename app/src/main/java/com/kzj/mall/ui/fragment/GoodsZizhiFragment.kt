package com.kzj.mall.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsZizhiBinding
import com.kzj.mall.di.component.AppComponent

class GoodsZizhiFragment : BaseFragment<IPresenter, FragmentGoodsZizhiBinding>() {
    private var barHeight = 0
    companion object {
        fun newInstance(barHeight: Int): Fragment {
            val goodsZizhiFragment = GoodsZizhiFragment()
            var b = Bundle()
            b.putInt("barHeight", barHeight)
            goodsZizhiFragment.arguments = b
            return goodsZizhiFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_zizhi
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val margin =  SizeUtils.dp2px(10f)
        arguments?.getInt("barHeight")?.let {
            barHeight = it +margin
        }
        mBinding?.llZizhiRoot?.setPadding(margin,barHeight,margin,margin)
        return view
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {
        GlideApp.with(this)
                .load(R.mipmap.zhengshu1)
                .centerCrop()
                .into(mBinding?.ivZizhi1!!)

        GlideApp.with(this)
                .load(R.mipmap.zhengshu2)
                .centerCrop()
                .into(mBinding?.ivZizhi2!!)

        GlideApp.with(this)
                .load(R.mipmap.zhengshu3)
                .centerCrop()
                .into(mBinding?.ivZizhi3!!)

        GlideApp.with(this)
                .load(R.mipmap.zhengshu4)
                .centerCrop()
                .into(mBinding?.ivZizhi4!!)
    }
}