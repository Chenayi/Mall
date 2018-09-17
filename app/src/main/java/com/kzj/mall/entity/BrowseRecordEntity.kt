package com.kzj.mall.entity

class BrowseRecordEntity {
    var browserecords: Browserecords? = null


    class Browserecords {
        var list: MutableList<BrowserecordList>? = null
    }

    class BrowserecordList {
        var deleteMode: Boolean = false
        var goods: Goods? = null
        var check = false
        var likeId: Long? = null
    }

    class Goods {
        var goodsName: String? = null
        var goodsImg: String? = null
        var goodsPrice: String? = null
        var goodsInfoSubtitle: String? = null
        var goodsInfoId:String?=null
    }
}