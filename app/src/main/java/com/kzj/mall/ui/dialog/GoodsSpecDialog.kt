package com.kzj.mall.ui.dialog

import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogGoodsSpecBinding
import com.kzj.mall.di.component.AppComponent
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.zhy.view.flowlayout.TagFlowLayout


class GoodsSpecDialog : BaseDialog<IPresenter, DialogGoodsSpecBinding>() {
    var  tags : MutableList<String> =ArrayList()

    companion object {
        fun newInstance(): GoodsSpecDialog {
            val goodsSpecDialog = GoodsSpecDialog()
            return goodsSpecDialog
        }
    }

    private val tagAdapter = object : TagAdapter<String>(tags){
        override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
            val tv = layoutInflater.inflate(R.layout.tag_text,
                    mBinding?.idFlowlayout, false) as TextView
            tv?.setText(t)
            return tv
        }
        override fun onSelected(position: Int, view: View?) {
//            super.onSelected(position, view)
            view?.setBackgroundResource(R.drawable.background_f0_corners_9999)
        }

        override fun unSelected(position: Int, view: View?) {
            super.unSelected(position, view)
            view?.setBackgroundResource(R.drawable.background_green_stroke_corners_9999)
        }
    }

    override fun initData() {
        mBinding?.rlRoot?.layoutParams?.height = (ScreenUtils.getScreenHeight() * 0.65f).toInt()


        mBinding?.idFlowlayout?.adapter = tagAdapter
        tags.clear()
        tags.addAll(specTags())
        tagAdapter?.notifyDataChanged()

        mBinding?.idFlowlayout?.setOnTagClickListener(object : TagFlowLayout.OnTagClickListener{
            override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
                ToastUtils.showShort(tags!![position])
                return true
            }
        })
//        tagAdapter?.setSelected(0,tags!![0])
    }


    private fun specTags():MutableList<String>{
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