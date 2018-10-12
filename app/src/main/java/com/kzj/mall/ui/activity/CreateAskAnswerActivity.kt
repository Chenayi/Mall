package com.kzj.mall.ui.activity

import android.text.TextUtils
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityCreateAskAnswerBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerCreateAskAnswerComponent
import com.kzj.mall.di.module.CreateAskAnswerModule
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import com.kzj.mall.mvp.contract.CreateAskAnswerContract
import com.kzj.mall.mvp.presenter.CreateAskAnswerPresenter
import com.kzj.mall.ui.dialog.AskAnswerTypeDialog
import java.text.SimpleDateFormat
import java.util.*

/**
 * 提问
 */
class CreateAskAnswerActivity : BaseActivity<CreateAskAnswerPresenter, ActivityCreateAskAnswerBinding>(), View.OnClickListener, CreateAskAnswerContract.View {

    private var catList: MutableList<AskAnswerTypeEntity.CatList>? = null

    private var checkCat: AskAnswerTypeEntity.CatList? = null
    private var checkCatPosition = -1

    private var sex = "男"

    override fun getLayoutId() = R.layout.activity_create_ask_answer

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerCreateAskAnswerComponent.builder()
                .appComponent(appComponent)
                .createAskAnswerModule(CreateAskAnswerModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        mBinding?.rlType?.setOnClickListener(this)
        mBinding?.tvSubmit?.setOnClickListener(this)
        mBinding?.llAge?.setOnClickListener(this)
        mBinding?.rgSex?.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_sex_man) {
                sex = "男"
            } else {
                sex = "女"
            }
        }
    }

    override fun keyboardEnable(): Boolean {
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_type -> {
                if (catList == null) {
                    mPresenter?.interlucationType()
                } else {
                    AskAnswerTypeDialog.newInstance(catList, checkCatPosition)
                            .setOnItemCheckListener(object : AskAnswerTypeDialog.OnItemCheckListener {
                                override fun onItemCheck(position: Int, cat: AskAnswerTypeEntity.CatList?) {
                                    mBinding?.tvType?.text = cat?.catName
                                    checkCat = cat
                                    checkCatPosition = position
                                }
                            })
                            .show(supportFragmentManager)
                }
            }

            R.id.ll_age -> {
                showDateDialog()
            }

            R.id.tv_submit -> {
                val confirm = confirm()
                if (confirm) {
                    val content = mBinding?.etContent?.text?.toString()?.trim()
                    val age = mBinding?.tvAge?.text?.toString()?.trim()
                    mPresenter?.saveInterlucation(checkCat?.catId, content, age, sex)
                }
            }
        }
    }

    private fun showDateDialog() {
        val type = BooleanArray(6)
        type[0] = true
        type[1] = false
        type[2] = false
        type[3] = false
        type[4] = false
        type[5] = false
        val pvTime = TimePickerBuilder(this, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                val date2String = TimeUtils.date2String(date, SimpleDateFormat("yyyy"))
                mBinding?.tvAge?.text = date2String
            }
        })
                .setType(type)
                .build();

        pvTime.show()
    }

    private fun confirm(): Boolean {

        if (checkCat == null) {
            ToastUtils.showShort("请选择分类")
            return false
        }

        val content = mBinding?.etContent?.text?.toString()?.trim()
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort("请填写咨询问题")
            return false
        }

//        val name = mBinding?.etName?.text?.toString()?.trim()
//        if (TextUtils.isEmpty(name)) {
//            ToastUtils.showShort("请填写姓名")
//            return false
//        }

        val age = mBinding?.tvAge?.text?.toString()?.trim()
        if (TextUtils.isEmpty(age)) {
            ToastUtils.showShort("请选择年龄")
            return false
        }

        return true
    }

    override fun showInterlucationType(types: MutableList<AskAnswerTypeEntity.CatList>?) {
        AskAnswerTypeDialog.newInstance(types, checkCatPosition)
                .setOnItemCheckListener(object : AskAnswerTypeDialog.OnItemCheckListener {
                    override fun onItemCheck(position: Int, cat: AskAnswerTypeEntity.CatList?) {
                        mBinding?.tvType?.text = cat?.catName
                        checkCat = cat
                        checkCatPosition = position
                    }
                })
                .show(supportFragmentManager)
        catList = types
    }

    override fun saceInterlucationSuccess() {
        ToastUtils.showShort("已提交完成")
        finish()
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