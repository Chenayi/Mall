package com.kzj.mall.ui.fragment

import android.os.Bundle
import com.kzj.mall.R
import com.kzj.mall.adapter.ExplainAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailExplain2Binding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.GoodsDetailEntity
import java.io.Serializable

/**
 * 说明书
 */
class GoodsDetailExplainFragment2 : BaseFragment<IPresenter, FragmentGoodsDetailExplain2Binding>() {
    private var explains: MutableList<GoodsDetailEntity.Explain>? = null
    private var explainAdapter: ExplainAdapter? = null

    companion object {
        fun newInstance(): GoodsDetailExplainFragment2 {
            val goodsDetailExplainBinding = GoodsDetailExplainFragment2()
            return goodsDetailExplainBinding
        }

        fun newInstance(explains: MutableList<GoodsDetailEntity.Explain>): GoodsDetailExplainFragment2 {
            val goodsDetailExplainBinding = GoodsDetailExplainFragment2()
            val arguments = Bundle()
            arguments.putSerializable("explains", explains as Serializable)
            goodsDetailExplainBinding?.arguments = arguments
            return goodsDetailExplainBinding
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail_explain2
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {
        val datas = arguments?.getSerializable("explains")
        datas?.let {
            explains = it as MutableList<GoodsDetailEntity.Explain>
            explainAdapter = ExplainAdapter(context, explains!!)
            mBinding?.lvExplain?.adapter = explainAdapter
        }

    }
}