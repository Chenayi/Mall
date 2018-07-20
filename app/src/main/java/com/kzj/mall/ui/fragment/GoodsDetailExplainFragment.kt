package com.kzj.mall.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.ExplainAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailExplainBinding
import com.kzj.mall.entity.GoodsDetailEntity
import java.io.Serializable

/**
 * 说明书
 */
class GoodsDetailExplainFragment : BaseFragment<IPresenter, FragmentGoodsDetailExplainBinding>() {
    private var listener: ChangeHeightListener? = null
    private var explains: MutableList<GoodsDetailEntity.Explain>? = null
    private var explainAdapter: ExplainAdapter? = null

    companion object {

        fun newInstance(): GoodsDetailExplainFragment {
            val goodsDetailExplainBinding = GoodsDetailExplainFragment()
            return goodsDetailExplainBinding
        }

        fun newInstance(explains: MutableList<GoodsDetailEntity.Explain>): GoodsDetailExplainFragment {
            val goodsDetailExplainBinding = GoodsDetailExplainFragment()
            val arguments = Bundle()
            arguments.putSerializable("explains", explains as Serializable)
            goodsDetailExplainBinding?.arguments = arguments
            return goodsDetailExplainBinding
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ChangeHeightListener) {
            listener = context
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail_explain
    }

    override fun initData() {
        explains = arguments?.getSerializable("explains") as MutableList<GoodsDetailEntity.Explain>
        explains?.let {
            explainAdapter = ExplainAdapter(context, it)
            mBinding?.lvExplain?.adapter = explainAdapter
        }

        val height = SizeUtils.getMeasuredHeight(mBinding?.rlExplain)
        LogUtils.e("height ===> " + height)

        listener?.changeDetailData(1, height)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface ChangeHeightListener {
        fun changeDetailData(position: Int, height: Int)
    }
}