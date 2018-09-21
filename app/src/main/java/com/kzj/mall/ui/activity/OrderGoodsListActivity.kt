package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.OrderGoodsListAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityOrderGoodsListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.ICart

/**
 *商品清单列表
 */
class OrderGoodsListActivity : BaseActivity<IPresenter, ActivityOrderGoodsListBinding>() {
    private var buyEntity: BuyEntity? = null
    private var orderGoodsListAdapter: OrderGoodsListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_order_goods_list
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.fb)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        intent?.getSerializableExtra("buyEntity")?.let {
            buyEntity = it as BuyEntity

            LogUtils.e("数据传递成功...")
        }

        val iCarts = ArrayList<ICart>()

        buyEntity?.shoplist?.let {
            for (i in 0 until it?.size) {
                val type = it?.get(i)?.shopping_cart_type
                //套装
                if (type.equals("2")) {
                    val cartGroupEntity = CartGroupEntity()
                    cartGroupEntity?.groups = it.get(i)?.ggList
                    cartGroupEntity?.goods_pre_price = it.get(i)?.goods_pre_price
                    cartGroupEntity?.combination_name = it.get(i)?.combination_name
                    cartGroupEntity?.goods_num = it.get(i)?.goods_num
                    cartGroupEntity?.goods_price = it.get(i)?.goods_price
                    cartGroupEntity?.goods_stock = it.get(i)?.goods_stock
                    cartGroupEntity?.shopping_cart_id = it.get(i)?.shopping_cart_id
                    iCarts.add(cartGroupEntity)
                }
                //单品 疗程
                else if (type.equals("0") || type.equals("1")) {
                    val singleEntity = it.get(i)?.appgoods
                    singleEntity?.shopping_cart_type = it.get(i)?.shopping_cart_type
                    singleEntity?.goods_pre_price = it.get(i)?.goods_pre_price
                    singleEntity?.combination_name = it.get(i)?.combination_name
                    singleEntity?.goods_num = it.get(i)?.goods_num
                    singleEntity?.goods_price = it.get(i)?.goods_price
                    singleEntity?.goods_stock = it.get(i)?.goods_stock
                    singleEntity?.goods_info_id = it.get(i)?.goods_info_id
                    singleEntity?.shopping_cart_id = it.get(i)?.shopping_cart_id
                    singleEntity?.let {
                        iCarts.add(it)
                    }
                }
            }
        }

        mBinding?.rvGoods?.layoutManager = LinearLayoutManager(this)
        orderGoodsListAdapter = OrderGoodsListAdapter(iCarts)
        orderGoodsListAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp
                , mBinding?.rvGoods?.parent as ViewGroup, false))
        mBinding?.rvGoods?.adapter = orderGoodsListAdapter
    }
}