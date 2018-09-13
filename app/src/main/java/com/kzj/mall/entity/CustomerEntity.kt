package com.kzj.mall.entity

class CustomerEntity {
    var custAllInfo: CustAllInfo? = null

    class CustAllInfo {
        var info_mobile: String? = null
        var customer_img: String? = null
        var info_gender: String? = null
        var customer_username: String? = null
        var customer_nickname: String? = null
        var customer_id: String? = null
    }
}