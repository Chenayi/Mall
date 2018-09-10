package com.kzj.mall.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.GoodsGroupViewBinding
import com.kzj.mall.entity.GoodsDetailEntity
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

class GoodsGroupView : BaseRelativeLayout<GoodsGroupViewBinding> {
    private var groupAdapter: GroupAdapter? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId() = R.layout.goods_group_view

    private var openPosition = 0

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        groupAdapter = GroupAdapter(ArrayList())
        mBinding?.rvGroup?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvGroup?.adapter = groupAdapter

        groupAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view?.id) {
                R.id.iv_down -> {
                    groupAdapter?.getItem(position)?.isOpen = true
                    groupAdapter?.getItem(openPosition)?.isOpen = false
                    groupAdapter?.notifyItemChanged(position)
                    groupAdapter?.notifyItemChanged(openPosition)
                    openPosition = position
                }
            }
        }
    }

    fun setNewDatas(datas: MutableList<GoodsDetailEntity.CombinationList>?) {
        datas?.let {
            if (it?.size > 0){
                for (i in 0 until it?.size){
                    if (i == 0){
                        it?.get(i)?.isOpen = true
                    }else{
                        it?.get(i)?.isOpen = false
                    }
                }
                groupAdapter?.setNewData(it)
            }
        }
    }


    class GroupAdapter(combinationList: MutableList<GoodsDetailEntity.CombinationList>)
        : BaseAdapter<GoodsDetailEntity.CombinationList, BaseViewHolder>(R.layout.item_goods_group, combinationList) {
        override fun convert(helper: BaseViewHolder?, item: GoodsDetailEntity.CombinationList?) {

            helper?.setText(R.id.tv_group_name, item?.combination_name)
                    ?.setText(R.id.tv_goods_price, "优惠价：¥" + item?.combination_price?.toString())
                    ?.setText(R.id.tv_goods_pre_price, "立省：¥" + item?.sumPrePrice)
                    ?.setGone(R.id.tv_group_add_cart, item?.isOpen == true)
                    ?.setGone(R.id.rv_group, item?.isOpen == true)
                    ?.setGone(R.id.iv_down, item?.isOpen == false)
                    ?.addOnClickListener(R.id.iv_down)

            val rvGroup = helper?.getView<MultiSnapRecyclerView>(R.id.rv_group)
            item?.ggList?.let {
                val goodsAdapter = GoodsAdapter(it)
                rvGroup?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                rvGroup?.adapter = goodsAdapter
            }
        }
    }


    class GoodsAdapter(goods: MutableList<GoodsDetailEntity.GGList>)
        : BaseAdapter<GoodsDetailEntity.GGList, BaseViewHolder>(R.layout.item_goods_detail_group, goods) {
        override fun convert(helper: BaseViewHolder?, item: GoodsDetailEntity.GGList?) {
            val linearLayout = helper?.getView<LinearLayout>(R.id.ll_item)
            var params: RelativeLayout.LayoutParams = linearLayout?.layoutParams as RelativeLayout.LayoutParams

            params.leftMargin = SizeUtils.dp2px(10f)
            if (helper?.layoutPosition == data?.size - 1) {
                params.rightMargin = SizeUtils.dp2px(10f)
                helper?.setGone(R.id.tv_plus, false)
            } else {
                params.rightMargin = 0
                helper?.setGone(R.id.tv_plus, true)
            }
            linearLayout.layoutParams = params

            GlideApp.with(mContext)
                    .load(item?.goods_img)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_goods)!!)

            helper?.setText(R.id.tv_num, item?.goodsNum + "盒")
                    ?.setText(R.id.tv_goods_name, item?.goods_name)
                    ?.setText(R.id.tv_goods_price_num, "¥" + item?.goods_price + "x" + item?.goodsNum)
        }
    }
}