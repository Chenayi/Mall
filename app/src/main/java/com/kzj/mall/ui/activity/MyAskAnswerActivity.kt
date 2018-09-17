package com.kzj.mall.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMyAskAnswerBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.fragment.MyAskAnswerFragment
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * 我的问答
 */
class MyAskAnswerActivity : BaseActivity<IPresenter, ActivityMyAskAnswerBinding>() {
    private val mTitles: Array<String> = arrayOf("全部", "待回复", "已回复")
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null

    override fun getLayoutId() = R.layout.activity_my_ask_answer
    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        fragments = ArrayList()
        fragments?.add(MyAskAnswerFragment.newInstance("0"))
        fragments?.add(MyAskAnswerFragment.newInstance("2"))
        fragments?.add(MyAskAnswerFragment.newInstance("1"))
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpAsk?.adapter = commomViewPagerAdapter
        mBinding?.vpAsk?.offscreenPageLimit = mTitles?.size

        var commonNavigator = CommonNavigator(applicationContext)
        commonNavigator?.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(applicationContext)
                colorTransitionPagerTitleView.normalColor = Color.parseColor("#6A6E75")
                colorTransitionPagerTitleView.selectedColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                colorTransitionPagerTitleView.textSize = 15f
                colorTransitionPagerTitleView.setPadding(SizeUtils.dp2px(10f), 0, SizeUtils.dp2px(10f), 0)
                colorTransitionPagerTitleView.setText(mTitles[index])
                colorTransitionPagerTitleView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        mBinding?.vpAsk?.currentItem = index
                    }
                })
                return colorTransitionPagerTitleView
            }

            override fun getCount(): Int {
                return mTitles?.size
            }

            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(applicationContext)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.setColors(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                indicator.lineWidth = SizeUtils.dp2px(23f).toFloat()
                indicator.lineHeight = SizeUtils.dp2px(3f).toFloat()
                return indicator
            }


            override fun getTitleWeight(context: Context?, index: Int): Float {
                return 1.0f
            }
        }

        mBinding?.magicIndicator?.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding?.magicIndicator, mBinding?.vpAsk);

        mBinding?.ivAsk?.visibility =View.GONE
        mBinding?.ivAsk?.setOnClickListener {
            startActivity(Intent(this,CreateAskAnswerActivity::class.java))
        }
    }
}