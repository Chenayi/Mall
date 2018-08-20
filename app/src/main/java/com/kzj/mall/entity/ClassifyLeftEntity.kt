package com.kzj.mall.entity

class ClassifyLeftEntity {
    var cateList: MutableList<CateList>? = null

    class CateList {
        var isChoose = false
        var cat_id: Int? = null
        var cat_parent_id: Int? = null
        var cat_name: String? = null
    }
}