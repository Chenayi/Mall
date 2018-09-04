package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.AddressEntity

class AddressAdapter constructor(val addressDatas : MutableList<AddressEntity.CustomerAddresses>)
    : BaseAdapter<AddressEntity.CustomerAddresses,BaseViewHolder>(R.layout.item_address,addressDatas) {
    override fun convert(helper: BaseViewHolder?, item: AddressEntity.CustomerAddresses?) {

        helper?.setVisible(R.id.iv_check,item?.isCheck == true)

    }
}