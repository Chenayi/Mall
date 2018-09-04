package com.kzj.mall.entity.address

class DistrictEntity {
    var districtBeen: MutableList<DistrictBeen>? = null

    class DistrictBeen : IAddress {
        var ckeck = false
        var chkDisabled: Boolean? = null
        var districtId: String? = null
        var districtName: String? = null
    }
}