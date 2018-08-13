package com.kzj.mall.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentMineBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.event.LoginSuccessEvent
import com.kzj.mall.ui.activity.LoginActivity
import com.kzj.mall.ui.activity.MyAskAnswerActivity
import com.kzj.mall.ui.activity.OrderActivity
import com.kzj.mall.ui.activity.PersonInfoActivity
import org.greenrobot.eventbus.Subscribe

class MineFragment : BaseFragment<IPresenter, FragmentMineBinding>(), View.OnClickListener {

    companion object {
        fun newInstance(): MineFragment {
            val mineFragment = MineFragment()
            return mineFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun isImmersionBarEnabled(): Boolean {
        return true
    }

    override fun enableEventBus(): Boolean {
        return true
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {
        val layoutParams = mBinding?.ivMsg?.layoutParams as RelativeLayout.LayoutParams
        layoutParams.rightMargin = SizeUtils.dp2px(14f)
        layoutParams.topMargin = BarUtils.getStatusBarHeight() + SizeUtils.dp2px(14f)
        mBinding?.ivMsg?.requestLayout()


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
    }

    @Subscribe
    fun loginSuccess(loginSuccessEvent: LoginSuccessEvent) {
        mBinding?.ivBg?.setImageResource(R.mipmap.mine_logined)
        val mobile = "15718807588"
        val maskNumber = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length)
        mBinding?.tvInfo?.setText(maskNumber)
        mBinding?.llMyCollect?.visibility = View.VISIBLE
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
                val intent = Intent(context, MyAskAnswerActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.ll_order->{
                val intent = Intent(context,OrderActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.rl_order_wait_pay->{
                val intent = Intent(context,OrderActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.rl_order_wait_send->{
                val intent = Intent(context,OrderActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.rl_order_wait_take->{
                val intent = Intent(context,OrderActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.rl_order_finish->{
                val intent = Intent(context,OrderActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}