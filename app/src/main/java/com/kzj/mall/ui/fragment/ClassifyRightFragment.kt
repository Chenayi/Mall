package com.kzj.mall.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.ClassifyRightAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentClassifyRightBinding
import com.kzj.mall.utils.LocalDatas

class ClassifyRightFragment : BaseFragment<IPresenter, FragmentClassifyRightBinding>() {
    private var classifyRightAdapter: ClassifyRightAdapter? = null

    companion object {
        fun newInstance(classifyName: String): Fragment {
            var classifyRightFragment = ClassifyRightFragment()
            var bundle = Bundle()
            bundle?.putString("classifyName", classifyName)
            classifyRightFragment?.arguments = bundle
            return classifyRightFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_classify_right
    }

    override fun initData() {
        val classifyName = arguments?.getString("classifyName")

        classifyRightAdapter = ClassifyRightAdapter(R.layout.item_classify_right_content,
                R.layout.item_classify_right_header, LocalDatas.classifyDatas())
        mBinding?.rvClassifyRight?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvClassifyRight?.adapter = classifyRightAdapter
        val headerView = layoutInflater.inflate(R.layout.header_classify_right, mBinding?.rvClassifyRight?.parent as ViewGroup, false)
        classifyRightAdapter?.setHeaderView(headerView)
    }
}