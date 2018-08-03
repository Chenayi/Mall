package com.kzj.mall.entity

import com.kzj.mall.entity.home.IHomeEntity

class HomeEntity constructor(val type: Int) : IHomeEntity {
    constructor() : this(0)

    override fun getItemType(): Int {
        return type
    }
}