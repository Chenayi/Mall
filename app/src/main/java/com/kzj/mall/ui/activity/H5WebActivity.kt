package com.kzj.mall.ui.activity

import android.webkit.JavascriptInterface
import android.widget.LinearLayout
import com.gyf.barlibrary.ImmersionBar
import com.just.agentweb.AgentWeb
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityH5WebBinding
import com.kzj.mall.di.component.AppComponent

/**
 * h5页面
 */
class H5WebActivity : BaseActivity<IPresenter, ActivityH5WebBinding>() {
    private var mAgentWeb: AgentWeb? = null
    private var url = "http://www.baidu.com"

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.white)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun getLayoutId() = R.layout.activity_h5_web

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mBinding?.llWeb as LinearLayout, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb?.getJsInterfaceHolder()?.addJavaObject("client", JSInterface())
    }


    class JSInterface {
        @JavascriptInterface
        fun test(goodsInfoId: String?) {

        }
    }
}