package com.kzj.mall.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityPersonInfoBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerPersonInfoComponent
import com.kzj.mall.di.module.PersonInfoModule
import com.kzj.mall.entity.CustomerEntity
import com.kzj.mall.entity.ImageJsonEntity
import com.kzj.mall.event.LogoutEvent
import com.kzj.mall.image.Glide4Engine
import com.kzj.mall.mvp.contract.PersonInfoContract
import com.kzj.mall.mvp.presenter.PersonInfoPresenter
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.ui.dialog.EditDialog
import com.nanchen.compresshelper.CompressHelper
import com.upyun.library.common.Params
import com.upyun.library.common.UploadEngine
import com.upyun.library.listener.UpCompleteListener
import com.upyun.library.utils.UpYunUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import org.greenrobot.eventbus.EventBus
import permissions.dispatcher.*
import java.io.File
import java.util.*

@RuntimePermissions
class PersonInfoActivity : BaseActivity<PersonInfoPresenter, ActivityPersonInfoBinding>(), View.OnClickListener
        , PersonInfoContract.View {
    private val REQUEST_CAMERA = 123
    private val REQUEST_ALBUM = 1233

    //空间名
    var SPACE = "kzjimg01"
    //操作员
    var OPERATER = "kzj365mall"
    //密码
    var PASSWORD = "74h25%VhUjh7j#!"
    private var savePath = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_person_info
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerPersonInfoComponent.builder()
                .appComponent(appComponent)
                .personInfoModule(PersonInfoModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
                ?.init()
    }

    override fun initData() {
        mBinding?.tvLogout?.setOnClickListener(this)

        mBinding?.rlAvatar?.setOnClickListener(this)
        mBinding?.llNickName?.setOnClickListener(this)
        mBinding?.llBirthday?.setOnClickListener(this)

        mBinding?.rg?.setOnCheckedChangeListener { group, checkedId ->
            var sex = "0"
            when(checkedId){
                R.id.sex_nor->{
                    sex = "0"
                }
                R.id.sex_man->{
                    sex = "1"
                }
                R.id.sex_woman->{
                    sex = "2"
                }
            }

            val params = HashMap<String, String>()
            params.put("sex", sex)
            mPresenter?.updateInfo(params)
        }

        mPresenter?.customerInfo(true)
    }

    override fun showPersonInfo(info: CustomerEntity.CustAllInfo?) {
        info?.customer_img?.let {
            GlideApp.with(this)
                    .load(it)
                    .centerCrop()
                    .into(mBinding?.ivAvatar!!)
        }

        info?.customer_nickname?.let {
            mBinding?.tvNickName?.text = it
        }

        info?.info_birthday?.let {
            mBinding?.tvBirthday?.text = it.split(" ")[0]
        }

        info?.info_mobile?.let {
            mBinding?.tvMobile?.text = it
        }

        info?.info_gender?.let {
            //男
            if (it.equals("1") && mBinding?.sexMan?.isChecked == false) {
                mBinding?.sexMan?.isChecked = true
            }
            //女
            else if (it?.equals("2") && mBinding?.sexWoman?.isChecked == false) {
                mBinding?.sexWoman?.isChecked = true
            }
            //保密
            else if (it?.equals("0") && mBinding?.sexNor?.isChecked == false){
                mBinding?.sexNor?.isChecked = true
            }
        }
    }


    @NeedsPermission(Manifest.permission.CAMERA)
    fun requestPermission() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .capture(true)
                .captureStrategy(
                        CaptureStrategy(true, "com.kzj.mall.fileprovider"))
                .imageEngine(Glide4Engine())
                .countable(true)
                .maxSelectable(1)
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
            if (requestCode == REQUEST_CAMERA) {
                requestPermissionWithPermissionCheck()
            } else if (requestCode == REQUEST_ALBUM) {
                val obtainResult = Matisse.obtainPathResult(data)
                showLoadingDialog(false)
                startUpLoad(obtainResult?.get(0))
            }
        }
    }

    private fun startUpLoad(path: String?) {
        val file = File(path)

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
            updateAvatar(imageUrl)
        }, null)
    }


    private fun updateAvatar(imageUrl: String?) {
        imageUrl?.let {
            val params = HashMap<String, String>()
            params.put("customer_img", it)
            mPresenter?.updateInfo(params)
        }
    }

    override fun updateInfoSuccess() {
        mPresenter?.customerInfo(false)
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


    private fun showDateDialog() {
        val pvTime = TimePickerBuilder(this, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                val date2String = TimeUtils.date2String(date)
                val params = HashMap<String, String>()
                params.put("info_birthday", date2String)
                mPresenter?.updateInfo(params)
            }
        }).build();

        pvTime.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_logout -> {
                C.TOKEN = ""
                SPUtils.getInstance()?.put(C.SP_TOKEN, "")
                C.IS_LOGIN = false
                EventBus.getDefault().post(LogoutEvent())
                finish()
            }
            R.id.rl_avatar -> {
                requestPermissionWithPermissionCheck()
            }
            R.id.ll_birthday -> {
                showDateDialog()
            }
            R.id.ll_nick_name -> {
                val content = mBinding?.tvNickName?.text?.toString()
                EditDialog.newInstance(content)
                        .setOnConfirmClickListener(object :EditDialog.OnConfirmClickListener{
                            override fun onLeftClick() {
                            }

                            override fun onRightClick(content: String?) {
                                content?.let {
                                    val params = HashMap<String, String>()
                                    params.put("nickname", it)
                                    mPresenter?.updateInfo(params)
                                }
                            }
                        })
                        .show(supportFragmentManager)
            }
        }
    }
}