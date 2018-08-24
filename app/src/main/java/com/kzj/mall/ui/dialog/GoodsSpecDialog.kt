package com.kzj.mall.ui.dialog

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogGoodsSpecBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.widget.SuperFlowLayout


class GoodsSpecDialog : BaseDialog<IPresenter, DialogGoodsSpecBinding>(), View.OnClickListener {

    private var goodsDetailEntity: GoodsDetailEntity? = null

    companion object {
        fun newInstance(goodsDetailEntity: GoodsDetailEntity?): GoodsSpecDialog {
            val goodsSpecDialog = GoodsSpecDialog()
            val arguments = Bundle()
            arguments?.putSerializable("goodsDetailEntity", goodsDetailEntity)
            goodsSpecDialog?.arguments = arguments
            return goodsSpecDialog
        }
    }

    override fun initData() {
        mBinding?.rlRoot?.layoutParams?.height = (ScreenUtils.getScreenHeight() * 0.65f).toInt()

        goodsDetailEntity = arguments?.getSerializable("goodsDetailEntity") as GoodsDetailEntity?

        //规格
        goodsDetailEntity?.openSpec?.let {
            if (it?.size > 0) {
                val tags = ArrayList<String>()
                for (i in 0 until it?.size) {
                    tags.add(it.get(i).goodsSpec!!)
                }

                mBinding?.sflGoodsSpec?.setDatas(tags)
                mBinding?.sflGoodsSpec?.switchTag(0)
                mBinding?.sflGoodsSpec?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
                    override fun onTagClick(position: Int, tag: String?) {
                        mBinding?.sflGoodsGroup?.reset()
                    }
                })
            }
        }

        //组合套餐
        val combinationList = goodsDetailEntity?.combinationList
        //疗程
        val packageList = goodsDetailEntity?.packageList


        if (combinationList?.size == 0 && packageList?.size == 0) {
            mBinding?.llGoodsGroup?.visibility = View.GONE
        } else {
            mBinding?.llGoodsGroup?.visibility = View.VISIBLE
            val groups = ArrayList<String>()
            groups.add("一盒标准装")
            if (packageList?.size != null && packageList?.size!! > 0) {
                for (i in 0 until packageList?.size!!) {
                    groups.add(packageList?.get(i)?.combination_name!!)
                }
            }
            if (combinationList?.size != null && combinationList?.size!! > 0) {
                for (i in 0 until combinationList?.size!!) {
                    groups.add(combinationList?.get(i)?.combination_name!!)
                }
            }
            mBinding?.sflGoodsGroup?.setDatas(groups)
            mBinding?.sflGoodsGroup?.switchTag(0)
            mBinding?.sflGoodsGroup?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
                override fun onTagClick(position: Int, tag: String?) {

                }
            })
        }

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