package com.kzj.mall.ui.fragment

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.ClassifyLeftAdapter
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.databinding.FragmentClassifyBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerClassifyLeftComponent
import com.kzj.mall.di.module.ClassifyLeftModule
import com.kzj.mall.entity.ClassifyLeftEntity
import com.kzj.mall.mvp.contract.ClassifyLeftContract
import com.kzj.mall.mvp.presenter.ClassifyLeftPresenter
import com.kzj.mall.ui.activity.SearchActivity

/**
 * 分类模块
 */
class ClassifyFragment : BaseFragment<ClassifyLeftPresenter, FragmentClassifyBinding>(), ClassifyLeftContract.View,View.OnClickListener {
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

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerClassifyLeftComponent.builder()
                .appComponent(appComponent)
                .classifyLeftModule(ClassifyLeftModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindowsInt(true, ContextCompat.getColor(context!!,R.color.colorPrimary))
                ?.statusBarDarkFont(false)
                ?.init()
    }


    override fun initData() {
        mBinding?.vpClassify?.setNoScroll(true)
        initLeft()
        mPresenter?.requestClassifyLeft()

        mBinding?.rlSearch?.setOnClickListener(this)
    }

    fun initLeft() {
        classifyleftAdapter = ClassifyLeftAdapter(ArrayList())
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
                val cid = classifyleftAdapter?.data?.get(i)?.cat_id
                fragments.add(ClassifyRightFragment.newInstance(cid!!))
            }
            commomViewPagerAdapter = CommomViewPagerAdapter(childFragmentManager, fragments)
            mBinding?.vpClassify?.adapter = commomViewPagerAdapter
            mBinding?.vpClassify?.offscreenPageLimit = it
        }
    }

    override fun requestClassifyLeftSuccess(classifyLeftEntity: ClassifyLeftEntity?) {
        val cateList = classifyLeftEntity?.cateList
        cateList?.get(0)?.isChoose = true
        classifyleftAdapter?.setNewData(cateList)
        initRight()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_search->{
                val intent = Intent(context,SearchActivity::class.java)
                intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}