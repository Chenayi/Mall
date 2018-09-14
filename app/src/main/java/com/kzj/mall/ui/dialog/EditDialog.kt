package com.kzj.mall.ui.dialog

import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogEditBinding
import com.kzj.mall.di.component.AppComponent

class EditDialog : BaseDialog<IPresenter, DialogEditBinding>(), View.OnClickListener {

    private var onConfirmClickListener: OnConfirmClickListener? = null

    companion object {
        fun newInstance(): EditDialog {
            val confirmDialog = EditDialog()
            return confirmDialog
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_edit
    }

    override fun initData() {
        setMargin(30)
        mBinding?.tvLeft?.setOnClickListener(this)
        mBinding?.tvRight?.setOnClickListener(this)

        mBinding?.etContent?.post {
            KeyboardUtils.showSoftInput(mBinding?.etContent)
        }
    }

    fun setOnConfirmClickListener(onConfirmClickListener: OnConfirmClickListener) : EditDialog {
        this.onConfirmClickListener = onConfirmClickListener
        return this
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_left->{
                onConfirmClickListener?.onLeftClick()
                dismiss()
            }
            R.id.tv_right->{
                val content = mBinding?.etContent?.text?.toString()?.trim()
                if (content?.length!! < 4){
                    ToastUtils.showShort("昵称长度不能小于4")
                    return
                }
                KeyboardUtils.hideSoftInput(mBinding?.etContent)
                onConfirmClickListener?.onRightClick(content)
                dismiss()
            }
        }
    }

    interface OnConfirmClickListener {
        fun onLeftClick()

        fun onRightClick(content:String?)
    }
}