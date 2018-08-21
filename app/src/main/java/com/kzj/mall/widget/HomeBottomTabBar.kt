package com.kzj.mall.widget

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.HomeBottomTabBarBinding
import android.view.animation.AnimationUtils


class HomeBottomTabBar : BaseRelativeLayout<HomeBottomTabBarBinding>, View.OnClickListener {
    private var onTabChooseListener: OnTabChooseListener? = null
    private var scaleAnim:Animation?=null

    companion object {
        val TAB_HOME = 0
        val TAB_CLASSIFT = 1
        val TAB_CART = 2
        val TAB_MINE = 3
    }

    private var curTab = TAB_HOME

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId(): Int {
        return R.layout.home_bottom_tab_bar
    }

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        scaleAnim = AnimationUtils.loadAnimation(context, R.anim.scale_anim)
        mBinding?.rlHome?.setOnClickListener(this)
        mBinding?.rlClassify?.setOnClickListener(this)
        mBinding?.rlCart?.setOnClickListener(this)
        mBinding?.rlMine?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_home -> {
                if (curTab != TAB_HOME) {
                    playAnim(mBinding?.ivHome)
                    switchHome()
                }
            }
            R.id.rl_classify -> {
                if (curTab != TAB_CLASSIFT) {
                    playAnim(mBinding?.llClassify)
                    switchClassify()
                }
            }
            R.id.rl_cart -> {
                if (curTab != TAB_CART) {
                    playAnim(mBinding?.llCart)
                    switchCart()
                }
            }
            R.id.rl_mine -> {
                if (curTab != TAB_MINE) {
                    playAnim(mBinding?.llMine)
                    switchMine()
                }
            }
        }
    }


    fun playAnim(v: View?) {
        var scaleAnim= AnimationUtils.loadAnimation(context, R.anim.scale_anim)
        v?.startAnimation(scaleAnim)
    }


    /**
     * 首页
     */
    fun switchHome() {
        setDefault(TAB_HOME)
        mBinding?.ivHome?.visibility = View.VISIBLE
        onTabChooseListener?.onTabChoose(TAB_HOME)
        curTab = TAB_HOME
    }

    /**
     * 分类
     */
    fun switchClassify() {
        setDefault(TAB_CLASSIFT)
        mBinding?.ivClassify?.setImageResource(R.mipmap.icon_tab_classify_sel)
        mBinding?.tvClassify?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        onTabChooseListener?.onTabChoose(TAB_CLASSIFT)
        curTab = TAB_CLASSIFT
    }

    /**
     * 购物车
     */
    fun switchCart() {
        setDefault(TAB_CART)
        mBinding?.ivCart?.setImageResource(R.mipmap.icon_tab_cart_sel)
        mBinding?.tvCart?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        onTabChooseListener?.onTabChoose(TAB_CART)
        curTab = TAB_CART
    }

    /**
     * 我的
     */
    fun switchMine() {
        setDefault(TAB_MINE)
        mBinding?.ivMine?.setImageResource(R.mipmap.icon_tab_mine_sel)
        mBinding?.tvMine?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        onTabChooseListener?.onTabChoose(TAB_MINE)
        curTab = TAB_MINE
    }

    /**
     * 默认
     */
    fun setDefault(clicktab: Int) {
        when (clicktab) {
            TAB_HOME -> {
                mBinding?.llHome?.visibility = View.GONE
                mBinding?.ivCart?.setImageResource(R.mipmap.icon_tab_cart)
                mBinding?.ivClassify?.setImageResource(R.mipmap.icon_tab_classify)
                mBinding?.ivMine?.setImageResource(R.mipmap.icon_tab_mine)
                mBinding?.tvCart?.setTextColor(Color.parseColor("#8A9099"))
                mBinding?.tvClassify?.setTextColor(Color.parseColor("#8A9099"))
                mBinding?.tvMine?.setTextColor(Color.parseColor("#8A9099"))
            }

            TAB_CLASSIFT -> {
                mBinding?.llHome?.visibility = View.VISIBLE
                mBinding?.ivHome?.visibility = View.GONE
                mBinding?.ivCart?.setImageResource(R.mipmap.icon_tab_cart)
                mBinding?.ivMine?.setImageResource(R.mipmap.icon_tab_mine)
                mBinding?.tvCart?.setTextColor(Color.parseColor("#8A9099"))
                mBinding?.tvMine?.setTextColor(Color.parseColor("#8A9099"))
            }

            TAB_CART -> {
                mBinding?.llHome?.visibility = View.VISIBLE
                mBinding?.ivHome?.visibility = View.GONE
                mBinding?.ivClassify?.setImageResource(R.mipmap.icon_tab_classify)
                mBinding?.ivMine?.setImageResource(R.mipmap.icon_tab_mine)
                mBinding?.tvClassify?.setTextColor(Color.parseColor("#8A9099"))
                mBinding?.tvMine?.setTextColor(Color.parseColor("#8A9099"))
            }

            TAB_MINE -> {
                mBinding?.ivCart?.setImageResource(R.mipmap.icon_tab_cart)
                mBinding?.ivClassify?.setImageResource(R.mipmap.icon_tab_classify)
                mBinding?.llHome?.visibility = View.VISIBLE
                mBinding?.ivHome?.visibility = View.GONE
                mBinding?.tvCart?.setTextColor(Color.parseColor("#8A9099"))
                mBinding?.tvClassify?.setTextColor(Color.parseColor("#8A9099"))
            }
        }
    }

    fun setOnTabChooseListener(onTabChooseListener: OnTabChooseListener) {
        this.onTabChooseListener = onTabChooseListener
    }

    interface OnTabChooseListener {
        fun onTabChoose(tab: Int)
    }
}