package com.kzj.mall.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.GoodsSortViewBinding
import com.kzj.mall.ui.dialog.PrescriptionPop
import razerdp.basepopup.BasePopupWindow

class GoodsSortView : BaseRelativeLayout<GoodsSortViewBinding>, View.OnClickListener, PrescriptionPop.OnTypeChooseListener {

    private var curSelect = S_DEFAULT

    private var priceOrder: String? = null

    private var onSortChangeListener: OnSortChangeListener? = null

    private var prescriptionPop: PrescriptionPop? = null


    private var typeWhat: String? = null

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
        prescriptionPop?.setOnTypeChooseListener(this)
        prescriptionPop?.setOnDismissListener(object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                mBinding?.ivType?.setImageResource(R.mipmap.down_sel)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_default -> {
                if (curSelect != S_DEFAULT) {
                    setDefault()
                    onSortChangeListener?.onSortChange(S_DEFAULT, "DESC", typeWhat)
                }
            }
            R.id.tv_sales -> {
                if (curSelect != S_SALES) {
                    setSales()
                    onSortChangeListener?.onSortChange(S_SALES, "DESC", typeWhat)
                }
            }
            R.id.ll_price -> {
                setPrice()
                onSortChangeListener?.onSortChange(S_PRICE, priceOrder, typeWhat)
            }
            R.id.ll_type -> {
                if (curSelect != S_TYPE) {
                    setType()
                    onSortChangeListener?.onSortChange(S_TYPE, "DESC", typeWhat)
                } else {
                    prescriptionPop?.showPopupWindow(this@GoodsSortView)
                    mBinding?.ivType?.setImageResource(R.mipmap.up_sel)
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
        mBinding?.ivType?.setImageResource(R.mipmap.down_default)
        curSelect = S_DEFAULT
        priceOrder = null
    }

    /**
     * 默认
     */
    fun setPopDefault() {
        prescriptionPop?.setDeafult()
        typeWhat = null
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
        mBinding?.ivType?.setImageResource(R.mipmap.down_default)
        curSelect = S_SALES
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
        mBinding?.ivType?.setImageResource(R.mipmap.down_default)
        if (priceOrder == null || priceOrder?.equals(ORDER_DESC)!!) {
            priceOrder = ORDER_ASC
            mBinding?.ivPriceUp?.setImageResource(R.mipmap.up_sel)
            mBinding?.ivPriceDown?.setImageResource(R.mipmap.down_default)
        } else {
            priceOrder = ORDER_DESC
            mBinding?.ivPriceUp?.setImageResource(R.mipmap.up_default)
            mBinding?.ivPriceDown?.setImageResource(R.mipmap.down_sel)
        }
        curSelect = S_PRICE
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
        mBinding?.ivType?.setImageResource(R.mipmap.down_sel)
        priceOrder = null
    }


    override fun onTypeChoose(typeWhat: String?) {
        onSortChangeListener?.onSortChange(S_TYPE, "DESC", typeWhat)
        this.typeWhat = typeWhat
    }

    fun setOnSortChangeListener(l: OnSortChangeListener) {
        onSortChangeListener = l
    }

    interface OnSortChangeListener {
        fun onSortChange(sort: Int?, order: String?, typeWhat: String?)
    }
}