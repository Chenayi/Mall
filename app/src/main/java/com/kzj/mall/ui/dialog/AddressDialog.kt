package com.kzj.mall.ui.dialog

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogAddressBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.address.Address
import com.kzj.mall.event.CheckCEvent
import com.kzj.mall.event.CheckDEvent
import com.kzj.mall.event.CheckPEvent
import com.kzj.mall.ui.fragment.AddressListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class AddressDialog : BaseDialog<IPresenter, DialogAddressBinding>(), View.OnClickListener {
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null

    private var checkP: Address.Province? = null
    private var checkC: Address.City? = null
    private var checkD: Address.District? = null

    private var onAddressCheckListener:OnAddressCheckListener?=null

    companion object {
        fun newInstance(): AddressDialog {
            val addressDialog = AddressDialog()
            val b = Bundle()
            addressDialog?.arguments = b
            return addressDialog
        }
    }

    override fun initData() {
        EventBus.getDefault().register(this)

        val pTop = ScreenUtils.getScreenHeight() * 0.3f
        mBinding?.flRoot?.setPadding(0, pTop.toInt(), 0, 0)
        mBinding?.flRoot?.setOnClickListener(this)
        mBinding?.ivClose?.setOnClickListener(this)

        fragments = ArrayList()
        fragments?.add(AddressListFragment.newInstance(0))
        fragments?.add(AddressListFragment.newInstance(1))
        fragments?.add(AddressListFragment.newInstance(2))
        commomViewPagerAdapter = CommomViewPagerAdapter(childFragmentManager, fragments!!)
        mBinding?.nsv?.offscreenPageLimit = 3
        mBinding?.nsv?.setNoScroll(true)
        mBinding?.nsv?.adapter = commomViewPagerAdapter

        mBinding?.llP?.setOnClickListener(this)
        mBinding?.llC?.setOnClickListener(this)
        mBinding?.llD?.setOnClickListener(this)
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId() = R.layout.dialog_address


    @Subscribe
    fun checkP(checkPEvent: CheckPEvent) {
        checkPEvent?.provinceBeen?.let {
            checkP = it
            checkC = null
            checkD = null
            mBinding?.tvP?.setText(it?.provinceName)
            mBinding?.tvC?.setText("请选择")
            mBinding?.llC?.visibility = View.VISIBLE
            mBinding?.llD?.visibility = View.GONE
            setDefaultCheck()
            checkCItem()
            mBinding?.nsv?.setCurrentItem(1, false)
            (fragments?.get(1) as AddressListFragment).requestCity(it?.provinceId!!)
        }
    }

    @Subscribe
    fun checkC(checkCEvent: CheckCEvent) {
        checkCEvent?.cityBeen?.let {
            checkC = it
            checkD = null
            mBinding?.tvC?.text = it?.cityName
            mBinding?.tvS?.setText("请选择")
            mBinding?.llD?.visibility = View.VISIBLE
            setDefaultCheck()
            checkDItem()
            mBinding?.nsv?.setCurrentItem(2, false)
            (fragments?.get(2) as AddressListFragment).requestDistrict(it?.cityId!!)
        }
    }

    @Subscribe
    fun checkD(checkDEvent: CheckDEvent) {
        checkDEvent?.districtBeen?.let {
            checkD = it
            mBinding?.tvS?.text = it?.districtName
            onAddressCheckListener?.onAddressCheck(checkP,checkC,checkD)
            dismiss()
        }
    }

    private fun checkPItem() {
        mBinding?.tvP?.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        mBinding?.lineP?.visibility = View.VISIBLE
    }

    private fun checkCItem() {
        mBinding?.tvC?.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        mBinding?.lineC?.visibility = View.VISIBLE
    }

    private fun checkDItem() {
        mBinding?.tvS?.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        mBinding?.lineS?.visibility = View.VISIBLE
    }

    private fun setDefaultCheck() {
        mBinding?.tvP?.setTextColor(ContextCompat.getColor(context!!, R.color.c_2e3033))
        mBinding?.tvC?.setTextColor(ContextCompat.getColor(context!!, R.color.c_2e3033))
        mBinding?.tvS?.setTextColor(ContextCompat.getColor(context!!, R.color.c_2e3033))

        mBinding?.lineP?.visibility = View.INVISIBLE
        mBinding?.lineC?.visibility = View.INVISIBLE
        mBinding?.lineS?.visibility = View.INVISIBLE
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_root -> {
                dismiss()
            }
            R.id.iv_close -> {
                dismiss()
            }
            R.id.ll_p -> {
                setDefaultCheck()
                checkPItem()
                mBinding?.nsv?.setCurrentItem(0, false)
            }
            R.id.ll_c -> {
                setDefaultCheck()
                checkCItem()
                mBinding?.nsv?.setCurrentItem(1, false)
            }
            R.id.ll_d -> {
                setDefaultCheck()
                checkDItem()
                mBinding?.nsv?.setCurrentItem(2, false)
            }
        }
    }

    fun setOnAddressCheckListener(onAddressCheckListener:OnAddressCheckListener):AddressDialog{
        this.onAddressCheckListener = onAddressCheckListener
        return this
    }

    interface OnAddressCheckListener{
        fun onAddressCheck(p: Address.Province?, c:Address.City?, d:Address.District?)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}