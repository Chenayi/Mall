package com.kzj.mall.entity.order

class OrderEntity {

    companion object {
        val ORDER_STATUS_WAIT_PAY = 0
        val ORDER_STATUS_WAIT_SEND = 1
        val ORDER_STATUS_WAIT_TAKE = 2
        val ORDER_STATUS_FINISH = 11
    }

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