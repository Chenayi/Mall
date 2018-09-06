package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.AddressAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityAddressListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerMyAddressListComponent
import com.kzj.mall.di.module.MyAddressListModule
import com.kzj.mall.entity.address.Address
import com.kzj.mall.entity.address.AddressEntity
import com.kzj.mall.mvp.contract.MyAddressListContract
import com.kzj.mall.mvp.presenter.MyAddressListPresenter

class MyAddressListActivity : BaseActivity<MyAddressListPresenter, ActivityAddressListBinding>(), View.OnClickListener, MyAddressListContract.View {
    private val REQUEST_CODE_CREATE_ADDRESS = 101
    private var addressAdapter: AddressAdapter? = null
    private var addressId: String? = null
    private var updatePosition = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_address_list
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerMyAddressListComponent.builder()
                .appComponent(appComponent)
                .myAddressListModule(MyAddressListModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        intent?.getStringExtra("addressId")?.let {
            addressId = it
        }

        addressAdapter = AddressAdapter(ArrayList())
        addressAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp,
                mBinding?.rvAddress?.parent as ViewGroup, false))
        mBinding?.rvAddress?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvAddress?.adapter = addressAdapter
        addressAdapter?.setOnItemClickListener { adapter, view, position ->
            val item = addressAdapter?.getItem(position)
            val intent = Intent()
            intent.putExtra("address", item)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        addressAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view?.id) {
                R.id.iv_edit -> {
                    updatePosition = position
                    val address = addressAdapter?.getItem(position)
                    val intent = Intent(this, CreateAddressActivity::class.java)
                    intent?.putExtra("isUpdateAddress", true)
                    intent?.putExtra("address", address)
                    startActivityForResult(intent, REQUEST_CODE_CREATE_ADDRESS)
                }
            }
        }

        mBinding?.tvCreateAddress?.setOnClickListener(this)


        mBinding?.refreshLayout?.setOnRefreshListener {
            mPresenter?.requestAddress()
        }

        mPresenter?.requestAddress()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_ADDRESS) {
                val isUpdateAddress = data?.getBooleanExtra("isUpdateAddress", false)
                data?.getSerializableExtra("address")?.let {
                    val address = it as Address
                    if (isUpdateAddress == true) {
                        mPresenter?.requestAddress()
                    } else {
                        addressAdapter?.addData( address)
                    }
                }
            }
        }
    }

    override fun showAddress(addressEntity: AddressEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        addressEntity?.customerAddresses?.let {
            addressAdapter?.setNewData(it)
            addressId?.let {
                val datas = addressAdapter?.data
                for (i in 0 until datas?.size!!) {
                    val addresses = datas?.get(i)
                    addresses?.isCheck = (it.equals(datas?.get(i)?.addressId))
                }
                addressAdapter?.notifyDataSetChanged()
            }
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_create_address -> {
                val intent = Intent(this, CreateAddressActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_CREATE_ADDRESS)
            }
        }
    }
}