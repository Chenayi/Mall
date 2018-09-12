package com.kzj.mall.ui.activity

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMyCollectGoodsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.fragment.MyCollectFragment
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class MyCollectGoodsActivity:BaseActivity<IPresenter,ActivityMyCollectGoodsBinding>() {
    private val mTitles: Array<String> = arrayOf("OTC普药", "处方药")

    private var fragments: MutableList<Fragment>? = null
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null

    override fun getLayoutId() = R.layout.activity_my_collect_goods

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {

        fragments = ArrayList()
        fragments?.add(MyCollectFragment.newInstance(0))
        fragments?.add(MyCollectFragment.newInstance(1))

        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpCollect?.adapter = commomViewPagerAdapter
        mBinding?.vpCollect?.offscreenPageLimit = fragments?.size!!

        var commonNavigator = CommonNavigator(applicationContext)
        commonNavigator?.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(applicationContext)
                colorTransitionPagerTitleView.normalColor = Color.parseColor("#6A6E75")
                colorTransitionPagerTitleView.selectedColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                colorTransitionPagerTitleView.setPadding(SizeUtils.dp2px(10f), 0, SizeUtils.dp2px(10f), 0)
                colorTransitionPagerTitleView.setText(mTitles[index])
                colorTransitionPagerTitleView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        mBinding?.vpCollect?.currentItem = index
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
        ViewPagerHelper.bind(mBinding?.magicIndicator, mBinding?.vpCollect)
    }
}