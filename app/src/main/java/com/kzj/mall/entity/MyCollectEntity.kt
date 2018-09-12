package com.kzj.mall.entity

class MyCollectEntity {
    var follows: Follows? = null

    class Follows {
        var list: MutableList<FollowList>? = null
    }

    class FollowList {
        var followId:Long?=null
        var good: Good? = null
    }

    class Good {
        var goodsImg: String? = null
        var goodsInfoSubtitle: String? = null
        var goodsName: String? = null
        var goodsPrice: String? = null
        var goodsInfoId: String? = null
    }
}