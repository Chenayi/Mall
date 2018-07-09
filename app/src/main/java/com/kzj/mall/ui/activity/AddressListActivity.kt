package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.AddressAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityAddressListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.DataHelper

class AddressListActivity  :BaseActivity<IPresenter,ActivityAddressListBinding>() {
    private var addressAdapter : AddressAdapter?= null
    override fun getLayoutId(): Int {
        return R.layout.activity_address_list
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        addressAdapter = AddressAdapter(DataHelper.addresses())
        addressAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp,
                mBinding?.rvAddress?.parent as ViewGroup,false))
        mBinding?.rvAddress?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvAddress?.adapter = addressAdapter
    }
}