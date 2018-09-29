package com.kzj.mall.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
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
import org.greenrobot.eventbus.Subscribe

class CartFragment : BaseFragment<CartPresenter, FragmentCartBinding>(), View.OnClickListener, CartContract.View {
    private var cartAdapter: CartAdapter? = null
    private var isDeleteMode = false
    private var isAllCheck = false

    private var headerView: View? = null
    private var tvContent: TextView? = null
    private var tvLogin: TextView? = null

    private var isCartChange = false

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


    override fun initData() {
        cartAdapter = CartAdapter(ArrayList())
        cartAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp, mBinding?.rvCart?.parent as ViewGroup, false))
        mBinding?.rvCart?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvCart?.adapter = cartAdapter


        headerView = layoutInflater.inflate(R.layout.header_cart, mBinding?.rvCart?.parent as ViewGroup, false)
        tvContent = headerView?.findViewById(R.id.tv_content)
        tvLogin = headerView?.findViewById(R.id.tv_login)
        if (C.IS_LOGIN) {
            mBinding?.refreshLayout?.isEnabled = true
        } else {
            mBinding?.refreshLayout?.isEnabled = false
            mBinding?.llBalance?.visibility = View.GONE
            mBinding?.tvEdit?.visibility = View.GONE
            tvLogin?.visibility = View.VISIBLE
            tvLogin?.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            cartAdapter?.setHeaderView(headerView)
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
            }
        }

        cartAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.iv_check -> {
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
                        mBinding?.tvToBalance?.isEnabled = checkNum() > 0
                        mBinding?.tvToBalance?.setText("去结算(" + checkNum() + ")")
                }

            //数量减
                R.id.iv_minus -> {
                    var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                    val goodsNum = cartEntity?.goods_num!! - 1
                    val cartId = cartEntity?.shopping_cart_id
                    if (goodsNum > 0){
                        mPresenter?.changeCartNum(position, cartId, goodsNum?.toString()?.trim()!!)
                    }
                }

            //数量加
                R.id.iv_plus -> {
                    var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                    val goodsNum = cartEntity?.goods_num!! + 1
                    val cartId = cartEntity?.shopping_cart_id
                    if (goodsNum <= cartEntity?.goods_stock!!){
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
     * 移除购物车
     */
//    fun remove(position: Int) {
//        cartAdapter?.remove(position)
//        mBinding?.tvToBalance?.isEnabled = checkNum() > 0
//        mBinding?.tvToBalance?.setText("去结算(" + checkNum() + ")")
//        for (i in 0 until cartAdapter?.data?.size!!) {
//            val cart = cartAdapter?.data?.get(i)
//            val itemType = cart?.getItemType()
//            if (itemType == ICart.SINGLE || itemType == ICart.GROUP) {
//                return
//            }
//        }
//        cartAdapter?.setHeaderView(headerView, 0)
//        mBinding?.llBalance?.visibility = View.GONE
//        tvLogin?.visibility = View.GONE
//        isDeleteMode = false
//        mBinding?.tvEdit?.text = "编辑"
//        mBinding?.tvEdit?.visibility = View.GONE
//        mBinding?.rvCart?.scrollToPosition(0)
//    }

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
            if ((datas?.get(i) as BaseCartEntity).isCheck == false) {
                (datas?.get(i) as BaseCartEntity).isCheck = true
            }

            if (datas?.get(i) is CartSingleEntity || datas?.get(i) is CartGroupEntity) {
                size += 1
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
                    val p = iCart?.goods_pre_price?.toFloat()
                    p?.let {
                        sumPrePrice += it
//                        var num = iCart?.goods_num
//                        val pp = p * num!!
//                        sumPrePrice += pp
                    }

                    val s = iCart?.goods_price?.toFloat()
                    s?.let {
                        val num = iCart?.goods_num
                        val ss = it * num!!
                        sumPrice += ss
                    }
                }
            } else if (iCart is CartGroupEntity) {
                if (iCart?.isCheck) {
                    val p = iCart?.goods_pre_price?.toFloat()
                    p?.let {
                        sumPrePrice += it
//                        val num = iCart?.goods_num
//                        val pp = p * num!!
//                        sumPrePrice += pp
                    }

                    val s = iCart?.goods_price?.toFloat()
                    s?.let {
                        //                        val num = iCart?.goods_num
//                        val ss = s * num!!
                        sumPrice += it
                    }
                }
            }
        }

        mBinding?.tvAllPrice?.setText("¥" + FloatUtils.format(sumPrice))
        mBinding?.tvMinusPrice?.setText("已省：¥" + FloatUtils.format(sumPrePrice))
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
        mBinding?.ivAllCheck?.setImageResource(R.mipmap.check_nor)
        mBinding?.tvToBalance?.isEnabled = false
        mBinding?.tvToBalance?.setText("去结算(0)")
        mBinding?.tvAllPrice?.setText("¥0.00")
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

    @Subscribe
    fun logout(logoutEvent: LogoutEvent) {
        mBinding?.refreshLayout?.isEnabled = false
        cartAdapter?.setNewData(ArrayList())

        mBinding?.llBalance?.visibility = View.GONE
        mBinding?.tvEdit?.visibility = View.GONE
        tvLogin?.visibility = View.VISIBLE
        tvLogin?.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        cartAdapter?.setHeaderView(headerView)

        mBinding?.rvCart?.scrollToPosition(0)

        mPresenter?.loadRecommendsData()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (C.IS_LOGIN && isCartChange) {
            mPresenter?.requesrCart(false)
        }
    }

    override fun loadRecommendDatas(t: MutableList<CartRecommendEntity.Data>?) {
        t?.let {
            it?.get(0)?.isShowRecommendText = true
            val iCart = ArrayList<ICart>()
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
                val lcSinglePrice = lcSinglePrice(shoppingCart?.goods_num!!, shoppingCart?.combinations)
                singleEntity?.goods_price = lcSinglePrice
            }
            singleEntity?.goods_stock = shoppingCart?.goods_stock
            singleEntity?.goods_info_id = shoppingCart?.goods_info_id
            singleEntity?.shopping_cart_id = shoppingCart?.shopping_cart_id
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
     * 疗程价格
     */
    private fun lcSinglePrice(num: Int, combinations: MutableList<CartEntity.Combinations>?): String {
        var price = "0.00"
        combinations?.let {
            for (i in 0 until it.size) {

                if (i == 0 && num == it.get(0)?.package_count!!) {
                    price = it.get(0)?.combination_unit_price!!
                    return price
                }

                if (i > 0 && i < it.size - 1) {
                    val pre = it?.get(i - 1)?.package_count!!
                    val cur = it?.get(i)?.package_count!!
                    if (num > pre && num <= cur) {
                        price = it.get(i)?.combination_unit_price!!
                        return price
                    }
                }

                if (i > 0 && i == it.size - 1) {
                    val count = it?.get(i)?.package_count!!
                    if (num >= count) {
                        price = it.get(i)?.combination_unit_price!!
                        return price
                    } else {
                        price = it.get(i - 1)?.combination_unit_price!!
                        return price
                    }
                }
            }

        }
        return price
    }

    /**
     * 购物车删除成功
     */
    override fun deleteCartSuccess() {
        mPresenter?.requesrCart(false)
    }

    private fun changeDeleteMode(){
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
     * 购物车数据
     */
    override fun showCart(cartEntity: CartEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false

        isCartChange = false
        isDeleteMode = false

        changeDeleteMode()

        mBinding?.ivAllCheck?.setImageResource(R.mipmap.check_nor)
        mBinding?.tvToBalance?.isEnabled = false
        mBinding?.tvToBalance?.setText("去结算(0)")
        mBinding?.tvAllPrice?.setText("¥0.00")
        mBinding?.tvMinusPrice?.setText("已省：¥0.00")

        val shoplist = cartEntity?.shoplist
        val iCarts = ArrayList<ICart>()

        if (shoplist == null || shoplist?.size!! <= 0) {
            cartAdapter?.setNewData(iCarts)
            mBinding?.llBalance?.visibility = View.GONE
            tvLogin?.visibility = View.GONE
            mBinding?.tvEdit?.visibility = View.GONE
            cartAdapter?.setHeaderView(headerView)
            mPresenter?.loadRecommendsData()
            return
        }

        mBinding?.llBalance?.visibility = View.VISIBLE
        mBinding?.tvEdit?.visibility = View.VISIBLE
        cartAdapter?.removeHeaderView(headerView)





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
                    val lcSinglePrice = lcSinglePrice(shoplist?.get(i)?.goods_num!!, shoplist?.get(i)?.combinations)
                    singleEntity?.goods_price = lcSinglePrice
                }
                singleEntity?.goods_stock = shoplist?.get(i)?.goods_stock
                singleEntity?.goods_info_id = shoplist?.get(i)?.goods_info_id
                singleEntity?.shopping_cart_id = shoplist?.get(i)?.shopping_cart_id
                singleEntity?.let {
                    iCarts.add(it)
                }
            }
        }

        cartAdapter?.setNewData(iCarts)

        mPresenter?.loadRecommendsData()
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
            val cartEntity = data?.get(i) as BaseCartEntity
            if (cartEntity?.isCheck && (cartEntity is CartSingleEntity || cartEntity is CartGroupEntity)) {
                ids?.add(cartEntity?.shopping_cart_id?.toLong()!!)
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

                if (!isDeleteMode && cartAdapter?.data?.size!! <=0){
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
                if (cartIds.size > 0){
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