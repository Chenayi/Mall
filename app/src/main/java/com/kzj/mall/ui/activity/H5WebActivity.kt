package com.kzj.mall.ui.activity

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.LinearLayout
import com.gyf.barlibrary.ImmersionBar
import com.just.agentweb.AgentWeb
import com.kzj.mall.C
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
    private val URL = "http://m.kzj365.com/singlesdayapp.htm"

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
                .go(URL);

        mAgentWeb?.getJsInterfaceHolder()?.addJavaObject("client", JSInterface(this))
    }


    class JSInterface(val context: Context) {

        /**
         * 点击商品
         */
        @JavascriptInterface
        fun clickGoods(goodsInfoId: String?) {
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent?.putExtra(C.GOODS_INFO_ID, goodsInfoId)
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}