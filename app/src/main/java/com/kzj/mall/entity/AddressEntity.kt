package com.kzj.mall.entity

class AddressEntity {
    var customerAddresses: MutableList<CustomerAddresses>? = null

    class CustomerAddresses {
        var isCheck = false
        var isDefault: String? = null
        var addressName: String? = null
        var addressMoblie: String? = null
        var city: City? = null
        var province: Province? = null
        var district: District? = null
        var addressDetail:String?=null
    }

    class City {
        var cityId: String? = null
        var cityName: String? = null
    }

    class Province {
        var provinceName: String? = null
        var provinceId: String? = null
    }

    class District {
        var districtName: String? = null
        var districtId: String? = null
    }
}