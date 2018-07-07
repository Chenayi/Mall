package com.kzj.mall.ui.fragment

import android.content.Context
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailExplainBinding

/**
 * 说明书
 */
class GoodsDetailExplainFragment : BaseFragment<IPresenter, FragmentGoodsDetailExplainBinding>() {
    private var listener: ChangeHeightListener? = null
    companion object {
        fun newInstance(): GoodsDetailExplainFragment {
            val goodsDetailExplainBinding = GoodsDetailExplainFragment()
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
        val height = SizeUtils.getMeasuredHeight(mBinding?.llRoot)
        listener?.changeData(1, height)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface ChangeHeightListener {
        fun changeData(position: Int, height: Int)
    }
}