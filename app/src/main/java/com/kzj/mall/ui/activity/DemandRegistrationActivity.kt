package com.kzj.mall.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.RequestCode
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityDemandRegistrationBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerDemandRegistrationComponent
import com.kzj.mall.di.module.DemandRegistrationModule
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.ImageJsonEntity
import com.kzj.mall.entity.address.Address
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.utils.Utils
import permissions.dispatcher.*
import com.kzj.mall.image.Glide4Engine
import com.kzj.mall.mvp.contract.DemandRegistrationContract
import com.kzj.mall.mvp.presenter.DemandRegistrationPresenter
import com.kzj.mall.utils.FloatUtils
import com.nanchen.compresshelper.CompressHelper
import com.upyun.library.common.Params
import com.upyun.library.common.UploadEngine
import com.upyun.library.listener.UpCompleteListener
import com.upyun.library.utils.UpYunUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.io.File
import java.util.HashMap


/**
 * 处方药需求登记
 */
@RuntimePermissions
class DemandRegistrationActivity : BaseActivity<DemandRegistrationPresenter, ActivityDemandRegistrationBinding>()
        , View.OnClickListener, DemandRegistrationContract.View {
    private val REQUEST_CAMERA = 123
    private val REQUEST_ALBUM = 1233

    //空间名
    var SPACE = "kzjimg01"
    //操作员
    var OPERATER = "kzj365mall"
    //密码
    var PASSWORD = "74h25%VhUjh7j#!"
    private var savePath = ""

    private var buyEntity: BuyEntity? = null
    private var addressId: String? = null
    private var address: Address? = null
    private var hasAddress = false
    private var mGoodsNum = 1
    private var rxRecordType = "1"
    private var goodsinfoid: String? = null
    private var maxCount = 5

    private var mImages: MutableList<String>? = null
    private var mImageUrls = ArrayList<String>()
    private var curImagePosition = 0

    override fun getLayoutId() = R.layout.activity_demand_registration

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.white)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerDemandRegistrationComponent.builder()
                .appComponent(appComponent)
                .demandRegistrationModule(DemandRegistrationModule(this))
                .build()
                .inject(this)
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
        mBinding?.ivDelete1?.setOnClickListener(this)
        mBinding?.ivDelete2?.setOnClickListener(this)
        mBinding?.ivDelete3?.setOnClickListener(this)
        mBinding?.ivDelete4?.setOnClickListener(this)
        mBinding?.ivDelete5?.setOnClickListener(this)

        intent?.getSerializableExtra("buyEntity")?.let {
            buyEntity = it as BuyEntity
        }

        intent?.getIntExtra("goodsNum", 1)?.let {
            mGoodsNum = it
        }


        intent?.getStringExtra("rxRecordType")?.let {
            rxRecordType = it
        }

        intent?.getStringExtra("goodsinfoid")?.let {
            goodsinfoid = it
        }

        if (rxRecordType?.equals("1")) {
            maxCount = 1
        }

        mImages = ArrayList()

        //商品
        buyEntity?.goodsinfoMap?.let {
            GlideApp.with(this)
                    .load(it?.goods_img)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(mBinding?.ivGoods!!)

            mBinding?.tvGoodsName?.text = it?.goods_name
        }

        mBinding?.tvGoodsNum?.text = "x" + mGoodsNum

        //套餐
        if (rxRecordType?.equals("2") == true) {
            val combinationprice = buyEntity?.combinationprice?.toFloat()!!
            val sumPrice = combinationprice * mGoodsNum
            mBinding?.tvGoodsPrice?.text = "¥" + buyEntity?.combinationprice
            mBinding?.tvAllGoodsPrice?.text = "合计：¥" + FloatUtils.format(sumPrice)
        }
        //单品 疗程
        else {
            val price = buyEntity?.goodsinfoMap?.goods_price?.toFloat()!!
            val sumPrice = price * mGoodsNum
            mBinding?.tvGoodsPrice?.text = "¥" + buyEntity?.goodsinfoMap?.goods_price
            mBinding?.tvAllGoodsPrice?.text = "合计：¥" + FloatUtils.format(sumPrice)
        }

        //地址
        buyEntity?.caddress?.let {
            mBinding?.tvCreateAddress?.visibility = View.GONE
            mBinding?.llAddress?.visibility = View.VISIBLE
            setAddress(it)
        }

        mBinding?.rlAddress?.setOnClickListener(this)
        mBinding?.tvSubmitDemand?.setOnClickListener(this)
    }

    private fun setAddress(address: Address) {
        addressId = address?.addressId
        this.address = address
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

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun submitDemandSuccess() {
        ToastUtils.showShort("提交成功")
        finish()
    }


    @NeedsPermission(Manifest.permission.CAMERA)
    fun requestPermission() {
        if (mImages?.size!! >= maxCount) {
            return
        }

        Matisse.from(this)
                .choose(MimeType.ofImage())
                .capture(true)
                .captureStrategy(
                        CaptureStrategy(true, "com.kzj.mall.fileprovider"))
                .imageEngine(Glide4Engine())
                .countable(true)
                .maxSelectable(maxCount - mImages?.size!!)
                .forResult(REQUEST_ALBUM);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationale(request: PermissionRequest) {
        ConfirmDialog.newInstance("取消", "确定", "需要相机权限")
                .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                    override fun onLeftClick() {
                        request.cancel()
                    }

                    override fun onRightClick() {
                        request.proceed()
                    }
                })
                .setOutCancel(false)
                .show(supportFragmentManager)
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    internal fun showNeverAsk() {
        ConfirmDialog.newInstance("取消", "确定", "请到设置中照相机权限")
                .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                    override fun onRightClick() {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.fromParts("package", packageName, null)
                        startActivityForResult(intent, REQUEST_CAMERA)
                    }

                    override fun onLeftClick() {
                    }
                })
                .setOutCancel(false)
                .show(supportFragmentManager)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun showDenied() {
        requestPermissionWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
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
            } else if (requestCode == REQUEST_CAMERA) {
                requestPermissionWithPermissionCheck()
            } else if (requestCode == REQUEST_ALBUM) {
                val obtainResult = Matisse.obtainPathResult(data)
                mImages?.addAll(obtainResult)

                showLoadingDialog(false)
                startUpLoad()
            }
        }
    }


    private fun startUpLoad() {
        val file = File(mImages?.get(curImagePosition))

        var newFile = file
        if (file?.name?.endsWith(".png") == false) {
            newFile = CompressHelper.getDefault(this).compressToFile(file)
        }
        val paramsMap = HashMap<String, Any>()
        paramsMap[Params.BUCKET] = SPACE
        savePath = "" + System.currentTimeMillis() + ".jpg"
        paramsMap[Params.SAVE_KEY] = savePath
        paramsMap[Params.CONTENT_LENGTH] = newFile.length()
        UploadEngine.getInstance().formUpload(newFile, paramsMap, OPERATER, UpYunUtils.md5(PASSWORD), UpCompleteListener { isSuccess, result ->
            val imageJsonEntity = Gson().fromJson<ImageJsonEntity>(result, ImageJsonEntity::class.java)
            val imageUrl = C.SPACE_ADDRESS + imageJsonEntity?.url
            mImageUrls.add(imageUrl)

            if (curImagePosition == 0) {
                mBinding?.flImage1?.visibility = View.VISIBLE
                GlideApp.with(applicationContext)
                        .load(imageUrl)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(mBinding?.ivImage1!!)
                if (maxCount == 1) {
                    mBinding?.ivCamera?.visibility = View.GONE
                }
            } else if (curImagePosition == 1) {
                mBinding?.flImage2?.visibility = View.VISIBLE
                GlideApp.with(applicationContext)
                        .load(imageUrl)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(mBinding?.ivImage2!!)
            } else if (curImagePosition == 2) {
                GlideApp.with(applicationContext)
                        .load(imageUrl)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(mBinding?.ivImage3!!)
                mBinding?.flImage3?.visibility = View.VISIBLE
            } else if (curImagePosition == 3) {
                mBinding?.flImage4?.visibility = View.VISIBLE
                GlideApp.with(applicationContext)
                        .load(imageUrl)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(mBinding?.ivImage4!!)
            } else if (curImagePosition == 4) {
                mBinding?.flImag5?.visibility = View.VISIBLE
                mBinding?.ivCamera?.visibility = View.GONE
                GlideApp.with(applicationContext)
                        .load(imageUrl)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(mBinding?.ivImage5!!)
            }

            if (curImagePosition == mImages?.size!! - 1) {
                dismissLoadingDialog()
                return@UpCompleteListener
            }

            curImagePosition += 1
            startUpLoad()
        }, null)
    }

    private fun removeImage(positoin: Int) {
        mImages?.removeAt(positoin)
        mImageUrls?.removeAt(positoin)
        if (curImagePosition > 0) {
            curImagePosition -= 1
        }

        mBinding?.flImage1?.visibility = View.GONE
        mBinding?.flImage2?.visibility = View.GONE
        mBinding?.flImage3?.visibility = View.GONE
        mBinding?.flImage4?.visibility = View.GONE
        mBinding?.flImag5?.visibility = View.GONE
        mBinding?.ivCamera?.visibility = View.VISIBLE

        for (i in 0 until mImages?.size!!) {
            when (i) {
                0 -> {
                    mBinding?.flImage1?.visibility = View.VISIBLE
                    GlideApp.with(applicationContext)
                            .load(mImages?.get(i))
                            .placeholder(R.color.gray_default)
                            .centerCrop()
                            .into(mBinding?.ivImage1!!)
                }
                1 -> {
                    mBinding?.flImage2?.visibility = View.VISIBLE
                    GlideApp.with(applicationContext)
                            .load(mImages?.get(i))
                            .placeholder(R.color.gray_default)
                            .centerCrop()
                            .into(mBinding?.ivImage2!!)
                }
                2 -> {
                    mBinding?.flImage3?.visibility = View.VISIBLE
                    GlideApp.with(applicationContext)
                            .load(mImages?.get(i))
                            .placeholder(R.color.gray_default)
                            .centerCrop()
                            .into(mBinding?.ivImage3!!)
                }
                3 -> {
                    mBinding?.flImage4?.visibility = View.VISIBLE
                    GlideApp.with(applicationContext)
                            .load(mImages?.get(i))
                            .placeholder(R.color.gray_default)
                            .centerCrop()
                            .into(mBinding?.ivImage4!!)
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
            R.id.iv_camera -> {
                requestPermissionWithPermissionCheck()
            }
            R.id.iv_delete1 -> {
                removeImage(0)
            }
            R.id.iv_delete2 -> {
                removeImage(1)
            }
            R.id.iv_delete3 -> {
                removeImage(2)
            }
            R.id.iv_delete4 -> {
                removeImage(3)
            }
            R.id.iv_delete5 -> {
                removeImage(4)
            }
            R.id.tv_submit_demand -> {

                if (!hasAddress) {
                    ToastUtils.showShort("请先选择收货地址")
                    return
                }

                val goodsinfoMap = buyEntity?.goodsinfoMap
                val consignee = address?.addressName
                val goodsinfo = goodsinfoMap?.goods_name + " " + goodsinfoMap?.goods_spec


                val addressStr = address?.province?.provinceName + address?.city?.cityName +
                        address?.district?.districtName + address?.addressDetail


                val message = mBinding?.etMark?.text?.toString()?.trim()

                var rxImages = ""
                for (i in 0 until mImageUrls?.size!!) {
                    rxImages += mImageUrls?.get(i) + ","
                }

                if (rxImages?.length > 0) {
                    rxImages = rxImages?.substring(0, rxImages?.length - 1)
                }

                mPresenter?.submitDemand(rxRecordType, consignee, address?.addressMoblie, goodsinfo, goodsinfoid,
                        addressStr, mGoodsNum?.toString(), message, rxImages, addressId)
            }
        }
    }
}