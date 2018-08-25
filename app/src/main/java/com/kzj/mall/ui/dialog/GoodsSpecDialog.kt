package com.kzj.mall.ui.dialog

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.databinding.DialogGoodsSpecBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerGoodsSpecComponent
import com.kzj.mall.di.module.GoodsSpeclModule
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.event.CombinationEvent
import com.kzj.mall.event.PacketListEvent
import com.kzj.mall.mvp.contract.GoodsSpecContract
import com.kzj.mall.mvp.presenter.GoodsSpecPresenter
import com.kzj.mall.widget.SuperFlowLayout
import org.greenrobot.eventbus.EventBus


class GoodsSpecDialog : BaseDialog<GoodsSpecPresenter, DialogGoodsSpecBinding>(), View.OnClickListener, GoodsSpecContract.View {
    private var goodsDetailEntity: GoodsDetailEntity? = null
    private var mGoodsDefaultInfoId: String? = null

    companion object {
        fun newInstance(goodsDefaultInfoId: String?, goodsDetailEntity: GoodsDetailEntity?): GoodsSpecDialog {
            val goodsSpecDialog = GoodsSpecDialog()
            val arguments = Bundle()
            arguments?.putString("goodsDefaultInfoId", goodsDefaultInfoId)
            arguments?.putSerializable("goodsDetailEntity", goodsDetailEntity)
            goodsSpecDialog?.arguments = arguments
            return goodsSpecDialog
        }
    }

    override fun initData() {
        mBinding?.rlRoot?.layoutParams?.height = (ScreenUtils.getScreenHeight() * 0.65f).toInt()

        mGoodsDefaultInfoId = arguments?.getString("goodsDefaultInfoId")
        goodsDetailEntity = arguments?.getSerializable("goodsDetailEntity") as GoodsDetailEntity?

        setSpecGroup(goodsDetailEntity)

        mBinding?.ivPlus?.setOnClickListener(this)
        mBinding?.ivMinus?.setOnClickListener(this)
        mBinding?.ivClose?.setOnClickListener(this)
    }

    /**
     * 规格 , 疗程套餐
     */
    private fun setSpecGroup(goodsDetailEntity: GoodsDetailEntity?) {
        //图片
        GlideApp.with(context!!)
                .load(goodsDetailEntity?.gn?.goodsImgs?.get(0))
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(mBinding?.ivGoods as ImageView)

        //价格
        mBinding?.tvGoodsPrice?.setText("合计：¥"+goodsDetailEntity?.gn?.goodsPrice)
        mBinding?.tvPreGoodsPrice?.setCenterString("立省：0")

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

        //疗程
        val packageList = goodsDetailEntity?.packageList
        val packet = GoodsDetailEntity.PackageList()
        packet?.combination_name = "一盒标准装"
        packet?.package_count = 1
        packet?.goods_info_id = mGoodsDefaultInfoId
        packageList?.add(0, packet)
        //组合套餐
        val combinationList = goodsDetailEntity?.combinationList

        val iGruops = ArrayList<GoodsDetailEntity.IGroup>()
        packageList?.let {
            iGruops.addAll(it)
        }
        combinationList?.let {
            iGruops.addAll(it)
            if (it.size > 0) {
                mBinding?.ivPlus?.isEnabled = false
                mBinding?.ivMinus?.isEnabled = false
            } else {
                mBinding?.ivPlus?.isEnabled = true
                mBinding?.ivMinus?.isEnabled = true
            }
        }


        if (iGruops?.size == 0) {
            mBinding?.llGoodsGroup?.visibility = View.GONE
        } else {
            mBinding?.llGoodsGroup?.visibility = View.VISIBLE
            val groups = ArrayList<String>()
            for (i in 0 until iGruops?.size) {
                val iGroup = iGruops?.get(i)
                if (iGroup is GoodsDetailEntity.PackageList) {
                    groups.add(iGroup.combination_name!!)
                } else if (iGroup is GoodsDetailEntity.CombinationList) {
                    groups.add(iGroup.combination_name!!)
                }
            }

            mBinding?.sflGoodsGroup?.setDatas(groups)
            mBinding?.sflGoodsGroup?.switchTag(0)
            mBinding?.sflGoodsGroup?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
                override fun onTagClick(position: Int, tag: String?) {
                    val iGroup = iGruops?.get(position)
                    var packetCount = 1
                    if (iGroup is GoodsDetailEntity.PackageList) {
                        if (iGroup.package_count != null && iGroup.package_count!! > 0) {
                            packetCount = iGroup.package_count!!
                        }
                        EventBus.getDefault().post(PacketListEvent(iGroup))
                    } else if (iGroup is GoodsDetailEntity.CombinationList) {
                        if (iGroup.package_count != null && iGroup.package_count!! > 0) {
                            packetCount = iGroup.package_count!!
                        }
                        EventBus.getDefault().post(CombinationEvent(iGroup))
                    }

                    mBinding?.tvNum?.text = packetCount?.toString()
                }
            })
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
        DaggerGoodsSpecComponent.builder()
                .appComponent(appComponent)
                .goodsSpeclModule(GoodsSpeclModule(this))
                .build()
                .inject(this)
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


    override fun showGoodsDetail(goodsDetailEntity: GoodsDetailEntity?) {
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