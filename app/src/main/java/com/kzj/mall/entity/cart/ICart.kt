package com.kzj.mall.entity.cart

interface ICart {
    companion object {
        val SINGLE = 1
        val GROUP = 2
        val RECOMMEND = 3
    }

    fun getItemType(): Int
}