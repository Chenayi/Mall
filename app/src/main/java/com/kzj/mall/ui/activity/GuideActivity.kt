package com.kzj.mall.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityGuideBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.fragment.GuideFragment1
import com.kzj.mall.ui.fragment.GuideFragment2
import com.kzj.mall.ui.fragment.GuideFragment3
import com.kzj.mall.ui.fragment.GuideFragment4

class GuideActivity : BaseActivity<IPresenter, ActivityGuideBinding>() {
    private var fragments: MutableList<Fragment>? = null
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_guide

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
    }

    override fun initData() {
        SPUtils.getInstance().put(C.IS_GUIDED, true)

        fragments = ArrayList<Fragment>()
        fragments?.add(GuideFragment1.newInstance())
        fragments?.add(GuideFragment2.newInstance())
        fragments?.add(GuideFragment3.newInstance())
        fragments?.add(GuideFragment4.newInstance())

        mBinding?.indicator?.setNoSelRes(R.mipmap.k_default)
        mBinding?.indicator?.setSelRes(R.mipmap.k_sel)
        mBinding?.indicator?.setIndicatorWidth(SizeUtils.dp2px(26f))
        mBinding?.indicator?.setIndicatorHeight(SizeUtils.dp2px(26f))
        mBinding?.indicator?.setIndicatorsSize(fragments?.size!! - 1)
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpGuide?.adapter = commomViewPagerAdapter
        mBinding?.vpGuide?.offscreenPageLimit = 3
        mBinding?.vpGuide?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position < fragments?.size!! - 1){
                    mBinding?.indicator?.visibility = View.VISIBLE
                    mBinding?.indicator?.setSelectIndex(position)
                }else{
                    mBinding?.indicator?.visibility = View.GONE
                }
                when (position) {
                    0 -> {
                        mBinding?.ivGuideBg?.setImageResource(R.mipmap.guide_bg1)
                    }
                    1 -> {
                        mBinding?.ivGuideBg?.setImageResource(R.mipmap.guide_bg2)
                    }
                    2 -> {
                        mBinding?.ivGuideBg?.setImageResource(R.mipmap.guide_bg3)
                    }
                    3->{
                        mBinding?.ivGuideBg?.setImageResource(R.color.white)
                    }
                }

                val fragment = fragments?.get(position)
                if (fragment is GuideFragment1){
                    fragment?.startAnim()
                }else if (fragment is GuideFragment2){
                    fragment?.startAnim()
                }else if (fragment is GuideFragment3){
                    fragment?.startAnim()
                }
            }
        })
    }
}