package com.kzj.mall.ui.activity.login

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.adapter.LoginNavigatorTitleView
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityLoginBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.event.LoginSuccessEvent
import com.kzj.mall.event.RegisterSuccessEvent
import com.kzj.mall.ui.fragment.login.BaseLoginFragment
import com.kzj.mall.ui.fragment.login.LoginCodeFragment
import com.kzj.mall.ui.fragment.login.LoginPasswordFragment
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class LoginActivity : BaseActivity<IPresenter, ActivityLoginBinding>(), View.OnClickListener {
    private val mTitles: Array<String> = arrayOf("密码登录", "验证码登录")
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null

    private var restart = false
    private var register = false
    private var mobile: String? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                ?.fitsSystemWindows(true, R.color.white)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun initData() {
        fragments = ArrayList()
        fragments?.add(LoginPasswordFragment.newInstance())
        fragments?.add(LoginCodeFragment.newInstance())
        mBinding?.vpLogin?.setNoScroll(false)
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpLogin?.adapter = commomViewPagerAdapter
        setUpViewPager()
        mBinding?.ivClose?.setOnClickListener(this)
    }

    private fun setUpViewPager() {
        var commonNavigator = CommonNavigator(applicationContext)
        commonNavigator?.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = LoginNavigatorTitleView(applicationContext)
                colorTransitionPagerTitleView.normalColor = ContextCompat.getColor(applicationContext, R.color.c_2e3033)
                colorTransitionPagerTitleView.selectedColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                colorTransitionPagerTitleView.setPadding(SizeUtils.dp2px(10f), 0, SizeUtils.dp2px(10f), 0)
                colorTransitionPagerTitleView.setText(mTitles[index])
                colorTransitionPagerTitleView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        mBinding?.vpLogin?.currentItem = index
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
                indicator.lineWidth = SizeUtils.dp2px(21f).toFloat()
                indicator.lineHeight = SizeUtils.dp2px(3f).toFloat()
                return indicator
            }


            override fun getTitleWeight(context: Context?, index: Int): Float {
                return 1.0f
            }
        }

        mBinding?.magicIndicator?.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding?.magicIndicator, mBinding?.vpLogin);
    }

    @Subscribe
    fun register(registerSuccessEvent: RegisterSuccessEvent) {
        mobile = registerSuccessEvent?.mobile
        register = true
    }

    override fun onRestart() {
        super.onRestart()
        restart = true
    }

    override fun onResume() {
        super.onResume()

        if (restart && register) {

            for (i in 0 until fragments?.size!!) {
                (fragments?.get(i) as BaseLoginFragment).setMobile(mobile)
            }

            register = false
            restart = false
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_close -> {
                onBackPressedSupport()
            }
        }
    }

    override fun onBackPressedSupport() {
        KeyboardUtils.hideSoftInput(this)
        super.onBackPressedSupport()
    }

    fun loginSuccess() {
        C.IS_LOGIN = true
        EventBus.getDefault().post(LoginSuccessEvent())
        KeyboardUtils.hideSoftInput(this)
        finish()
    }
}