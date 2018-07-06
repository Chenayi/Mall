package com.kzj.mall.ui.fragment

import android.content.Context
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailDescribeBinding

/**
 * 图文描述
 */
class GoodsDetailDescribeFragment : BaseFragment<IPresenter, FragmentGoodsDetailDescribeBinding>() {
    private var listener: ChangeHeightListener? = null

    companion object {
        fun newInstance(): GoodsDetailDescribeFragment {
            val goodeDetailDescribeFragment = GoodsDetailDescribeFragment()
            return goodeDetailDescribeFragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ChangeHeightListener) {
            listener = context
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail_describe
    }

    override fun initData() {
        val height = SizeUtils.getMeasuredHeight(mBinding?.llRoot)
        listener?.changeData(0, height)
    }

    interface ChangeHeightListener {
        fun changeData(position: Int, height: Int)
    }
}