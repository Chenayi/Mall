package com.kzj.mall.ui.fragment

import android.os.Bundle
import android.webkit.WebSettings
import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailDescribeBinding
import com.kzj.mall.di.component.AppComponent

/**
 * 图文描述
 */
class GoodsDetailDescribeFragment : BaseFragment<IPresenter, FragmentGoodsDetailDescribeBinding>() {

    private var goodsMobileDesc: String? = null

    companion object {
        fun newInstance(goodsMobileDesc: String?): GoodsDetailDescribeFragment {
            val goodeDetailDescribeFragment = GoodsDetailDescribeFragment()
            val a = Bundle()
            a.putString("goodsMobileDesc", goodsMobileDesc)
            goodeDetailDescribeFragment?.arguments = a
            return goodeDetailDescribeFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail_describe
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {

        val webSettings = mBinding?.wvDetail?.settings
        //支持javascript
        webSettings?.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings?.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings?.setBuiltInZoomControls(true);
        //扩大比例的缩放
        webSettings?.setUseWideViewPort(true);
        //自适应屏幕
        webSettings?.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings?.setLoadWithOverviewMode(true);


        arguments?.getString("goodsMobileDesc")?.let {
            goodsMobileDesc = it
            goodsMobileDesc = goodsMobileDesc?.replace("src=\"/images", "src=\"" + C.REAL_URL + "/images")

            val htmlData = getHtmlData(goodsMobileDesc)

            LogUtils.e(htmlData)

            mBinding?.wvDetail?.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
        }
    }


    private fun getHtmlData(bodyHTML:String?):String{
        val head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>"

        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}