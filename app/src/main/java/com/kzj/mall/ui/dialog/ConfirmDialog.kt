package com.kzj.mall.ui.dialog

import android.os.Bundle
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogTipsBinding
import com.kzj.mall.di.component.AppComponent

class ConfirmDialog : BaseDialog<IPresenter, DialogTipsBinding>(), View.OnClickListener {

    private var onConfirmClickListener: OnConfirmClickListener? = null

    private var leftText: String? = null
    private var rightText: String? = null
    private var content: String? = null

    companion object {
        fun newInstance(): ConfirmDialog {
            val confirmDialog = ConfirmDialog()
            return confirmDialog
        }

        fun newInstance(content: String): ConfirmDialog {
            val confirmDialog = ConfirmDialog()
            var arguments = Bundle()
            arguments?.putString("content", content)
            confirmDialog?.arguments = arguments
            return confirmDialog
        }

        fun newInstance(lefText: String, rightText: String, content: String): ConfirmDialog {
            val confirmDialog = ConfirmDialog()
            var arguments = Bundle()
            arguments?.putString("leftText", lefText)
            arguments?.putString("rightText", rightText)
            arguments?.putString("content", content)
            confirmDialog?.arguments = arguments
            return confirmDialog
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_tips
    }

    override fun initData() {
        setMargin(30)
        if (arguments != null) {
            leftText = arguments?.getString("leftText")
            rightText = arguments?.getString("rightText")
            content = arguments?.getString("content")
        }

        leftText?.let {
            mBinding?.tvLeft?.setCenterString(it)
        }

        rightText?.let {
            mBinding?.tvRight?.setCenterString(it)
        }

        content?.let {
            mBinding?.tvContent?.text = it
        }

        mBinding?.tvLeft?.setOnClickListener(this)
        mBinding?.tvRight?.setOnClickListener(this)
    }

    fun setOnConfirmClickListener(onConfirmClickListener: OnConfirmClickListener) : ConfirmDialog {
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
                onConfirmClickListener?.onRightClick()
                dismiss()
            }
        }
    }

    interface OnConfirmClickListener {
        fun onLeftClick()

        fun onRightClick()
    }
}