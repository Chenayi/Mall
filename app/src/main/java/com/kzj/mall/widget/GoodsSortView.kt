package com.kzj.mall.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.GoodsSortViewBinding
import com.kzj.mall.ui.dialog.PrescriptionPop

class GoodsSortView : BaseRelativeLayout<GoodsSortViewBinding>, View.OnClickListener {

    private var curSelect = S_DEFAULT

    private var priceOrder: String? = null

    private var onSortChangeListener: OnSortChangeListener? = null

    private var prescriptionPop:PrescriptionPop?=null

    companion object {
        val S_DEFAULT = 0
        val S_SALES = 1
        val S_PRICE = 2
        val S_TYPE = 3

        //升序
        val ORDER_ASC = "ASC"
        //降序
        val ORDER_DESC = "DESC"
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId() = R.layout.goods_sort_view
    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        mBinding?.tvDefault?.setOnClickListener(this)
        mBinding?.tvSales?.setOnClickListener(this)
        mBinding?.llPrice?.setOnClickListener(this)
        mBinding?.llType?.setOnClickListener(this)
        prescriptionPop = PrescriptionPop(context)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_default -> {
                if (curSelect != S_DEFAULT) {
                    setDefault()
                    curSelect = S_DEFAULT
                    onSortChangeListener?.onSortChange(S_DEFAULT,"DESC")
                }
            }
            R.id.tv_sales -> {
                if (curSelect != S_SALES) {
                    setSales()
                    curSelect = S_SALES
                    onSortChangeListener?.onSortChange(S_SALES,"DESC")
                }
            }
            R.id.ll_price -> {
                setPrice()
                curSelect = S_PRICE
                onSortChangeListener?.onSortChange(S_PRICE,priceOrder)
            }
            R.id.ll_type -> {
                if (curSelect != S_TYPE) {
                    setType()
                }else{
                    prescriptionPop?.showPopupWindow(this@GoodsSortView)
                }
                curSelect = S_TYPE
            }
        }
    }

    /**
     * 默认
     */
    fun setDefault() {
        mBinding?.tvSales?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvPrice?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvType?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvDefault?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        mBinding?.ivPriceUp?.setImageResource(R.mipmap.up_default)
        mBinding?.ivPriceDown?.setImageResource(R.mipmap.down_default)
        priceOrder = null
    }

    /**
     * 销量
     */
    fun setSales() {
        mBinding?.tvDefault?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvPrice?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvType?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvSales?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        mBinding?.ivPriceUp?.setImageResource(R.mipmap.up_default)
        mBinding?.ivPriceDown?.setImageResource(R.mipmap.down_default)
        priceOrder = null
    }

    /**
     * 价格
     */
    fun setPrice() {
        mBinding?.tvDefault?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvSales?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvType?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvPrice?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        if (priceOrder == null || priceOrder?.equals(ORDER_DESC)!!) {
            priceOrder = ORDER_ASC
            mBinding?.ivPriceUp?.setImageResource(R.mipmap.up_sel)
            mBinding?.ivPriceDown?.setImageResource(R.mipmap.down_default)
        } else {
            priceOrder = ORDER_DESC
            mBinding?.ivPriceUp?.setImageResource(R.mipmap.up_default)
            mBinding?.ivPriceDown?.setImageResource(R.mipmap.down_sel)
        }
    }

    /**
     * 类型
     */
    fun setType() {
        mBinding?.tvDefault?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvPrice?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvSales?.setTextColor(ContextCompat.getColor(context, R.color.c_2e3033))
        mBinding?.tvType?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        mBinding?.ivPriceUp?.setImageResource(R.mipmap.up_default)
        mBinding?.ivPriceDown?.setImageResource(R.mipmap.down_default)
        priceOrder = null
    }

    fun setOnSortChangeListener(l: OnSortChangeListener) {
        onSortChangeListener = l
    }

    interface OnSortChangeListener {
        fun onSortChange(sort: Int?, order: String?)
    }
}