package com.kzj.mall.ui.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.ClassifyLeftAdapter
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentClassifyBinding
import com.kzj.mall.utils.LocalDatas

class ClassifyFragment : BaseFragment<IPresenter, FragmentClassifyBinding>() {
    private var classifyleftAdapter: ClassifyLeftAdapter? = null
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null

    companion object {
        fun newInstance(): ClassifyFragment {
            val classifyFragment = ClassifyFragment()
            return classifyFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_classify
    }

    override fun isImmersionBarEnabled(): Boolean {
        return true
    }

    override fun initImmersionBar() {
        immersionBarColor = R.color.colorPrimary
        super.initImmersionBar()
    }

    override fun initData() {
        initLeft()
        initRight()
    }

    fun initLeft() {
        classifyleftAdapter = ClassifyLeftAdapter(LocalDatas.classifyLeftDatas())
        mBinding?.rlClassify?.layoutManager = LinearLayoutManager(context)
        mBinding?.rlClassify?.adapter = classifyleftAdapter

        classifyleftAdapter?.setOnItemClickListener { adapter, view, position ->
            val data = classifyleftAdapter?.data?.get(position)
            if (data?.isChoose == false) {
                val size = classifyleftAdapter?.data?.size
                size?.let {
                    for (i in 0 until it) {
                        val get = classifyleftAdapter?.data?.get(i)
                        if (get?.isChoose == true) {
                            get?.isChoose = false
                            break
                        }
                    }
                }

                data?.isChoose = true

                classifyleftAdapter?.notifyDataSetChanged()
                mBinding?.vpClassify?.setCurrentItem(position, false)
            }
        }
    }

    fun initRight() {
        val size = classifyleftAdapter?.data?.size
        size?.let {
            var fragments = ArrayList<Fragment>()
            for (i in 0 until it) {
                val name = classifyleftAdapter?.data?.get(i)?.name
                fragments.add(ClassifyRightFragment.newInstance(name!!))
            }
            commomViewPagerAdapter = CommomViewPagerAdapter(childFragmentManager, fragments)
            mBinding?.vpClassify?.adapter = commomViewPagerAdapter
            mBinding?.vpClassify?.offscreenPageLimit = it
        }
    }
}