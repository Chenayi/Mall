package com.kzj.mall.entity

class MineEntity {
    /**
     * 收藏的商品
     */
    var follows: Follows? = null

    /**
     * 订单数
     */
    var orderSum: OrderSum? = null

    /**
     * 用户信息
     */
    var customerMap: CustomerMap? = null

    class Follows {
        var list: MutableList<FollowList>? = null
    }

    class FollowList {
        var good: Good? = null
    }

    class Good {
        var goodsImg: String? = null
        var goodsInfoId: String? = null
    }

    class OrderSum {
        /**
         * 待收货
         */
        var nottdy: Int = 0

        /**
         * 待发货
         */
        var notdy: Int = 0

        /**
         *已完成
         */
        var ots: Int = 0

        /**
         *  待付款
         */
        var notpay: Int = 0
    }

    class CustomerMap {
        var customer_img: String? = null
        var customer_username: String? = null
        var customer_id: String? = null
        var info_mobile: String? = null
        var info_gender: String? = null
        var customer_nickname: String? = null
    }
}