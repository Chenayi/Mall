package com.kzj.mall.ui.dialog

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogAskAnswerTypeBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import java.io.Serializable

class AskAnswerTypeDialog : BaseDialog<IPresenter, DialogAskAnswerTypeBinding>() {
    private var catList: MutableList<AskAnswerTypeEntity.CatList>? = null
    private var myAdapter: MyAdapter? = null
    private var onItemCheckListener: OnItemCheckListener? = null
    private var checkCatPosition = -1

    companion object {
        fun newInstance(datas: MutableList<AskAnswerTypeEntity.CatList>?, checkCatPosition: Int): AskAnswerTypeDialog {
            val askAnswerTypeDialog = AskAnswerTypeDialog()
            val b = Bundle()
            b.putSerializable("datas", datas as Serializable)
            b.putInt("checkCatPosition", checkCatPosition)
            askAnswerTypeDialog?.arguments = b
            return askAnswerTypeDialog
        }
    }

    override fun initData() {
        setMargin(10)

        catList = arguments?.getSerializable("datas") as MutableList<AskAnswerTypeEntity.CatList>
        checkCatPosition = arguments?.getInt("checkCatPosition", -1)!!

        catList?.let {
            for (i in 0 until it.size) {
                if (i == checkCatPosition) {
                    it?.get(i)?.check = true
                } else {
                    it?.get(i)?.check = false
                }
            }
            myAdapter = MyAdapter(it)
            myAdapter?.setOnItemClickListener { adapter, view, position ->
                onItemCheckListener?.onItemCheck(position, myAdapter?.getItem(position))
                dismiss()
            }
            mBinding?.rvType?.layoutManager = LinearLayoutManager(context)
            mBinding?.rvType?.adapter = myAdapter
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {

    }

    override fun intLayoutId() = R.layout.dialog_ask_answer_type


    class MyAdapter(catList: MutableList<AskAnswerTypeEntity.CatList>)
        : BaseAdapter<AskAnswerTypeEntity.CatList, BaseViewHolder>(R.layout.item_ask_type, catList) {
        override fun convert(helper: BaseViewHolder?, item: AskAnswerTypeEntity.CatList?) {
            helper?.setText(R.id.tv_type_name, item?.catName)
                    ?.setVisible(R.id.v_check, item?.check == true)
                    ?.setBackgroundColor(R.id.ll_bg, if (item?.check == true) Color.rgb(218,241,212) else Color.WHITE)
                    ?.setTextColor(R.id.tv_type_name, if (item?.check == true) ContextCompat.getColor(mContext, R.color.colorPrimary)
                    else ContextCompat.getColor(mContext, R.color.c_2e3033))
        }
    }

    fun setOnItemCheckListener(l: OnItemCheckListener): AskAnswerTypeDialog {
        onItemCheckListener = l
        return this
    }

    interface OnItemCheckListener {
        fun onItemCheck(position: Int, cat: AskAnswerTypeEntity.CatList?)
    }
}