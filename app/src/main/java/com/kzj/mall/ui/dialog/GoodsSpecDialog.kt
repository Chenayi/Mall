package com.kzj.mall.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.databinding.DialogGoodsSpecBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerGoodsSpecComponent
import com.kzj.mall.di.module.GoodsSpeclModule
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.event.*
import com.kzj.mall.mvp.contract.GoodsSpecContract
import com.kzj.mall.mvp.presenter.GoodsSpecPresenter
import com.kzj.mall.ui.activity.login.LoginActivity
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.widget.SuperFlowLayout
import org.greenrobot.eventbus.EventBus


class GoodsSpecDialog : BaseDialog<GoodsSpecPresenter, DialogGoodsSpecBinding>(), View.OnClickListener, GoodsSpecContract.View {
    private var goodsDetailEntity: GoodsDetailEntity? = null
    private var mGoodsDefaultInfoId: String? = null
    private var iGruops: MutableList<GoodsDetailEntity.IGroup>? = null
    private var packageList: MutableList<GoodsDetailEntity.PackageList>? = null

    /**
     * 是否套餐
     */
    private var isCombination = false

    /**
     * 规格选中位置
     */
    private var specPosition = 0

    /**
     * 疗程套装选中位置
     */
    private var groupPosition = 0

    /**
     * 商品数量
     */
    private var mGoodsNum = 1

    companion object {
        fun newInstance(goodsNum: Int?, specPosition: Int?, groupPosition: Int?, goodsDefaultInfoId: String?, goodsDetailEntity: GoodsDetailEntity?): GoodsSpecDialog {
            val goodsSpecDialog = GoodsSpecDialog()
            val arguments = Bundle()
            arguments?.putInt("goodsNum", goodsNum!!)
            arguments?.putInt("specPosition", specPosition!!)
            arguments?.putInt("groupPosition", groupPosition!!)
            arguments?.putString("goodsDefaultInfoId", goodsDefaultInfoId)
            arguments?.putSerializable("goodsDetailEntity", goodsDetailEntity)
            goodsSpecDialog?.arguments = arguments
            return goodsSpecDialog
        }
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_goods_spec
    }

    override fun initData() {
        mBinding?.rlRoot?.layoutParams?.height = (ScreenUtils.getScreenHeight() * 0.65f).toInt()

        mGoodsDefaultInfoId = arguments?.getString("goodsDefaultInfoId")
        arguments?.getSerializable("goodsDetailEntity")?.let {
            goodsDetailEntity = it as GoodsDetailEntity?
        }

        arguments?.getInt("specPosition")?.let {
            specPosition = it
        }

        arguments?.getInt("groupPosition")?.let {
            groupPosition = it
        }

        arguments?.getInt("goodsNum")?.let {
            mGoodsNum = it
        }

        val goodsStock = goodsDetailEntity?.gn?.goodsStock!!
        if (goodsStock <= 0) {
            mBinding?.tvNoStock?.visibility = View.VISIBLE
            mBinding?.tvRequestCheckin?.visibility = View.GONE
            mBinding?.llBottom?.visibility = View.GONE
        } else {
            mBinding?.tvNoStock?.visibility = View.GONE
            goodsDetailEntity?.gn?.goodsType?.let {
                //处方
                if (it.equals("0")) {
                    mBinding?.llBottom?.visibility = View.GONE
                    mBinding?.tvRequestCheckin?.visibility = View.VISIBLE
                }

                //非处方
                else {
                    mBinding?.llBottom?.visibility = View.VISIBLE
                    mBinding?.tvRequestCheckin?.visibility = View.GONE
                }
            }
        }

        //数量
        mBinding?.tvNum?.text = mGoodsNum?.toString()


        //规格
        mBinding?.sflGoodsSpec?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
            override fun onTagClick(position: Int, tag: String?) {
                mPresenter.requesrGoodsDetail(position, tag, goodsDetailEntity?.openSpec?.get(position)?.goodsId)
            }
        })
        mBinding?.sflGoodsGroup?.setOnTagClickListener(object : SuperFlowLayout.OnTagClickListener {
            override fun onTagClick(position: Int, tag: String?) {
                val iGroup = iGruops?.get(position)
                var packetCount = 1
                if (iGroup is GoodsDetailEntity.PackageList) {
                    isCombination = false
                    if (iGroup.package_count != null && iGroup.package_count!! > 0) {
                        packetCount = iGroup.package_count!!
                    }
                    if (position > 0) {
                        mBinding?.tvPreGoodsPrice?.visibility = View.VISIBLE
                        mBinding?.tvGroupName?.text = "疗程装"
                    } else {
                        mBinding?.tvPreGoodsPrice?.visibility = View.INVISIBLE
                        mBinding?.tvGroupName?.text = "标准装"
                    }

                    EventBus.getDefault().post(GoodsNumChangeEvent(iGroup?.package_count))
                    setPackagesPrice(position, iGroup?.goods_info_id!!, iGroup?.combination_unit_price, iGroup?.package_count)
                } else if (iGroup is GoodsDetailEntity.CombinationList) {
                    isCombination = true
                    if (iGroup.package_count != null && iGroup.package_count!! > 0) {
                        packetCount = iGroup.package_count!!
                    }
                    mBinding?.tvGoodsPrice?.setText("合计：¥" + FloatUtils.format(iGroup.combination_price))
                    mBinding?.tvPreGoodsPrice?.visibility = View.VISIBLE
                    mBinding?.tvPreGoodsPrice?.setCenterString("立省：¥" + FloatUtils.format(iGroup.sumPrePrice))
                    mBinding?.tvGroupName?.text = "活动套餐"
                    EventBus.getDefault().post(GoodsNumChangeEvent(1))
                    EventBus.getDefault().post(CombinationEvent(isCombination, position, iGroup))
                }

                mBinding?.tvNum?.text = packetCount?.toString()
            }
        })
        setSpecGroup(goodsDetailEntity)

        mBinding?.ivPlus?.setOnClickListener(this)
        mBinding?.ivMinus?.setOnClickListener(this)
        mBinding?.ivClose?.setOnClickListener(this)
        mBinding?.tvAddCart?.setOnClickListener(this)
        mBinding?.tvBuy?.setOnClickListener(this)
        mBinding?.tvRequestCheckin?.setOnClickListener(this)
    }

    /**
     * 规格 , 疗程套餐
     */
    private fun setSpecGroup(goodsDetailEntity: GoodsDetailEntity?) {
        //图片
        GlideApp.with(context!!)
                .load(goodsDetailEntity?.gn?.goodsImg)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(mBinding?.ivGoods!!)

        //价格
        mBinding?.tvGoodsPrice?.setText("合计：¥" + goodsDetailEntity?.gn?.goodsPrice)


        //规格
        goodsDetailEntity?.openSpec?.let {
            if (it?.size > 0) {
                val tags = ArrayList<String>()
                for (i in 0 until it?.size) {
                    tags.add(it.get(i).goodsSpec!!)
                }

                mBinding?.sflGoodsSpec?.setDatas(tags)
                mBinding?.sflGoodsSpec?.switchTag(specPosition)

            }
        }

        //疗程
        packageList = ArrayList()
        val packet = GoodsDetailEntity.PackageList()
        packet?.combination_name = "一盒标准装"
        packet?.package_count = 1
        packet?.goods_info_id = mGoodsDefaultInfoId
        packet?.combination_price = goodsDetailEntity?.gn?.goodsPrice?.toFloat()!!
        packet?.combination_unit_price = goodsDetailEntity?.gn?.goodsPrice?.toFloat()!!
        packageList?.add(packet)
        packageList?.addAll(goodsDetailEntity?.packageList!!)
        //组合套餐
        val combinationList = goodsDetailEntity?.combinationList

        iGruops = ArrayList()
        packageList?.let {
            iGruops?.addAll(it)
        }
        combinationList?.let {
            iGruops?.addAll(it)
        }


        if (iGruops?.size == 0) {
            mBinding?.llGoodsGroup?.visibility = View.GONE
        } else {
            mBinding?.llGoodsGroup?.visibility = View.VISIBLE
            val groups = ArrayList<String>()
            for (i in 0 until iGruops?.size!!) {
                val iGroup = iGruops?.get(i)
                if (iGroup is GoodsDetailEntity.PackageList) {
                    groups.add(iGroup.combination_name!!)
                } else if (iGroup is GoodsDetailEntity.CombinationList) {
                    groups.add(iGroup.combination_name!!)
                }
            }

            mBinding?.sflGoodsGroup?.setDatas(groups)
            mBinding?.sflGoodsGroup?.switchTag(groupPosition)
        }
    }

    /**
     * 疗程价格
     */
    private fun setPackagesPrice(position: Int, goodsInfoId: String, goodsPrice: Float, num: Int) {
        var sumPrice = goodsPrice * num
        val oldSinglePrice = goodsDetailEntity?.gn?.goodsPrice?.toFloat()!!
        val oldSumPrice = oldSinglePrice * num
        val preSumPrice = oldSumPrice - sumPrice

        mBinding?.tvGoodsPrice?.setText("合计：¥" + FloatUtils.format(sumPrice))
        mBinding?.tvPreGoodsPrice?.setCenterString("立省：¥" + FloatUtils.format(preSumPrice))

        EventBus.getDefault().post(PackageListEvent(isCombination, position, goodsInfoId, FloatUtils.format(sumPrice), FloatUtils.format(oldSumPrice)))
    }

    /**
     * 切换疗程
     */
    private fun switchPackageList(num: Int) {

        if (packageList?.size!! <= 1) {
            return
        }

        for (i in 1 until packageList?.size!!) {
            if (i < packageList?.size!! - 1) {

                if (i == 1 && num < packageList?.get(1)?.package_count!!) {
                    mBinding?.sflGoodsGroup?.switchTag(0)
                    return
                }

                val cur = packageList?.get(i)?.package_count!!
                val next = packageList?.get(i + 1)?.package_count!!
                if (num >= cur && num < next) {
                    mBinding?.sflGoodsGroup?.switchTag(i)
                    return
                }
            }
            val count = packageList?.get(packageList?.size!! - 1)?.package_count!!
            if (num >= count) {
                mBinding?.sflGoodsGroup?.switchTag(i)
            }
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
        DaggerGoodsSpecComponent.builder()
                .appComponent(appComponent)
                .goodsSpeclModule(GoodsSpeclModule(this))
                .build()
                .inject(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_plus -> plus()
            R.id.iv_minus -> minus()
            R.id.iv_close -> dismiss()
            R.id.tv_add_cart -> addCart()
            R.id.tv_buy -> buy()
            R.id.tv_request_checkin -> submitDemand()
        }
    }

    /**
     * 加入购物车
     */
    fun addCart() {
        if (!C.IS_LOGIN) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }
        EventBus.getDefault().post(AddCartEvent())
        dismiss()
    }

    /**
     * 购买
     */
    fun buy() {
        if (!C.IS_LOGIN) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }
        EventBus.getDefault().post(BuyNowEvent())
        dismiss()
    }

    /**
     *  提交处方登记
     */
    fun submitDemand() {
        if (!C.IS_LOGIN){
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
            return
        }
        EventBus.getDefault().post(SubmitDemandEvent())
        dismiss()
    }


    /**
     * 详情数据返回
     */
    override fun showGoodsDetail(position: Int, spec: String?, goodsInfoId: String?, goodsDetailEntity: GoodsDetailEntity?) {
        specPosition = position
        groupPosition = 0
        setSpecGroup(goodsDetailEntity)
        this.goodsDetailEntity = goodsDetailEntity
        goodsDetailEntity?.let {
            EventBus.getDefault().post(GoodSpecChangeEvent(position, spec!!, goodsInfoId!!, it))
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
        mBinding?.sflGoodsSpec?.returnPrePosition(specPosition)
    }

    /**
     * 数量 -
     */
    private fun minus() {
        var num = mBinding?.tvNum?.text?.toString()?.toInt()
        num?.let {
            if (it > 1) {
                val i = it - 1
                if (!isCombination) {
                    switchPackageList(i)
                    EventBus.getDefault().post(GoodsNumChangeEvent(i))
                }
                mBinding?.tvNum?.text = i.toString()
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
                val i = it + 1
                if (!isCombination) {
                    switchPackageList(i)
                    EventBus.getDefault().post(GoodsNumChangeEvent(i))
                }
                mBinding?.tvNum?.text = i.toString()
            }
        }
    }
}