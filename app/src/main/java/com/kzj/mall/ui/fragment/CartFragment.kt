package com.kzj.mall.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.allen.library.SuperTextView
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.CartAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentCartBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.entity.cart.BaseCartEntity
import com.kzj.mall.event.LoginSuccessEvent
import com.kzj.mall.ui.activity.ConfirmOrderActivity
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.kzj.mall.ui.activity.LoginActivity
import com.kzj.mall.ui.dialog.ConfirmDialog
import org.greenrobot.eventbus.Subscribe

class CartFragment : BaseFragment<IPresenter, FragmentCartBinding>(), View.OnClickListener {
    private var cartAdapter: CartAdapter? = null
    private var isDeleteMode = false
    private var isAllCheck = false

    private var headerView: View? = null
    private var tvContent: TextView? = null
    private var tvLogin: SuperTextView? = null

    companion object {
        fun newInstance(): CartFragment {
            val cartFragment = CartFragment()
            return cartFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_cart
    }

    override fun enableEventBus() = true

    override fun isImmersionBarEnabled(): Boolean {
        return true
    }

    override fun initImmersionBar() {
        immersionBarColor = R.color.fb
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, immersionBarColor)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {
        cartAdapter = CartAdapter(ArrayList())
        cartAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp, mBinding?.rvCart?.parent as ViewGroup, false))
        mBinding?.rvCart?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvCart?.adapter = cartAdapter


        headerView = layoutInflater.inflate(R.layout.header_cart, mBinding?.rvCart?.parent as ViewGroup, false)
        tvContent = headerView?.findViewById(R.id.tv_content)
        tvLogin = headerView?.findViewById(R.id.tv_login)
        if (C.ISLOGIN) {
            val datas = LocalDatas.cartDatas()
            if (datas?.size > 0) {
                cartAdapter?.setNewData(datas)
            } else {
                tvContent?.setText("购物车空空如也")
                tvLogin?.visibility = View.GONE
                mBinding?.tvEdit?.visibility = View.GONE
                cartAdapter?.addHeaderView(headerView)
            }
        } else {
            mBinding?.tvEdit?.visibility = View.GONE
            tvLogin?.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            cartAdapter?.addHeaderView(headerView)
        }
        cartAdapter?.addData(LocalDatas.cartRecommendDatas())


        cartAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

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
                    } else {
                        ConfirmDialog.newInstance("狠心删除", "留在购物车", "很抢手哦 ～ 下次不一定能买到确定要删除我吗 ～")
                                .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                                    override fun onLeftClick() {
                                        cartAdapter?.remove(position)
                                    }

                                    override fun onRightClick() {
                                    }

                                })
                                .show(childFragmentManager)
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

    @Subscribe
    fun loginSuccess(loginSuccessEvent: LoginSuccessEvent) {
        val datas = LocalDatas.cartDatas()
        if (datas?.size > 0) {
            cartAdapter?.removeHeaderView(headerView)
            cartAdapter?.setNewData(datas)
            cartAdapter?.addData(LocalDatas.cartRecommendDatas())
        } else {
            tvContent?.setText("购物车空空如也")
            tvLogin?.visibility = View.GONE
            mBinding?.tvEdit?.visibility = View.GONE
            cartAdapter?.addHeaderView(headerView)
        }
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