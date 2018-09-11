package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.RequestCode
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityDemandRegistrationBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.address.Address
import com.kzj.mall.utils.Utils

/**
 * 处方药需求登记
 */
class DemandRegistrationActivity : BaseActivity<IPresenter, ActivityDemandRegistrationBinding>(), View.OnClickListener {

    private var buyEntity: BuyEntity? = null
    private var addressId: String? = null
    private var hasAddress = false

    override fun getLayoutId() = R.layout.activity_demand_registration

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.fb)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {

        val imageWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(56f)) / 5f
        val imageHeight = imageWidth

        mBinding?.flImage1?.layoutParams?.width = imageWidth?.toInt()
        mBinding?.flImage1?.layoutParams?.height = imageHeight?.toInt()
        mBinding?.flImage1?.requestLayout()

        mBinding?.flImage2?.layoutParams?.width = imageWidth?.toInt()
        mBinding?.flImage2?.layoutParams?.height = imageHeight?.toInt()
        mBinding?.flImage2?.requestLayout()

        mBinding?.flImage3?.layoutParams?.width = imageWidth?.toInt()
        mBinding?.flImage3?.layoutParams?.height = imageHeight?.toInt()
        mBinding?.flImage3?.requestLayout()

        mBinding?.flImage4?.layoutParams?.width = imageWidth?.toInt()
        mBinding?.flImage4?.layoutParams?.height = imageHeight?.toInt()
        mBinding?.flImage4?.requestLayout()

        mBinding?.flImag5?.layoutParams?.width = imageWidth?.toInt()
        mBinding?.flImag5?.layoutParams?.height = imageHeight?.toInt()
        mBinding?.flImag5?.requestLayout()

        mBinding?.flImage1?.visibility = View.GONE
        mBinding?.flImage2?.visibility = View.GONE
        mBinding?.flImage3?.visibility = View.GONE
        mBinding?.flImage4?.visibility = View.GONE
        mBinding?.flImag5?.visibility = View.GONE
        mBinding?.ivCamera?.visibility = View.VISIBLE
        mBinding?.ivCamera?.setOnClickListener(this)

        intent?.getSerializableExtra("buyEntity")?.let {
            buyEntity = it as BuyEntity
        }

        //商品
        buyEntity?.goodsinfoMap?.let {
            GlideApp.with(this)
                    .load(it?.goods_img)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(mBinding?.ivGoods!!)

            mBinding?.tvGoodsName?.text = it?.goods_name
            mBinding?.tvGoodsPrice?.text = "¥" + it?.goods_price
        }

        //地址
        buyEntity?.caddress?.let {
            mBinding?.tvCreateAddress?.visibility = View.GONE
            mBinding?.llAddress?.visibility = View.VISIBLE
            setAddress(it)
        }

        mBinding?.rlAddress?.setOnClickListener(this)
    }

    private fun setAddress(address: Address) {
        addressId = address?.addressId
        val provinceName = address?.province?.provinceName
        val cityName = address?.city?.cityName
        val districtName = address?.district?.districtName
        val addressDetail = address?.addressDetail
        mBinding?.tvAddress?.text = provinceName + "省" + cityName + "市" + districtName + addressDetail
        mBinding?.tvName?.text = address?.addressName
        mBinding?.tvMobile?.text = Utils.subMobile(address?.addressMoblie)
        address?.isDefault?.let {
            mBinding?.tvDefault?.visibility = if (it?.equals("1")) View.VISIBLE else View.GONE
        }

        hasAddress = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCode.CREATE_ADDRESS) {
                mBinding?.llAddress?.visibility = View.VISIBLE
                mBinding?.tvCreateAddress?.visibility = View.GONE
                data?.getSerializableExtra("address")?.let {
                    setAddress(it as Address)
                }
            } else if (requestCode == RequestCode.CHOOSE_ADDRESS) {
                data?.getSerializableExtra("address")?.let {
                    setAddress(it as Address)
                }
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_address -> {
                if (hasAddress) {
                    val intent = Intent(this, MyAddressListActivity::class.java)
                    intent?.putExtra("addressId", addressId)
                    startActivityForResult(intent, RequestCode.CHOOSE_ADDRESS)
                } else {
                    val intent = Intent(this, CreateAddressActivity::class.java)
                    intent?.putExtra("isManager", false)
                    startActivityForResult(intent, RequestCode.CREATE_ADDRESS)
                }
            }
            R.id.iv_camera->{

            }
        }
    }
}