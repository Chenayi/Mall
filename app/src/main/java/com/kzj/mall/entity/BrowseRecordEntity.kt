package com.kzj.mall.entity

class BrowseRecordEntity {
    var deleteMode = false

    var browserecords: Browserecords? = null


    class Browserecords {
        var list: MutableList<BrowserecordList>? = null
    }

    class BrowserecordList {

    }
}