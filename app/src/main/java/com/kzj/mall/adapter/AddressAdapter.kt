package com.kzj.mall.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.address.Address
import com.kzj.mall.utils.Utils

class AddressAdapter constructor(addressDatas: MutableList<Address>, val isManager: Boolean)
    : BaseAdapter<Address, BaseViewHolder>(R.layout.item_address, addressDatas) {
    override fun convert(helper: BaseViewHolder?, item: Address?) {

        val adddress = item?.province?.provinceName + "省" + item?.city?.cityName + "市" + item?.district?.districtName + item?.addressDetail

        val ivCheck = helper?.getView<ImageView>(R.id.iv_check)


        if (isManager) {
            ivCheck?.visibility = View.GONE
        } else {
            if (item?.isCheck == true) {
                ivCheck?.visibility = View.VISIBLE
            } else {
                ivCheck?.visibility = View.INVISIBLE
            }
        }

        helper?.setGone(R.id.tv_default, item?.isDefault?.equals("1")!!)
                ?.setGone(R.id.top_view, helper?.layoutPosition <= 0)
                ?.setText(R.id.tv_name, item?.addressName)
                ?.setText(R.id.tv_mobile, Utils.subMobile(item?.addressMoblie!!))
                ?.setText(R.id.tv_address, adddress)
                ?.setImageResource(R.id.iv_default, if (item?.isDefault?.equals("1") == true) R.mipmap.default_check else R.mipmap.c_nor)
                ?.addOnClickListener(R.id.ll_edit)?.addOnClickListener(R.id.ll_default)
                ?.addOnClickListener(R.id.ll_delete)
    }
}