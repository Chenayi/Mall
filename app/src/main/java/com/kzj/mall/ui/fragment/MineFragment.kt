package com.kzj.mall.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.databinding.FragmentMineBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerMineComponent
import com.kzj.mall.di.module.MineModule
import com.kzj.mall.entity.MineEntity
import com.kzj.mall.event.LoginSuccessEvent
import com.kzj.mall.event.LogoutEvent
import com.kzj.mall.mvp.contract.MineContract
import com.kzj.mall.mvp.presenter.MinePresenter
import com.kzj.mall.ui.activity.*
import com.kzj.mall.ui.activity.login.LoginActivity
import org.greenrobot.eventbus.Subscribe
import q.rorbin.badgeview.QBadgeView

class MineFragment : BaseFragment<MinePresenter, FragmentMineBinding>(), View.OnClickListener, MineContract.View {
    private var followLists: MutableList<MineEntity.FollowList>? = null

    private var waitPayNum:QBadgeView?=null
    private var waitSendNum:QBadgeView?=null
    private var waitTakeNum:QBadgeView?=null

    companion object {
        fun newInstance(): MineFragment {
            val mineFragment = MineFragment()
            return mineFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun enableEventBus(): Boolean {
        return true
    }


    override fun setupComponent(appComponent: AppComponent?) {
        DaggerMineComponent.builder()
                .appComponent(appComponent)
                .mineModule(MineModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(false)
                ?.statusBarColor(R.color.tran)
                ?.statusBarDarkFont(false)
                ?.init()
    }



    override fun initData() {
        val layoutParams = mBinding?.ivMsg?.layoutParams as RelativeLayout.LayoutParams
        layoutParams.rightMargin = SizeUtils.dp2px(14f)
        layoutParams.topMargin = BarUtils.getStatusBarHeight() + SizeUtils.dp2px(14f)
        mBinding?.ivMsg?.requestLayout()


        waitPayNum = QBadgeView(context)
        waitSendNum = QBadgeView(context)
        waitTakeNum = QBadgeView(context)
        if (C.IS_LOGIN) {
            mPresenter?.requestMine()
        }
        initListener()
    }

    private fun initListener() {
        mBinding?.rlTop?.setOnClickListener(this)
        mBinding?.rlAskAnswer?.setOnClickListener(this)
        mBinding?.llOrder?.setOnClickListener(this)
        mBinding?.rlOrderWaitPay?.setOnClickListener(this)
        mBinding?.rlOrderWaitSend?.setOnClickListener(this)
        mBinding?.rlOrderWaitTake?.setOnClickListener(this)
        mBinding?.rlOrderFinish?.setOnClickListener(this)
        mBinding?.rlBrowseRecord?.setOnClickListener(this)
        mBinding?.rlAddress?.setOnClickListener(this)
        mBinding?.rlCooperation?.setOnClickListener(this)
        mBinding?.rlAboutKzj?.setOnClickListener(this)
        mBinding?.llMyCollect?.setOnClickListener(this)
        mBinding?.ivCGoods1?.setOnClickListener(this)
        mBinding?.ivCGoods2?.setOnClickListener(this)
        mBinding?.ivCGoods3?.setOnClickListener(this)
        mBinding?.ivCGoods4?.setOnClickListener(this)
        mBinding?.rlMobile?.setOnClickListener(this)
        mBinding?.ivMsg?.setOnClickListener(this)
    }

    @Subscribe
    fun loginSuccess(loginSuccessEvent: LoginSuccessEvent) {
        mPresenter?.requestMine()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
    }

    override fun showMineData(mineEntity: MineEntity?) {
        mBinding?.ivBg?.setImageResource(R.mipmap.mine_logined)

        //商品收藏
        followLists = mineEntity?.follows?.list
        setFollow()
        //订单数目
        val orderSum = mineEntity?.orderSum
        orderSum?.let {
            waitPayNum?.bindTarget(mBinding?.rlOrderWaitPay)
                    ?.setShowShadow(false)
                    ?.setBadgeBackgroundColor(Color.RED)
                    ?.setGravityOffset(8f, 0f, true)
                    ?.setBadgeTextSize(12f, true)
                    ?.setBadgeNumber(it?.notpay)

            waitSendNum?.bindTarget(mBinding?.rlOrderWaitSend)
                    ?.setShowShadow(false)
                    ?.setBadgeBackgroundColor(Color.RED)
                    ?.setGravityOffset(8f, 0f, true)
                    ?.setBadgeTextSize(12f, true)
                    ?.setBadgeNumber(it?.notdy)

            waitTakeNum?.bindTarget(mBinding?.rlOrderWaitTake)
                    ?.setShowShadow(false)
                    ?.setBadgeBackgroundColor(Color.RED)
                    ?.setGravityOffset(8f, 0f, true)
                    ?.setBadgeTextSize(12f, true)
                    ?.setBadgeNumber(it?.nottdy)
        }

        //个人信息
        val customerMap = mineEntity?.customerMap
        customerMap?.let {
            mBinding?.tvInfo?.text = it?.customer_username
        }
        customerMap?.customer_img?.let {
            GlideApp.with(context!!)
                    .load(it)
                    .placeholder(R.mipmap.icon_avatar_default)
                    .centerCrop()
                    .into(mBinding?.ivAvatar!!)
        }
    }

    /**
     * 商品收藏
     */
    private fun setFollow() {
        if (followLists == null) {
            mBinding?.llMyCollect?.visibility = View.GONE
        } else {
            followLists?.let {

                if (it.size > 0) {
                    mBinding?.llMyCollect?.visibility = View.VISIBLE
                    if (it.size == 1) {
                        mBinding?.ivCGoods1?.visibility = View.VISIBLE
                        mBinding?.ivCGoods2?.visibility = View.INVISIBLE
                        mBinding?.ivCGoods3?.visibility = View.INVISIBLE
                        mBinding?.ivCGoods4?.visibility = View.INVISIBLE
                        GlideApp.with(context!!)
                                .load(it?.get(0)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods1!!)
                    } else if (it.size == 2) {
                        mBinding?.ivCGoods1?.visibility = View.VISIBLE
                        mBinding?.ivCGoods2?.visibility = View.VISIBLE
                        mBinding?.ivCGoods3?.visibility = View.INVISIBLE
                        mBinding?.ivCGoods4?.visibility = View.INVISIBLE
                        GlideApp.with(context!!)
                                .load(it?.get(0)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods1!!)

                        GlideApp.with(context!!)
                                .load(it?.get(1)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods2!!)
                    } else if (it.size == 3) {
                        mBinding?.ivCGoods1?.visibility = View.VISIBLE
                        mBinding?.ivCGoods2?.visibility = View.VISIBLE
                        mBinding?.ivCGoods3?.visibility = View.VISIBLE
                        mBinding?.ivCGoods4?.visibility = View.INVISIBLE

                        GlideApp.with(context!!)
                                .load(it?.get(0)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods1!!)

                        GlideApp.with(context!!)
                                .load(it?.get(1)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods2!!)

                        GlideApp.with(context!!)
                                .load(it?.get(2)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods3!!)

                    } else {
                        mBinding?.ivCGoods1?.visibility = View.VISIBLE
                        mBinding?.ivCGoods2?.visibility = View.VISIBLE
                        mBinding?.ivCGoods3?.visibility = View.VISIBLE
                        mBinding?.ivCGoods4?.visibility = View.VISIBLE

                        GlideApp.with(context!!)
                                .load(it?.get(0)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods1!!)

                        GlideApp.with(context!!)
                                .load(it?.get(1)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods2!!)

                        GlideApp.with(context!!)
                                .load(it?.get(2)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods3!!)

                        GlideApp.with(context!!)
                                .load(it?.get(3)?.good?.goodsImg)
                                .placeholder(R.color.gray_default)
                                .centerCrop()
                                .into(mBinding?.ivCGoods4!!)
                    }
                } else {
                    mBinding?.llMyCollect?.visibility = View.GONE
                }
            }

        }
    }

    @Subscribe
    fun logout(logoutEvent: LogoutEvent) {
        mBinding?.ivBg?.setImageResource(R.mipmap.mine_default)
        mBinding?.tvInfo?.setText("登录/注册")
        mBinding?.llMyCollect?.visibility = View.GONE

        waitPayNum?.hide(true)
        waitTakeNum?.hide(true)
        waitSendNum?.hide(true)

        mBinding?.ivCGoods1?.visibility = View.INVISIBLE
        mBinding?.ivCGoods2?.visibility = View.INVISIBLE
        mBinding?.ivCGoods3?.visibility = View.INVISIBLE
        mBinding?.ivCGoods4?.visibility = View.INVISIBLE
        mBinding?.llMyCollect?.visibility = View.GONE

        mBinding?.ivAvatar?.setImageResource(R.mipmap.icon_avatar_default)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (isAcquired && C.IS_LOGIN) {
            mPresenter?.requestMine()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_top -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, PersonInfoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_ask_answer -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, MyAskAnswerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.ll_order -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, OrderActivity::class.java)
                    intent.putExtra("position", 0)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_order_wait_pay -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, OrderActivity::class.java)
                    intent.putExtra("position", 1)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_order_wait_send -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, OrderActivity::class.java)
                    intent.putExtra("position", 2)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_order_wait_take -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, OrderActivity::class.java)
                    intent.putExtra("position", 3)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_order_finish -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, OrderActivity::class.java)
                    intent.putExtra("position", 4)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_browse_record -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, BrowseRecordsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_address -> {
                if (!C.IS_LOGIN) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, MyAddressListActivity::class.java)
                    intent.putExtra("isManager", true)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            R.id.rl_cooperation -> {
                val intent = Intent(context, CooperationActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.rl_about_kzj -> {
                val intent = Intent(context, AboutKzjActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            R.id.iv_c_goods1 -> {
                followLists?.let {
                    if (it.size > 0) {
                        val intent = Intent(context, GoodsDetailActivity::class.java)
                        intent?.putExtra(C.GOODS_INFO_ID, it.get(0)?.good?.goodsInfoId)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
            R.id.iv_c_goods2 -> {
                followLists?.let {
                    if (it.size > 1) {
                        val intent = Intent(context, GoodsDetailActivity::class.java)
                        intent?.putExtra(C.GOODS_INFO_ID, it.get(1)?.good?.goodsInfoId)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
            R.id.iv_c_goods3 -> {
                followLists?.let {
                    if (it.size > 2) {
                        val intent = Intent(context, GoodsDetailActivity::class.java)
                        intent?.putExtra(C.GOODS_INFO_ID, it.get(2)?.good?.goodsInfoId)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
            R.id.iv_c_goods4 -> {
                followLists?.let {
                    if (it.size > 3) {
                        val intent = Intent(context, GoodsDetailActivity::class.java)
                        intent?.putExtra(C.GOODS_INFO_ID, it.get(3)?.good?.goodsInfoId)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }

            R.id.ll_my_collect -> {
                val intent = Intent(context, MyCollectGoodsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.rl_mobile->{
                call()
            }
            R.id.iv_msg->{
                jumpActivity(MessageActivity().javaClass)
            }
        }
    }
}