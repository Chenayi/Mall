package com.kzj.mall.ui.fragment.home

import android.content.Context
import android.support.v4.app.Fragment
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentHomeBinding
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import android.graphics.Color
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.adapter.HomeNavigatorTitleView
import com.kzj.mall.ui.dialog.HomeTabClassifyPop
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator


class HomeFragment : BaseFragment<IPresenter, FragmentHomeBinding>(), View.OnClickListener {
    private var mCommomViewPagerAdapter: CommomViewPagerAdapter? = null
    private val mTitles: Array<String> = arrayOf("首页", "男科", "早泄", "温阳补肾", "脱发少发")
    private var mFragments: MutableList<Fragment>? = null

    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            return homeFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        mFragments = ArrayList()
        mBinding?.vpHome?.offscreenPageLimit = mTitles?.size - 1
        mFragments?.let {
            it?.add(HomeChildFragment.newInstance())
            it?.add(AndrologyFragment.newInstance())
            it?.add(OtherFragment.newInstance())
            it?.add(OtherFragment.newInstance())
            it?.add(OtherFragment.newInstance())
            mCommomViewPagerAdapter = CommomViewPagerAdapter(childFragmentManager, it)
            mBinding?.vpHome?.adapter = mCommomViewPagerAdapter
        }


        var commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = HomeNavigatorTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.parseColor("#EBFFD9")
                colorTransitionPagerTitleView.selectedColor = Color.WHITE
                colorTransitionPagerTitleView.textSize = 13f
                colorTransitionPagerTitleView.setPadding(SizeUtils.dp2px(12f), 0, SizeUtils.dp2px(12f), 0)
                colorTransitionPagerTitleView.setText(mTitles[index])
                colorTransitionPagerTitleView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        mBinding?.vpHome?.setCurrentItem(index, false)
                    }

                })
                return colorTransitionPagerTitleView
            }

            override fun getCount(): Int {
                return mTitles?.size
            }

            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.setColors(Color.WHITE)
                indicator.lineWidth = SizeUtils.dp2px(13f).toFloat()
                return indicator
            }

        }

        mBinding?.magicIndicator?.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding?.magicIndicator, mBinding?.vpHome);


        mBinding?.ivClassify?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_classify -> {
            }
        }
    }
}