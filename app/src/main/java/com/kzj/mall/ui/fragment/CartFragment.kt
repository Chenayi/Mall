package com.kzj.mall.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.allen.library.SuperTextView
import com.blankj.utilcode.util.ToastUtils
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
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.entity.cart.BaseCartEntity
import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.cart.ICart
import com.kzj.mall.event.LoginSuccessEvent
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
            mBinding?.llBalance?.visibility = View.GONE
            tvContent?.setText("购物车空空如也")
            tvLogin?.visibility = View.GONE
            mBinding?.tvEdit?.visibility = View.GONE
            cartAdapter?.addHeaderView(headerView)
        } else {
            mBinding?.refreshLayout?.isEnabled = false
            mBinding?.llBalance?.visibility = View.GONE
            mBinding?.tvEdit?.visibility = View.GONE
            tvLogin?.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            cartAdapter?.addHeaderView(headerView)
        }

        cartAdapter?.setOnItemClickListener { adapter, view, position ->
            val iCart = cartAdapter?.data?.get(position)
            if (iCart is CartSingleEntity && !isDeleteMode) {
                val intent = Intent(context, GoodsDetailActivity::class.java)
                intent?.putExtra(C.GOODS_INFO_ID, iCart?.goods_info_id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
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
                            mBinding?.ivAllCheck?.setImageResource(R.mipmap.check_nor)
                            isAllCheck = false
                        }
                        setCheckPrice()
                        mBinding?.tvToBalance?.isEnabled = checkNum() > 0
                        mBinding?.tvToBalance?.setText("去结算(" + checkNum() + ")")
                    } else {
                        ConfirmDialog.newInstance("狠心删除", "留在购物车", "很抢手哦 ～ 下次不一定能买到确定要删除我吗 ～")
                                .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                                    override fun onLeftClick() {
                                        val cartId = (cartAdapter?.getItem(position) as BaseCartEntity)?.shopping_cart_id
                                        mPresenter?.deleteCart(position, cartId)
                                    }

                                    override fun onRightClick() {
                                    }

                                })
                                .show(childFragmentManager)
                    }
                }

            //数量减
                R.id.iv_minus -> {
                    var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                    val goodsNum = cartEntity?.goods_num!! - 1
                    val cartId = cartEntity?.shopping_cart_id
                    mPresenter?.changeCartNum(position, cartId, goodsNum?.toString()?.trim()!!)
                }

            //数量加
                R.id.iv_plus -> {
                    var cartEntity = cartAdapter?.data?.get(position) as BaseCartEntity
                    val goodsNum = cartEntity?.goods_num!! + 1
                    val cartId = cartEntity?.shopping_cart_id
                    mPresenter?.changeCartNum(position, cartId, goodsNum?.toString()?.trim()!!)
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

        if (C.IS_LOGIN) {
            mPresenter?.requesrCart(true)
        }
    }

    /**
     * 移除购物车
     */
    fun remove(position: Int) {
        cartAdapter?.remove(position)
        mBinding?.tvToBalance?.isEnabled = checkNum() > 0
        mBinding?.tvToBalance?.setText("去结算(" + checkNum() + ")")
        for (i in 0 until cartAdapter?.data?.size!!) {
            val cart = cartAdapter?.data?.get(i)
            val itemType = cart?.getItemType()
            if (itemType == ICart.SINGLE || itemType == ICart.GROUP) {
                return
            }
        }
        cartAdapter?.addHeaderView(headerView, 0)
        mBinding?.llBalance?.visibility = View.GONE
        tvLogin?.visibility = View.GONE
        mBinding?.tvEdit?.visibility = View.GONE
        tvContent?.setText("购物车空空如也")
        mBinding?.rvCart?.scrollToPosition(0)
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
     * 选中数量
     */
    fun checkNum(): Int {
        var num = 0
        val datas = cartAdapter?.data
        for (i in 0 until datas?.size!!) {
            if ((datas?.get(i) as BaseCartEntity).isCheck) {
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
        for (i in 0 until datas?.size!!) {
            if ((datas?.get(i) as BaseCartEntity).isCheck == false) {
                (datas?.get(i) as BaseCartEntity).isCheck = true
            }
        }
        setCheckPrice()
        cartAdapter?.notifyDataSetChanged()
        mBinding?.ivAllCheck?.setImageResource(R.mipmap.icon_cart_check)
        mBinding?.tvToBalance?.isEnabled = cartAdapter?.data?.size!! > 0
        mBinding?.tvToBalance?.setText("去结算(" + cartAdapter?.data?.size + ")")
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
                        var num = iCart?.goods_num
                        val pp = p * num!!
                        sumPrePrice += pp
                    }

                    val s = iCart?.goods_price?.toFloat()
                    s?.let {
                        val num = iCart?.goods_num
                        val ss = s * num!!
                        sumPrice += ss
                    }
                }
            } else if (iCart is CartGroupEntity) {
                if (iCart?.isCheck) {
                    val p = iCart?.goods_pre_price?.toFloat()
                    p?.let {
                        val num = iCart?.goods_num
                        val pp = p * num!!
                        sumPrePrice += pp
                    }

                    val s = iCart?.goods_price?.toFloat()
                    s?.let {
                        val num = iCart?.goods_num
                        val ss = s * num!!
                        sumPrice += ss
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

//    @Subscribe
//    fun loginSuccess(loginSuccessEvent: LoginSuccessEvent) {
//        mPresenter?.requesrCart(false)
//    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (isAcquired && C.IS_LOGIN){
            mPresenter?.requesrCart(false)
        }
    }

    /**
     * 购物车数量修改成功
     */
    override fun changeCartNumSeccess(position: Int, t: CartEntity?) {
        val cartEntity = cartAdapter?.getItem(position)
        if (cartEntity is CartSingleEntity){
            cartEntity?.goods_num = t?.shoppingCart?.goods_num
            cartEntity?.goods_stock = t?.shoppingCart?.goods_stock
        }else if (cartEntity is CartGroupEntity){
            cartEntity?.goods_num = t?.shoppingCart?.goods_num
            cartEntity?.goods_stock = t?.shoppingCart?.goods_stock
        }

        cartAdapter?.notifyItemChanged(position)
    }

    /**
     * 购物车删除成功
     */
    override fun deleteCartSuccess(position: Int) {
        remove(position)
        setCheckPrice()
    }

    /**
     * 购物车数据
     */
    override fun showCart(cartEntity: CartEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        mBinding?.ivAllCheck?.setImageResource(R.mipmap.check_nor)
        mBinding?.tvToBalance?.isEnabled = false
        mBinding?.tvToBalance?.setText("去结算(0)")
        mBinding?.tvAllPrice?.setText("¥0.00")
        mBinding?.tvMinusPrice?.setText("已省：¥0.00")
        val shoplist = cartEntity?.shoplist
        val iCarts = ArrayList<ICart>()

        if (shoplist == null || shoplist?.size!! <= 0) {
            cartAdapter?.setNewData(iCarts)
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
                singleEntity?.goods_price = shoplist?.get(i)?.goods_price
                singleEntity?.goods_stock = shoplist?.get(i)?.goods_stock
                singleEntity?.goods_info_id = shoplist?.get(i)?.goods_info_id
                singleEntity?.shopping_cart_id = shoplist?.get(i)?.shopping_cart_id
                singleEntity?.let {
                    iCarts.add(it)
                }
            }
        }

        cartAdapter?.setNewData(iCarts)
    }

    /**
     * 结算
     */
    override fun banlance(buyEntity: BuyEntity?) {
        val intent = Intent(context, ConfirmOrderActivity::class.java)
        intent?.putExtra("buyEntity", buyEntity)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun cartIds(): LongArray {
        val data = cartAdapter?.data
        val ids = ArrayList<Long>()
        for (i in 0 until data?.size!!) {
            val cartEntity = data?.get(i) as BaseCartEntity
            if (cartEntity?.isCheck) {
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
                mPresenter?.cartBanlance(cartIds())
            }
        }
    }
}