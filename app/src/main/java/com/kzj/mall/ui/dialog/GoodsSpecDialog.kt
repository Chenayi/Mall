package com.kzj.mall.ui.dialog

import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogGoodsSpecBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.widget.SuperFlowLayout


class GoodsSpecDialog : BaseDialog<IPresenter, DialogGoodsSpecBinding>(), View.OnClickListener {

    companion object {
        fun newInstance(): GoodsSpecDialog {
            val goodsSpecDialog = GoodsSpecDialog()
            return goodsSpecDialog
        }
    }

    override fun initData() {
        mBinding?.rlRoot?.layoutParams?.height = (ScreenUtils.getScreenHeight() * 0.65f).toInt()


        val tags = specTags()
        mBinding?.sflGoodsSpec?.setDatas(tags)
        mBinding?.sflGoodsSpec?.switchTag(0)
        mBinding?.sflGoodsSpec?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
            override fun onTagClick(position: Int, tag: String?) {
                mBinding?.sflGoodsGroup?.reset()
            }
        })


        val groups = goodsgroup()
        mBinding?.sflGoodsGroup?.setDatas(groups)
        mBinding?.sflGoodsGroup?.switchTag(0)
        mBinding?.sflGoodsGroup?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
            override fun onTagClick(position: Int, tag: String?) {

            }
        })


        mBinding?.ivPlus?.setOnClickListener(this)
        mBinding?.ivMinus?.setOnClickListener(this)
        mBinding?.ivClose?.setOnClickListener(this)
    }


    private fun specTags(): MutableList<String> {
        val specTags = ArrayList<String>()
        specTags.add("20ml*48支")
        specTags.add("20ml*12支")
        return specTags
    }

    private fun goodsgroup(): MutableList<String> {
        val goodsgroup = ArrayList<String>()
        goodsgroup.add("一盒标准装")
        goodsgroup.add("2盒起288元/盒")
        goodsgroup.add("4盒起278元/盒")
        return goodsgroup
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_goods_spec
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_plus -> plus()
            R.id.iv_minus -> minus()
            R.id.iv_close -> dismiss()
        }
    }

    /**
     * 数量 -
     */
    private fun minus() {
        var num = mBinding?.tvNum?.text?.toString()?.toInt()
        num?.let {
            if (it > 1) {
                mBinding?.tvNum?.text = (it - 1).toString()
            }
        }
    }

    /**
     * 数量 +
     */
    private fun plus() {
        var num = mBinding?.tvNum?.text?.toString()?.toInt()
        num?.let {
            if (it < 9999) {
                mBinding?.tvNum?.text = (it + 1).toString()
            }
        }
    }
}