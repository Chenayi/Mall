package com.kzj.mall.ui.fragment

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.CartAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.databinding.FragmentCartBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerCartComponent
import com.kzj.mall.di.module.CartModule
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.CartEntity
import com.kzj.mall.entity.cart.*
import com.kzj.mall.event.CartChangeEvent
import com.kzj.mall.event.LoginSuccessEvent
import com.kzj.mall.event.LogoutEvent
import com.kzj.mall.mvp.contract.CartContract
import com.kzj.mall.mvp.presenter.CartPresenter
import com.kzj.mall.ui.activity.ConfirmOrderActivity
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.kzj.mall.ui.activity.login.LoginActivity
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.utils.PriceUtils
import org.greenrobot.eventbus.Subscribe
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class CartFragment : BaseFragment<CartPresenter, FragmentCartBinding>(), View.OnClickListener, CartContract.View {
    private var cartAdapter: CartAdapter? = null
    private var isDeleteMode = false
    private var isAllCheck = false

    private var headerEmptyView: View? = null
    private var tvContent: TextView? = null
    private var tvLogin: TextView? = null

    private var isCartChange = false

    /**
     * 满额
     */
    private var fullPrices = ArrayList<BigDecimal>()

    /**
     * 减额
     */
    private var reducePrices = ArrayList<BigDecimal>()

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


    override fun setupComponent(appComponent: AppComponent?) {
        DaggerCartComponent.builder()
                .appComponent(appComponent)
                .cartModule(CartModule(this))
                .build()
                .inject(this)
    }


    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindowsInt(true, ContextCompat.getColor(context!!, R.color.colorPrimary))
                ?.init()
    }

    override fun initData() {
        cartAdapter = CartAdapter(ArrayList())
        cartAdapter?.addFooterView(layoutInflater.inflate(R.layout.footer_kzj, mBinding?.rvCart?.parent as ViewGroup, false))
        mBinding?.rvCart?.layoutManager = GridLayoutManager(context, 2)

        cartAdapter?.setSpanSizeLookup(object : BaseQuickAdapter.SpanSizeLookup {
            override fun getSpanSize(gridLayoutManager: GridLayoutManager?, position: Int): Int {
                val itemType = cartAdapter?.getItem(position)?.getItemType()
                if (itemType == ICart.RECOMMEND) {
                    return 1
                }
                return 2
            }
        })

        mBinding?.rvCart?.adapter = cartAdapter


        headerEmptyView = layoutInflater.inflate(R.layout.header_cart, mBinding?.rvCart?.parent as ViewGroup, false)
        tvContent = headerEmptyView?.findViewById(R.id.tv_content)
        tvLogin = headerEmptyView?.findViewById(R.id.tv_login)

        //已登录
        if (C.IS_LOGIN) {
            mBinding?.refreshLayout?.isEnabled = true
        }
        //未登录
        else {
            mBinding?.refreshLayout?.isEnabled = false
            mBinding?.llBalance?.visibility = View.GONE
            mBinding?.tvEdit?.visibility = View.GONE
            tvLogin?.visibility = View.VISIBLE
            tvLogin?.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            cartAdapter?.setHeaderView(headerEmptyView)
            mPresenter?.loadRecommendsData()
        }

        cartAdapter?.setOnItemClickListener { adapter, view, position ->
            val iCart = cartAdapter?.data?.get(position)
            if (iCart is CartSingleEntity) {
                val intent = Intent(context, GoodsDetailActivity::class.java)
                intent?.putExtra(C.GOODS_INFO_ID, iCart?.goods_info_id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (iCart is CartRecommendEntity.Data) {
                val intent = Intent(context, GoodsDetailActivity::class.java)
                intent?.putExtra(C.GOODS_INFO_ID, iCart?.goods_info_id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (iCart is CartZSEntity) {
                val intent = Intent(context, GoodsDetailActivity::class.java)
                intent?.putExtra(C.GOODS_INFO_ID, iCart?.msMap?.goods_info?.goods_info_id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        cartAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.fl_check -> {
                    var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                    cartEntity?.isCheck = !cartEntity?.isCheck
                    cartAdapter?.notifyItemChanged(position)

                    if (isAllCheck()) {
                        mBinding?.ivAllCheck?.setImageResource(R.mipmap.icon_cart_check)
                        isAllCheck = true
                    } else {
                        mBinding?.ivAllCheck?.setImageResource(R.mipmap.check_nor)
                        isAllCheck = false
                    }
                    setCheckPrice()
                    val checkNum = checkNum()
                    mBinding?.tvToBalance?.isEnabled = checkNum > 0
                    mBinding?.tvToBalance?.setText("去结算(" + checkNum + ")")
                }

            //数量减
                R.id.iv_minus -> {
                    var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                    val goodsNum = cartEntity?.goods_num!! - 1
                    val cartId = cartEntity?.shopping_cart_id
                    if (goodsNum > 0) {
                        mPresenter?.changeCartNum(position, cartId, goodsNum?.toString()?.trim()!!)
                    }
                }

            //数量加
                R.id.iv_plus -> {
                    var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                    val goodsNum = cartEntity?.goods_num!! + 1
                    val cartId = cartEntity?.shopping_cart_id
                    if (goodsNum <= cartEntity?.goods_stock!!) {
                        mPresenter?.changeCartNum(position, cartId, goodsNum?.toString()?.trim()!!)
                    }
                }
            }
        }

        mBinding?.refreshLayout?.setOnRefreshListener {
            mPresenter?.requesrCart(false)
        }

        mBinding?.tvEdit?.setOnClickListener(this)
        mBinding?.llAllCheck?.setOnClickListener(this)
        mBinding?.tvToBalance?.isEnabled = false
        mBinding?.tvToBalance?.setOnClickListener(this)
        mBinding?.tvDelete?.setOnClickListener(this)

        if (C.IS_LOGIN) {
            mPresenter?.requesrCart(true)
        }
    }

    /**
     * 判断是否全选
     */
    fun isAllCheck(): Boolean {
        val datas = cartAdapter?.data

        val cartDatas = ArrayList<ICart>()
        for (i in 0 until datas?.size!!) {
            if (datas?.get(i) is CartSingleEntity || datas?.get(i) is CartGroupEntity) {
                cartDatas.add(datas?.get(i))
            }
        }


        for (i in 0 until cartDatas.size) {
            if (!(datas?.get(i) as BaseCartEntity).isCheck) {
                return false
            }
        }
        return true
    }

    /**
     * 选中数量
     */
    fun checkNum(): Int {
        var num = 0
        val datas = cartAdapter?.data
        for (i in 0 until datas?.size!!) {
            if ((datas?.get(i) is CartSingleEntity || datas?.get(i) is CartGroupEntity) && (datas?.get(i) as BaseCartEntity).isCheck) {
                num += 1
            }
        }
        return num
    }

    /**
     * 设置全选
     */
    fun setAllCheck() {
        val datas = cartAdapter?.data
        var size = 0
        for (i in 0 until datas?.size!!) {
            if (datas?.get(i) is BaseCartEntity) {
                if ((datas?.get(i) as BaseCartEntity).isCheck == false) {
                    (datas?.get(i) as BaseCartEntity).isCheck = true
                }

                if (datas?.get(i) is CartSingleEntity || datas?.get(i) is CartGroupEntity) {
                    size += 1
                }
            }
        }
        setCheckPrice()
        cartAdapter?.notifyDataSetChanged()
        mBinding?.ivAllCheck?.setImageResource(R.mipmap.icon_cart_check)
        mBinding?.tvToBalance?.isEnabled = cartAdapter?.data?.size!! > 0
        mBinding?.tvToBalance?.setText("去结算(" + size + ")")
    }

    /**
     * 计算选择的总金额
     */
    fun setCheckPrice() {
        var sumPrePrice = 0.00F
        var sumPrice = 0.00F

        val datas = cartAdapter?.data
        for (i in 0 until datas?.size!!) {
            val iCart = datas?.get(i)
            if (iCart is CartSingleEntity) {
                if (iCart?.isCheck) {
                    sumPrice += iCart?.goodsSumPrice?.toFloat()!!
                    //减额
                    val p = iCart?.goods_pre_price?.toFloat()
                    p?.let {
                        sumPrePrice += it
                    }
                }
            }
            //套餐
            else if (iCart is CartGroupEntity) {
                if (iCart?.isCheck) {
                    val s = iCart?.goods_price?.toFloat()
                    s?.let {
                        sumPrice += it
                    }
                    val p = iCart?.goods_pre_price?.toFloat()
                    p?.let {
                        sumPrePrice += it
                    }
                }
            }
        }

        val fp = ArrayList<BigDecimal>()
        fp.addAll(fullPrices)
        val rp = ArrayList<BigDecimal>()
        rp.addAll(reducePrices)
        Collections.reverse(fp)
        Collections.reverse(rp)

        for (i in 0 until fp.size) {
            val f = fp.get(i)
            val r = rp.get(i)
            if (sumPrice >= f.toFloat()) {
                sumPrePrice += r.toFloat()
                sumPrice -= r.toFloat()
                break
            }
        }
        val allPrice = PriceUtils.split12sp("¥" + FloatUtils.format(sumPrice))
        mBinding?.tvAllPrice?.setText(allPrice)
        mBinding?.tvMinusPrice?.setText("已省：¥" + FloatUtils.format(sumPrePrice))
    }

    /**
     * 取消全选
     */
    fun cancelAllCheck() {
        val datas = cartAdapter?.data
        for (i in 0 until datas?.size!!) {
            if (datas?.get(i) is BaseCartEntity) {
                if ((datas?.get(i) as BaseCartEntity).isCheck == true) {
                    (datas?.get(i) as BaseCartEntity).isCheck = false
                }
            }
        }
        cartAdapter?.notifyDataSetChanged()
        mBinding?.ivAllCheck?.setImageResource(R.mipmap.check_nor)
        mBinding?.tvToBalance?.isEnabled = false
        mBinding?.tvToBalance?.setText("去结算(0)")
        mBinding?.tvAllPrice?.setText(PriceUtils.split12sp("¥0.00"))
        mBinding?.tvMinusPrice?.setText("已省：¥0.00")
    }

    @Subscribe
    fun cartChange(cartChangeEvent: CartChangeEvent) {
        isCartChange = true
    }

    @Subscribe
    fun loginSuccess(loginSuccessEvent: LoginSuccessEvent) {
        mBinding?.refreshLayout?.isEnabled = true
        mPresenter?.requesrCart(false)
    }

    /**
     * 注销登录
     */
    @Subscribe
    fun logout(logoutEvent: LogoutEvent) {
        cartAdapter?.removeAllHeaderView()

        mBinding?.refreshLayout?.isEnabled = false
        cartAdapter?.setNewData(ArrayList())

        mBinding?.tvManjian?.visibility = View.GONE
        mBinding?.llBalance?.visibility = View.GONE
        mBinding?.tvEdit?.visibility = View.GONE

        tvLogin?.visibility = View.VISIBLE
        tvLogin?.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        cartAdapter?.setHeaderView(headerEmptyView)

        mBinding?.rvCart?.scrollToPosition(0)

        mPresenter?.loadRecommendsData()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (C.IS_LOGIN && isCartChange) {
            mPresenter?.requesrCart(false)
        }
    }

    /**
     * 推荐商品
     */
    override fun loadRecommendDatas(t: MutableList<CartRecommendEntity.Data>?) {
        t?.let {

            for (i in 0 until t?.size) {
                if (i % 2 == 0) {
                    it.get(i).isShowRightMargin = false
                    it?.get(i).isShowRightMargin2 = true
                    it.get(i).isShowLeftMargin = true
                    it.get(i).isShowLeftMargin2 = false
                } else {
                    it.get(i).isShowRightMargin = true
                    it?.get(i).isShowRightMargin2 = false
                    it.get(i).isShowLeftMargin = false
                    it.get(i).isShowLeftMargin2 = true
                }

                if (i == 0 || i == 1) {
                    t.get(i).isShowTopMargin = false
                } else {
                    t.get(i).isShowTopMargin = true
                }
            }

            val iCart = ArrayList<ICart>()
            iCart?.add(CartRecommendTextEntity())
            iCart?.addAll(it)
            if (cartAdapter?.data?.size!! > 0) {
                cartAdapter?.addData(iCart)
            } else {
                cartAdapter?.setNewData(iCart)
            }
            cartAdapter?.loadMoreEnd()
        }
    }

    /**
     * 购物车数量修改成功
     */
    override fun changeCartNumSeccess(position: Int, t: CartEntity?) {
        val shoppingCart = t?.shoppingCart
        val type = shoppingCart?.shopping_cart_type

        //套装
        if (type.equals("2")) {
            val cartGroupEntity = CartGroupEntity()
            cartGroupEntity?.groups = shoppingCart?.ggList
            cartGroupEntity?.goods_pre_price = shoppingCart?.goods_pre_price
            cartGroupEntity?.combination_name = shoppingCart?.combination_name
            cartGroupEntity?.goods_num = shoppingCart?.goods_num
            cartGroupEntity?.goods_price = shoppingCart?.goods_price
            cartGroupEntity?.goods_stock = shoppingCart?.goods_stock
            cartGroupEntity?.shopping_cart_id = shoppingCart?.shopping_cart_id
            cartGroupEntity?.promotionMap = shoppingCart?.promotionMap
            val check = (cartAdapter?.data?.get(position) as BaseCartEntity).isCheck
            cartGroupEntity?.isCheck = check
            cartAdapter?.data?.set(position, cartGroupEntity)

            if (check) {
                setCheckPrice()
            }
        }

        //单品 疗程
        else if (type.equals("0") || type.equals("1")) {
            val singleEntity = shoppingCart?.appgoods
            singleEntity?.shopping_cart_type = shoppingCart?.shopping_cart_type
            singleEntity?.goods_pre_price = shoppingCart?.goods_pre_price
            singleEntity?.combination_name = shoppingCart?.combination_name
            singleEntity?.goods_num = shoppingCart?.goods_num
            //单品
            if (type.equals("0")) {
                singleEntity?.goods_price = shoppingCart?.appgoods?.goods_price
            }
            //疗程
            else {
                val goodsPrice = shoppingCart?.goods_price?.toFloat()!!
                val singleGoodsPrice = goodsPrice / shoppingCart?.goods_num?.toFloat()!!
                singleEntity?.goods_price = FloatUtils.format(singleGoodsPrice)
            }
            singleEntity?.goods_stock = shoppingCart?.goods_stock
            singleEntity?.goods_info_id = shoppingCart?.goods_info_id
            singleEntity?.shopping_cart_id = shoppingCart?.shopping_cart_id
            singleEntity?.promotionMap = shoppingCart?.promotionMap
            val check = (cartAdapter?.data?.get(position) as BaseCartEntity).isCheck
            singleEntity?.isCheck = check
            singleEntity?.let {
                cartAdapter?.data?.set(position, it)
            }
            if (check) {
                setCheckPrice()
            }
        }
        cartAdapter?.notifyItemChanged(position)
    }

    /**
     * 购物车删除成功
     */
    override fun deleteCartSuccess() {
        mPresenter?.requesrCart(false)
    }

    /**
     * 切换删除编辑模式
     */
    private fun changeDeleteMode() {
        if (isDeleteMode) {
            mBinding?.tvEdit?.setText("完成")
            mBinding?.llBalance2?.visibility = View.GONE
            mBinding?.tvDelete?.visibility = View.VISIBLE
        } else {
            mBinding?.tvEdit?.setText("编辑")
            mBinding?.tvDelete?.visibility = View.GONE
            mBinding?.llBalance2?.visibility = View.VISIBLE
        }
    }

    /**
     * 重置底部默认
     */
    private fun setDefaultBottom() {
        mBinding?.ivAllCheck?.setImageResource(R.mipmap.check_nor)
        mBinding?.tvToBalance?.isEnabled = false
        mBinding?.tvToBalance?.setText("去结算(0)")
        mBinding?.tvAllPrice?.setText(PriceUtils.split12sp("¥0.00"))
        mBinding?.tvMinusPrice?.setText("已省：¥0.00")
    }

    /**
     * 购物车数据
     */
    override fun showCart(cartEntity: CartEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false

        isCartChange = false
        isDeleteMode = false

        //切换删除编辑模式
        changeDeleteMode()
        //重置底部默认
        setDefaultBottom()

        val shoplist = cartEntity?.shoplist
        val iCarts = ArrayList<ICart>()

        //购物车没有数据
        if (shoplist == null || shoplist?.size!! <= 0) {
            cartAdapter?.setNewData(iCarts)
            mBinding?.tvManjian?.visibility = View.GONE
            mBinding?.llBalance?.visibility = View.GONE
            tvLogin?.visibility = View.GONE
            mBinding?.tvEdit?.visibility = View.GONE
            cartAdapter?.setHeaderView(headerEmptyView)
            mPresenter?.loadRecommendsData()
            return
        }

        //全场满减
        setManjian(cartEntity?.orderPromotion)

        mBinding?.llBalance?.visibility = View.VISIBLE
        mBinding?.tvEdit?.visibility = View.VISIBLE
        cartAdapter?.removeHeaderView(headerEmptyView)


        //遍历数据适配
        for (i in 0 until shoplist?.size!!) {
            val type = shoplist?.get(i)?.shopping_cart_type

            //套装
            if (type.equals("2")) {
                val cartGroupEntity = CartGroupEntity()
                cartGroupEntity?.groups = shoplist?.get(i).ggList
                cartGroupEntity?.goods_pre_price = shoplist?.get(i)?.goods_pre_price
                cartGroupEntity?.combination_name = shoplist?.get(i)?.combination_name
                cartGroupEntity?.goods_num = shoplist?.get(i)?.goods_num
                cartGroupEntity?.goods_price = shoplist?.get(i)?.goods_price
                cartGroupEntity?.goods_stock = shoplist?.get(i)?.goods_stock
                cartGroupEntity?.shopping_cart_id = shoplist?.get(i)?.shopping_cart_id
                cartGroupEntity?.promotionMap = shoplist?.get(i)?.promotionMap
                iCarts.add(cartGroupEntity)
            }

            //单品 疗程
            else if (type.equals("0") || type.equals("1")) {
                val singleEntity = shoplist?.get(i).appgoods
                singleEntity?.shopping_cart_type = shoplist?.get(i)?.shopping_cart_type
                singleEntity?.goods_pre_price = shoplist?.get(i)?.goods_pre_price
                singleEntity?.combination_name = shoplist?.get(i)?.combination_name
                singleEntity?.goods_num = shoplist?.get(i)?.goods_num
                //单品
                if (type.equals("0")) {
                    singleEntity?.goods_price = shoplist?.get(i)?.appgoods?.goods_price
                }
                //疗程
                else {
                    var goodsPrice = shoplist?.get(i)?.goods_price?.toFloat()!!

                    if (shoplist?.get(i)?.promotionMap?.promotion_type == 3 && shoplist?.get(i)?.promotionMap?.promotion_mjprice != null) {
                        goodsPrice = PriceUtils.manjianPrice(goodsPrice, shoplist?.get(i)?.promotionMap?.promotion_mjprice!!)
                    }

                    val singleGoodsPrice = goodsPrice / shoplist?.get(i)?.goods_num?.toFloat()!!
                    singleEntity?.goods_price = FloatUtils.format(singleGoodsPrice)
                }
                singleEntity?.goodsSumPrice = shoplist?.get(i)?.goods_price
                singleEntity?.goods_stock = shoplist?.get(i)?.goods_stock
                singleEntity?.goods_info_id = shoplist?.get(i)?.goods_info_id
                singleEntity?.shopping_cart_id = shoplist?.get(i)?.shopping_cart_id
                singleEntity?.promotionMap = shoplist?.get(i)?.promotionMap
                singleEntity?.let {
                    iCarts.add(it)
                }
            }
        }

        //下单即送
        val msMap = cartEntity?.msMap
        if (msMap != null && msMap?.goods_info != null) {
            if (msMap?.goods_info?.goods_stock!! > 0) {
                val cartZSEntity = CartZSEntity()
                cartZSEntity.msMap = msMap
                iCarts.add(0, cartZSEntity)
            }
        }

        cartAdapter?.setNewData(iCarts)

        mPresenter?.loadRecommendsData()
    }

    /**
     * 全场满减
     */
    fun setManjian(orderPromotion: CartEntity.OrderPromotion?) {
        if (orderPromotion != null) {
            mBinding?.tvManjian?.visibility = View.VISIBLE

            fullPrices.clear()
            reducePrices.clear()

            orderPromotion?.promotion_mjprice?.let {
                val mj = it?.split(",")
                for (i in 0 until mj?.size) {
                    val m = mj.get(i).split("_")
                    fullPrices.add(m.get(0)?.toBigDecimal())
                    reducePrices.add(m.get(1)?.toBigDecimal())
                }

                //从小到大排序
                Collections.sort(fullPrices)
                Collections.sort(reducePrices)

                var r1 = "全场商品每笔订单每"
                var r2 = ""
                for (i in 0 until fullPrices.size) {
                    val f = fullPrices.get(i)
                    val r = reducePrices.get(i)
                    r2 += "满${f}即减${r}元、"
                }

                r2 = r2.substring(0, r2.length - 1)
                r1 += r2
                mBinding?.tvManjian?.text = r1
            }
        } else {
            mBinding?.tvManjian?.visibility = View.GONE
        }
    }

    /**
     * 结算
     */
    override fun banlance(buyEntity: BuyEntity?) {
        buyEntity?.shoppingCartIds = cartIds()
        val intent = Intent(context, ConfirmOrderActivity::class.java)
        intent?.putExtra("buyEntity", buyEntity)
        intent?.putExtra("isFromCart", true)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun cartIds(): LongArray {
        val data = cartAdapter?.data
        val ids = ArrayList<Long>()
        for (i in 0 until data?.size!!) {
            if (data?.get(i) is BaseCartEntity) {
                val cartEntity = data?.get(i) as BaseCartEntity
                if (cartEntity?.isCheck && (cartEntity is CartSingleEntity || cartEntity is CartGroupEntity)) {
                    ids?.add(cartEntity?.shopping_cart_id?.toLong()!!)
                }
            }
        }

        val cartIds = LongArray(ids?.size)
        for (i in 0 until ids?.size) {
            cartIds?.set(i, ids?.get(i))
        }
        return cartIds
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        mBinding?.refreshLayout?.isRefreshing = false
        ToastUtils.showShort(msg)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_edit -> {

                if (!isDeleteMode && cartAdapter?.data?.size!! <= 0) {
                    return
                }

                isDeleteMode = !isDeleteMode
                changeDeleteMode()
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
                mPresenter?.cartBanlance(cartIds())
            }
            R.id.tv_delete -> {
                val cartIds = cartIds()
                if (cartIds.size > 0) {
                    ConfirmDialog.newInstance("狠心删除", "留在购物车", "很抢手哦 ～ 下次不一定能买到确定要删除我吗 ～")
                            .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                                override fun onLeftClick() {
                                    mPresenter?.deleteCart(cartIds)
                                }

                                override fun onRightClick() {
                                }

                            })
                            .show(childFragmentManager)
                }
            }
        }
    }
}