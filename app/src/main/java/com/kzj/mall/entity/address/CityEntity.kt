package com.kzj.mall.entity.address

class CityEntity {
    var cityBeen: MutableList<CityBeen>? = null

    class CityBeen : IAddress {
        var ckeck = false
        var cityId: String? = null
        var cityName: String? = null
    }
}