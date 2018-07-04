package com.kzj.mall.entity

import com.chad.library.adapter.base.entity.SectionEntity

class ClassifyRightSectionEntity : SectionEntity<MutableList<ClassifyRightEntity>> {
    constructor(isHeader: Boolean, header: String?) : super(isHeader, header)
    constructor(t: MutableList<ClassifyRightEntity>?) : super(t)
}