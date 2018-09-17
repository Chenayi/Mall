package com.kzj.mall.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogEditBinding
import com.kzj.mall.di.component.AppComponent

class EditDialog : BaseDialog<IPresenter, DialogEditBinding>(), View.OnClickListener {
    private var content: String? = null
    private var onConfirmClickListener: OnConfirmClickListener? = null

    companion object {
        fun newInstance(content: String?): EditDialog {
            val confirmDialog = EditDialog()
            val b = Bundle()
            b.putString("content", content)
            confirmDialog?.arguments = b
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

        arguments?.getString("content")?.let {
            content = it
            mBinding?.etContent?.setText(it)
            mBinding?.etContent?.setSelection(it?.length)
        }

        mBinding?.tvLeft?.setOnClickListener(this)
        mBinding?.tvRight?.setOnClickListener(this)

        mBinding?.etContent?.post {
            KeyboardUtils.showSoftInput(mBinding?.etContent)
        }
    }

    fun setOnConfirmClickListener(onConfirmClickListener: OnConfirmClickListener): EditDialog {
        this.onConfirmClickListener = onConfirmClickListener
        return this
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_left -> {
                onConfirmClickListener?.onLeftClick()
                KeyboardUtils.hideSoftInput(mBinding?.etContent)
                dismiss()
            }
            R.id.tv_right -> {
                val content = mBinding?.etContent?.text?.toString()?.trim()
                if (content?.length!! < 4) {
                    ToastUtils.showShort("昵称长度不能小于4")
                    return
                }
                KeyboardUtils.hideSoftInput(mBinding?.etContent)
                onConfirmClickListener?.onRightClick(content)
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        KeyboardUtils.hideSoftInput(mBinding?.etContent)
        super.onDismiss(dialog)
    }

    interface OnConfirmClickListener {
        fun onLeftClick()

        fun onRightClick(content: String?)
    }
}