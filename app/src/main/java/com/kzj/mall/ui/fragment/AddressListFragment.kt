package com.kzj.mall.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.AddressListAdapter
import com.kzj.mall.base.BaseFragment2
import com.kzj.mall.databinding.FragmentAddressListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerAddressListComponent
import com.kzj.mall.di.module.AddressListModule
import com.kzj.mall.entity.address.*
import com.kzj.mall.event.CheckCEvent
import com.kzj.mall.event.CheckDEvent
import com.kzj.mall.event.CheckPEvent
import com.kzj.mall.mvp.contract.AddressListContract
import com.kzj.mall.mvp.presenter.AddressListPresenter
import org.greenrobot.eventbus.EventBus

class AddressListFragment : BaseFragment2<AddressListPresenter, FragmentAddressListBinding>(), AddressListContract.View {
    private var addressListAdapter: AddressListAdapter? = null
    private var p: Int = 0

    companion object {
        fun newInstance(p: Int): AddressListFragment {
            val addressListFragment = AddressListFragment()
            val b = Bundle()
            b.putInt("p", p)
            addressListFragment?.arguments = b
            return addressListFragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_address_list

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerAddressListComponent.builder()
                .appComponent(appComponent)
                .addressListModule(AddressListModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {

        LogUtils.e("initData ... ")

        arguments?.getInt("p")?.let {
            p = it
        }

        addressListAdapter = AddressListAdapter(ArrayList())
        addressListAdapter?.setOnItemClickListener { adapter, view, position ->
            when (p) {
                0 -> {
                    val data = addressListAdapter?.data
                    if (!(data?.get(position) as Address.Province).ckeck){
                        for (i in 0 until data?.size!!) {
                            val province = data?.get(i) as Address.Province
                            province?.ckeck = (position == i)
                        }
                        addressListAdapter?.notifyDataSetChanged()

                        EventBus.getDefault().post(CheckPEvent(addressListAdapter?.data?.get(position) as Address.Province))
                    }
                }
                1 -> {
                    val data = addressListAdapter?.data
                    if (!(data?.get(position) as Address.City).ckeck){
                        for (i in 0 until data?.size!!) {
                            val city = data?.get(i) as Address.City
                            city?.ckeck = (position == i)
                        }
                        addressListAdapter?.notifyDataSetChanged()

                        EventBus.getDefault().post(CheckCEvent(addressListAdapter?.data?.get(position) as Address.City))
                    }
                }
                2 -> {
                    val data = addressListAdapter?.data
                    if (!(data?.get(position) as Address.District).ckeck){
                        for (i in 0 until data?.size!!) {
                            val province = data?.get(i) as Address.District
                            province?.ckeck = (position == i)
                        }
                        addressListAdapter?.notifyDataSetChanged()

                        EventBus.getDefault().post(CheckDEvent(addressListAdapter?.data?.get(position) as Address.District))
                    }
                }
            }
        }
        mBinding?.rvAddress?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvAddress?.adapter = addressListAdapter

        if (p == 0) {
            mPresenter?.requestP()
        }
    }

    fun requestCity(pid:String){
        if (p == 1){
            mPresenter?.requestC(pid)
        }
    }

    fun requestDistrict(cid:String){
        if (p==2){
            mPresenter?.requestD(cid)
        }
    }


    override fun showP(provinceEntity: ProvinceEntity?) {
        provinceEntity?.provinceBeen?.let {
            val iAddress = ArrayList<IAddress>()
            iAddress.addAll(it)
            addressListAdapter?.setNewData(iAddress)
        }
    }

    override fun showC(cityEntity: CityEntity?) {
        cityEntity?.cityBeen?.let {
            val iAddress = ArrayList<IAddress>()
            iAddress.addAll(it)
            addressListAdapter?.setNewData(iAddress)
        }
    }

    override fun showD(districtEntity: DistrictEntity?) {
        districtEntity?.districtBeen?.let {
            val iAddress = ArrayList<IAddress>()
            iAddress.addAll(it)
            addressListAdapter?.setNewData(iAddress)
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }
}