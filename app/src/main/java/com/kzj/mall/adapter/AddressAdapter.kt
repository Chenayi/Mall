package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.address.Address
import com.kzj.mall.utils.Utils

class AddressAdapter constructor(val addressDatas: MutableList<Address>)
    : BaseAdapter<Address, BaseViewHolder>(R.layout.item_address, addressDatas) {
    override fun convert(helper: BaseViewHolder?, item: Address?) {

        val adddress = item?.province?.provinceName + "省" + item?.city?.cityName + "市" + item?.district?.districtName + item?.addressDetail

        helper?.setVisible(R.id.iv_check, item?.isCheck == true)
                ?.setGone(R.id.tv_default, item?.isDefault?.equals("1")!!)
                ?.setText(R.id.tv_name, item?.addressName)
                ?.setText(R.id.tv_mobile, Utils.subMobile(item?.addressMoblie!!))
                ?.setText(R.id.tv_address, adddress)
                ?.addOnClickListener(R.id.iv_edit)
    }
}