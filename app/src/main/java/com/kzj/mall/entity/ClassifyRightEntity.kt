package com.kzj.mall.entity

import com.chad.library.adapter.base.entity.SectionEntity

class ClassifyRightEntity {
    var cateList: MutableList<CateList>? = null

    class CateList {
        var cat_id: Long? = null
        var cat_parent_id: Long? = null
        var cat_name: String? = null
    }
}