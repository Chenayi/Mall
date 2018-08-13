package com.kzj.mall.ui.activity

import android.support.v4.app.Fragment
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMyAskAnswerBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.fragment.MyAskAnswerFragment

/**
 * 我的问答
 */
class MyAskAnswerActivity : BaseActivity<IPresenter, ActivityMyAskAnswerBinding>() {
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null

    override fun getLayoutId() = R.layout.activity_my_ask_answer
    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        fragments = ArrayList()
        fragments?.add(MyAskAnswerFragment.newInstance(0))
        fragments?.add(MyAskAnswerFragment.newInstance(1))
        fragments?.add(MyAskAnswerFragment.newInstance(2))
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpAsk?.adapter = commomViewPagerAdapter
    }
}