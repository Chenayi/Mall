package com.kzj.mall.ui.fragment

import android.databinding.ViewDataBinding
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentClassifyBinding

class ClassifyFragment : BaseFragment<IPresenter, FragmentClassifyBinding>() {

    companion object {
        fun newInstance(): ClassifyFragment {
            val classifyFragment = ClassifyFragment()
            return classifyFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_classify
    }

    override fun initData() {
    }
}