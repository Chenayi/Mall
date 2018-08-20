package com.kzj.mall.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.ClassifyRightAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentClassifyRightBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerClassifyRightComponent
import com.kzj.mall.di.module.ClassifyRightModule
import com.kzj.mall.entity.ClassifyRightEntity
import com.kzj.mall.mvp.contract.ClassifyRightContract
import com.kzj.mall.mvp.presenter.ClassifyRightPresenter
import com.kzj.mall.utils.LocalDatas

class ClassifyRightFragment : BaseFragment<ClassifyRightPresenter, FragmentClassifyRightBinding>(),ClassifyRightContract.View {
    private var classifyRightAdapter: ClassifyRightAdapter? = null
    private var cid:Int?=null

    companion object {
        fun newInstance(cid: Int): Fragment {
            var classifyRightFragment = ClassifyRightFragment()
            var bundle = Bundle()
            bundle?.putInt("cid", cid)
            classifyRightFragment?.arguments = bundle
            return classifyRightFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_classify_right
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerClassifyRightComponent.builder()
                .appComponent(appComponent)
                .classifyRightModule(ClassifyRightModule(this))
                .build()
                .inject(this)
    }


    override fun initData() {
        cid = arguments?.getInt("cid")

        classifyRightAdapter = ClassifyRightAdapter(R.layout.item_classify_right_content,
                R.layout.item_classify_right_header, LocalDatas.classifyDatas())
        mBinding?.rvClassifyRight?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvClassifyRight?.adapter = classifyRightAdapter
        val headerView = layoutInflater.inflate(R.layout.header_classify_right, mBinding?.rvClassifyRight?.parent as ViewGroup, false)
        classifyRightAdapter?.setHeaderView(headerView)


        mPresenter?.requestClassiftRight(cid)
    }

    override fun requestClassifyRightSuccess(classifyRightEntity: ClassifyRightEntity?) {
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }
}