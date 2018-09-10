package com.kzj.mall.entity

class OrderEntity {
    var orderList: OrderList? = null;

    class OrderList {
        var list: MutableList<List>? = null
    }

    class List {
        var order_id: String? = null
        var order_status: Int? = null
        var order_price: String? = null
        var orderGoodses: MutableList<OrderGoodses>? = null
        var order_code: String? = null
    }

    class OrderGoodses {
        var order_id: String? = null
        var goods_info_num:String?=null
        var goods_info_price:String?=null
        var goods_info_old_price:String?=null
        var order_goods_type:String?=null
        var goods_info_id:String?=null
        var goods_spec:String?=null
        var goods_img:String?=null
        var goods_name:String?=null
    }
}