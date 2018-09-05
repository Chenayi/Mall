package com.kzj.mall.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.address.*

class AddressListAdapter(addresses: MutableList<IAddress>) : BaseAdapter<IAddress, BaseViewHolder>(R.layout.item_address_list, addresses) {
    override fun convert(helper: BaseViewHolder?, item: IAddress?) {
        if (item is Address.Province) {
            helper?.setText(R.id.tv_address, item?.provinceName)
                    ?.setTextColor(R.id.tv_address, if (item?.ckeck) ContextCompat.getColor(mContext, R.color.colorPrimary)
                    else ContextCompat.getColor(mContext, R.color.c_2e3033))
                    ?.setGone(R.id.iv_check, item?.ckeck)
        } else if (item is Address.City) {
            helper?.setText(R.id.tv_address, item?.cityName)
                    ?.setTextColor(R.id.tv_address, if (item?.ckeck) ContextCompat.getColor(mContext, R.color.colorPrimary)
                    else ContextCompat.getColor(mContext, R.color.c_2e3033))
                    ?.setGone(R.id.iv_check, item?.ckeck)
        } else if (item is Address.District) {
            helper?.setText(R.id.tv_address, item?.districtName)
                    ?.setTextColor(R.id.tv_address, if (item?.ckeck) ContextCompat.getColor(mContext, R.color.colorPrimary)
                    else ContextCompat.getColor(mContext, R.color.c_2e3033))
                    ?.setGone(R.id.iv_check, item?.ckeck)
        }
    }
}