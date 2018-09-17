package com.kzj.mall.ui.dialog

import android.os.Bundle
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogAskAnswerTypeBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import java.io.Serializable

class AskAnswerTypeDialog : BaseDialog<IPresenter,DialogAskAnswerTypeBinding>() {

    companion object {
        fun newInstance(datas:MutableList<AskAnswerTypeEntity.CatList>?):AskAnswerTypeDialog{
            val askAnswerTypeDialog = AskAnswerTypeDialog()
            val b = Bundle()
            b.putSerializable("datas",datas as Serializable)
            return askAnswerTypeDialog
        }
    }

    override fun initData() {
        setMargin(10)
    }

    override fun setUpComponent(appComponent: AppComponent?) {

    }

    override fun intLayoutId() = R.layout.dialog_ask_answer_type

}