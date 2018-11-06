package com.kzj.mall.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseListFragment
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerMyCollectComponent
import com.kzj.mall.di.module.MyCollectModule
import com.kzj.mall.entity.MyCollectEntity
import com.kzj.mall.mvp.contract.MyCollectContract
import com.kzj.mall.mvp.presenter.MyCollectPresenter
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.kzj.mall.ui.dialog.ConfirmDialog

/**
 * 我的收藏（关注）
 */
class MyCollectFragment : BaseListFragment<MyCollectPresenter, MyCollectEntity.FollowList>(), MyCollectContract.View {
    private var goodsType: Int? = null

    companion object {
        fun newInstance(goodsType: Int): MyCollectFragment {
            val myCollectFragment = MyCollectFragment()
            val b = Bundle()
            b.putInt("goodsType", goodsType)
            myCollectFragment?.arguments = b
            return myCollectFragment
        }
    }

    override fun initData() {
        arguments?.getInt("goodsType")?.let {
            goodsType = it
        }
        super.initData()

        listAdapter?.setOnItemLongClickListener { adapter, view, position ->

            ConfirmDialog.newInstance("取消", "确定", "确定删除此关注？")
                    .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                        override fun onLeftClick() {
                        }

                        override fun onRightClick() {
                            var ids = LongArray(1)
                            ids[0] = listAdapter?.getItem(position)?.followId!!
                            mPresenter?.deleteCollect(ids)
                        }

                    }).show(childFragmentManager)
            return@setOnItemLongClickListener true
        }

        pageNo = 1
        mPresenter?.myCollect(goodsType?.toString(), pageNo, C.PAGE_SIZE, false, true)
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerMyCollectComponent.builder()
                .appComponent(appComponent)
                .myCollectModule(MyCollectModule(this))
                .build()
                .inject(this)
    }

    override fun myCollect(goodsList: MutableList<MyCollectEntity.FollowList>?) {
        if (goodsList != null) {
            goodsList?.let {
                finishRefresh(it)
            }
        } else {
            finishRefresh(ArrayList())
        }
    }

    override fun moreMyCollect(goodsList: MutableList<MyCollectEntity.FollowList>?) {
        if (goodsList != null) {
            goodsList?.let {
                finishLoadMore(it)
            }
        } else {
            finishLoadMore(ArrayList())
        }
    }

    override fun deleteSuccess() {
        pageNo = 1
        mPresenter?.myCollect(goodsType?.toString(), pageNo, C.PAGE_SIZE, false, false)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun myHolder(helper: BaseViewHolder?, data: MyCollectEntity.FollowList) {


        GlideApp.with(context!!)
                .load(data?.good?.goodsImg)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)


        helper?.setText(R.id.tv_goods_name, data?.good?.goodsName)
                ?.setText(R.id.tv_goods_info_subtitle, Html.fromHtml(data?.good?.goodsInfoSubtitle))
                ?.setText(R.id.tv_goods_price, "¥" + data?.good?.goodsPrice)
    }

    override fun itemLayout() = R.layout.item_my_goods_collect

    override fun onRefresh() {
        pageNo = 1
        mPresenter?.myCollect(goodsType?.toString(), pageNo, C.PAGE_SIZE, false, false)
    }

    override fun onLoadMore() {
        pageNo += 1
        mPresenter?.myCollect(goodsType?.toString(), pageNo, C.PAGE_SIZE, true, false)
    }

    override fun emptyMsg() = "一个关注都还没有哦～"

    override fun emptyViewIcon() = R.mipmap.icon_empty_save

    override fun onItemClick(view: View, position: Int, data: MyCollectEntity.FollowList?) {
        val intent = Intent(context, GoodsDetailActivity::class.java)
        intent?.putExtra(C.GOODS_INFO_ID, data?.good?.goodsInfoId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}