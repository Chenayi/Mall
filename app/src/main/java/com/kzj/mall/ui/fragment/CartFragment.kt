package com.kzj.mall.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.CartAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentCartBinding
import com.kzj.mall.entity.DataHelper
import com.kzj.mall.entity.cart.BaseCartEntity

class CartFragment : BaseFragment<IPresenter, FragmentCartBinding>() {
    private var cartAdapter:CartAdapter?=null
    companion object {
        fun newInstance(): CartFragment {
            val cartFragment = CartFragment()
            return cartFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_cart
    }

    override fun initData() {
        cartAdapter = CartAdapter(ArrayList())
        cartAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp,mBinding?.rvCart?.parent as ViewGroup,false))
        mBinding?.rvCart?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvCart?.adapter = cartAdapter

        cartAdapter?.setNewData(DataHelper.cartDatas())


        cartAdapter?.setOnItemClickListener { adapter, view, position ->
            var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
            cartEntity?.isCheck = !cartEntity?.isCheck
            cartAdapter?.notifyItemChanged(position)
        }
    }
}