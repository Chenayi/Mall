package com.kzj.mall.entity.address

class ProvinceEntity {

    var provinceBeen: MutableList<ProvinceBeen>? = null


    class ProvinceBeen : IAddress {
        var ckeck = false
        var provinceName: String? = null
        var provinceId: String? = null
    }
}