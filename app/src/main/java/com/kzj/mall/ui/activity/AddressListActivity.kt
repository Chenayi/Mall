package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.AddressAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityAddressListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.utils.LocalDatas

class AddressListActivity : BaseActivity<IPresenter, ActivityAddressListBinding>(), View.OnClickListener {
    private val REQUEST_CODE_CREATE_ADDRESS = 101
    private var addressAdapter: AddressAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_address_list
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        addressAdapter = AddressAdapter(LocalDatas.addresses())
        addressAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp,
                mBinding?.rvAddress?.parent as ViewGroup, false))
        mBinding?.rvAddress?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvAddress?.adapter = addressAdapter
        addressAdapter?.setOnItemClickListener { adapter, view, position ->
            finish()
        }

        mBinding?.tvCreateAddress?.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_ADDRESS) {

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_create_address -> {
                val intent = Intent(this, CreateAddressActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_CREATE_ADDRESS)
            }
        }
    }
}