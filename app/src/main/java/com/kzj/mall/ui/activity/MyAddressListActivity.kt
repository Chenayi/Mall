package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
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
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.widget.RootLayout

class MyAddressListActivity : BaseActivity<MyAddressListPresenter, ActivityAddressListBinding>(), View.OnClickListener, MyAddressListContract.View {
    private val REQUEST_CODE_CREATE_ADDRESS = 101
    private var addressAdapter: AddressAdapter? = null
    private var addressId: String? = null
    private var updatePosition = 0
    private var changeDefaultPosition = 0

    private var isManager = false

    override fun onCreate(savedInstanceState: Bundle?) {
        intent?.getStringExtra("addressId")?.let {
            addressId = it
        }

        intent?.getBooleanExtra("isManager", false)?.let {
            isManager = it
        }
        super.onCreate(savedInstanceState)
    }

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

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        if (isManager) {
            mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
                    ?.init()
        } else {
            mImmersionBar?.fitsSystemWindows(true, R.color.fb)
                    ?.statusBarDarkFont(true, 0.5f)
                    ?.init()
        }
    }

    override fun initData() {
        if (isManager) {
            RootLayout.getInstance(this).setStatusBarViewColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setLeftIcon(R.mipmap.back_white)
                    .setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            RootLayout.getInstance(this).setStatusBarViewColor(ContextCompat.getColor(this, R.color.fb))
                    .setLeftIcon(R.mipmap.back_black)
                    .setTitleTextColor(ContextCompat.getColor(this, R.color.c_2e3033))
        }

        addressAdapter = AddressAdapter(ArrayList(), isManager)
        addressAdapter?.setEmptyView(R.layout.empty_view, mBinding?.rvAddress?.parent as ViewGroup)
        addressAdapter?.emptyView?.findViewById<ImageView>(R.id.iv_empty)?.setImageResource(R.mipmap.empty_address)
        addressAdapter?.emptyView?.findViewById<TextView>(R.id.tv_empty_msg)?.setText("一个地址都没有哦～")
        mBinding?.rvAddress?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvAddress?.adapter = addressAdapter


        if (!isManager) {
            addressAdapter?.setOnItemClickListener { adapter, view, position ->
                val item = addressAdapter?.getItem(position)
                val intent = Intent()
                intent.putExtra("address", item)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        addressAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view?.id) {
                R.id.ll_edit -> {
                    updatePosition = position
                    val address = addressAdapter?.getItem(position)
                    val intent = Intent(this, CreateAddressActivity::class.java)
                    intent?.putExtra("isManager", isManager)
                    intent?.putExtra("isUpdateAddress", true)
                    intent?.putExtra("address", address)
                    startActivityForResult(intent, REQUEST_CODE_CREATE_ADDRESS)
                }
                R.id.ll_default -> {
                    val item = addressAdapter?.getItem(position)
                    if (!item?.isDefault.equals("1")) {
                        mPresenter?.updateAddress(item?.province?.provinceId!!, item?.city?.cityId!!, item?.district?.districtId!!,
                                item?.addressName!!, item?.addressMoblie!!, item?.addressDetail!!, "1", item?.addressId!!)
                        changeDefaultPosition = position
                    }
                }
                R.id.ll_delete -> {
                    ConfirmDialog.newInstance("取消", "确定", "确定删除此地址？")
                            .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                                override fun onLeftClick() {
                                }

                                override fun onRightClick() {
                                    mPresenter?.deleteAddress(addressAdapter?.getItem(position)?.addressId)
                                }
                            })
                            .show(supportFragmentManager)
                }
            }
        }

        mBinding?.tvCreateAddress?.setOnClickListener(this)


        mBinding?.refreshLayout?.setOnRefreshListener {
            mPresenter?.requestAddress(false)
        }

        mPresenter?.requestAddress(true)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_ADDRESS) {
                mPresenter?.requestAddress(false)
//                val isUpdateAddress = data?.getBooleanExtra("isUpdateAddress", false)
//                data?.getSerializableExtra("address")?.let {
//                    val address = it as Address
//
//                    //更新地址
//                    if (isUpdateAddress == true) {
//                        addressAdapter?.data?.set(updatePosition, address)
//                        addressAdapter?.notifyItemChanged(updatePosition)
//                    }
//                    //新增地址
//                    else{
//                        addressAdapter?.addData(address)
//                    }
//
//                    if (address?.isDefault.equals("1")) {
//                        val d = addressAdapter?.data
//                        for (i in 0 until d?.size!!) {
//                            if (d?.get(i)?.isDefault.equals("1")) {
//                                d?.get(i)?.isDefault = "0"
//                                addressAdapter?.notifyItemChanged(i)
//                                return
//                            }
//                        }
//                    }
//                }
            }
        }
    }

    override fun deleteAddressSuccess() {
        mPresenter?.requestAddress(false)
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

    override fun addOrUpdateAddressSuccess(t: Address?) {
        for (i in 0 until addressAdapter?.data?.size!!) {
            if (addressAdapter?.data?.get(i)?.isDefault.equals("1")) {
                addressAdapter?.data?.get(i)?.isDefault = "0"
            }
        }
        val item = addressAdapter?.getItem(changeDefaultPosition)
        item?.isDefault = t?.isDefault
        addressAdapter?.notifyDataSetChanged()
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
                intent?.putExtra("isManager", isManager)
                startActivityForResult(intent, REQUEST_CODE_CREATE_ADDRESS)
            }
        }
    }
}