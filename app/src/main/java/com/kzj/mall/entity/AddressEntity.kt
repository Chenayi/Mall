package com.kzj.mall.entity

class AddressEntity {
    var customerAddresses:MutableList<CustomerAddresses>?=null

    class CustomerAddresses{
        var isCheck = false
    }
}