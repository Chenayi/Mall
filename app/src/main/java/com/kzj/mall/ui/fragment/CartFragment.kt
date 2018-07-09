package com.kzj.mall.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.CartAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentCartBinding
import com.kzj.mall.entity.DataHelper
import com.kzj.mall.entity.cart.BaseCartEntity
import com.kzj.mall.ui.activity.ConfirmOrderActivity

class CartFragment : BaseFragment<IPresenter, FragmentCartBinding>(), View.OnClickListener {
    private var cartAdapter: CartAdapter? = null
    private var isDeleteMode = false
    private var isAllCheck = false

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
        cartAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp, mBinding?.rvCart?.parent as ViewGroup, false))
        mBinding?.rvCart?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvCart?.adapter = cartAdapter

        cartAdapter?.setNewData(DataHelper.cartDatas())


//        cartAdapter?.setOnItemClickListener { adapter, view, position ->
//            var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
//            cartEntity?.isCheck = !cartEntity?.isCheck
//            cartAdapter?.notifyItemChanged(position)
//        }

        cartAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.iv_check -> {
                    if (isDeleteMode == false) {
                        var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                        cartEntity?.isCheck = !cartEntity?.isCheck
                        cartAdapter?.notifyItemChanged(position)

                        if (isAllCheck()) {
                            mBinding?.ivAllCheck?.setImageResource(R.mipmap.icon_cart_check)
                            isAllCheck = true
                        } else {
                            mBinding?.ivAllCheck?.setImageResource(R.color.gray_default)
                            isAllCheck = false
                        }
                    }
                }
            }
        }

        mBinding?.tvEdit?.setOnClickListener(this)
        mBinding?.llAllCheck?.setOnClickListener(this)
        mBinding?.tvToBalance?.setOnClickListener(this)
    }

    /**
     * 判断是否全选
     */
    fun isAllCheck(): Boolean {
        val datas = cartAdapter?.data
        for (i in 0 until datas?.size!!) {
            if (!(datas?.get(i) as BaseCartEntity).isCheck) {
                return false
            }
        }
        return true
    }

    /**
     * 设置全选
     */
    fun setAllCheck() {
        val datas = cartAdapter?.data
        for (i in 0 until datas?.size!!) {
            if ((datas?.get(i) as BaseCartEntity).isCheck == false) {
                (datas?.get(i) as BaseCartEntity).isCheck = true
            }
        }
        cartAdapter?.notifyDataSetChanged()
        mBinding?.ivAllCheck?.setImageResource(R.mipmap.icon_cart_check)
    }

    /**
     * 取消全选
     */
    fun cancelAllCheck() {
        val datas = cartAdapter?.data
        for (i in 0 until datas?.size!!) {
            if ((datas?.get(i) as BaseCartEntity).isCheck == true) {
                (datas?.get(i) as BaseCartEntity).isCheck = false
            }
        }
        cartAdapter?.notifyDataSetChanged()
        mBinding?.ivAllCheck?.setImageResource(R.color.gray_default)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_edit -> {
                isDeleteMode = !isDeleteMode
                if (isDeleteMode) {
                    mBinding?.tvEdit?.setText("完成")
                } else {
                    mBinding?.tvEdit?.setText("编辑")
                }
                val datas = cartAdapter?.data
                for (i in 0 until datas?.size!!) {
                    (datas?.get(i) as BaseCartEntity).isDeleteMode = isDeleteMode
                }
                cartAdapter?.notifyDataSetChanged()
            }

            R.id.ll_all_check -> {
                if (!isAllCheck) {
                    setAllCheck()
                } else {
                    cancelAllCheck()
                }
                isAllCheck = !isAllCheck
            }

            R.id.tv_to_balance -> {
                val intent = Intent(context, ConfirmOrderActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}