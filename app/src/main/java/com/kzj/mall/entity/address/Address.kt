package com.kzj.mall.entity.address

import java.io.Serializable


class Address : Serializable {
    /**
     * 名字
     */
    var addressName: String? = null
    /**
     * 电话
     */
    var addressMoblie: String? = null
    /**
     * id
     */
    var addressId: String? = null


    var isCheck = false
    var isDefault: String? = null
    //省
    var province: Province? = null
    //市
    var city: City? = null
    //区
    var district: District? = null
    //详细地址
    var addressDetail: String? = null

    /**
     * 省
     */
    class Province : Serializable,IAddress {
        var ckeck = false
        var provinceId: String? = null
        var provinceName: String? = null
    }

    /**
     * 市
     */
    class City : Serializable,IAddress {
        var ckeck = false
        var cityId: String? = null
        var cityName: String? = null
    }

    /**
     *  区
     */
    class District : Serializable,IAddress {
        var ckeck = false
        var districtId: String? = null
        var districtName: String? = null
    }
}