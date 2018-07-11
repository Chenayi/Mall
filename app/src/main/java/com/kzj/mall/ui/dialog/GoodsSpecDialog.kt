package com.kzj.mall.ui.dialog

import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogGoodsSpecBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.widget.SuperFlowLayout


class GoodsSpecDialog : BaseDialog<IPresenter, DialogGoodsSpecBinding>() {
    var tags: MutableList<String> = ArrayList()

    companion object {
        fun newInstance(): GoodsSpecDialog {
            val goodsSpecDialog = GoodsSpecDialog()
            return goodsSpecDialog
        }
    }

    override fun initData() {
        mBinding?.rlRoot?.layoutParams?.height = (ScreenUtils.getScreenHeight() * 0.65f).toInt()


        tags.clear()
        tags.addAll(specTags())
        mBinding?.sfl?.setDatas(tags)
        mBinding?.sfl?.switchTag(0)
        mBinding?.sfl?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
            override fun onTagClick(position: Int, tag: String?) {
                ToastUtils.showShort(position.toString() + " , " + tag)
            }
        })
    }


    private fun specTags(): MutableList<String> {
        val specTags = ArrayList<String>()
        specTags.add("20ml*48支")
        specTags.add("20ml*12支")
        return specTags
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_goods_spec
    }

}