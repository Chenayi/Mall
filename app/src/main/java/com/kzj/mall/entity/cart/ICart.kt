package com.kzj.mall.entity.cart

interface ICart {
    companion object {
        val SINGLE = 1
        val GROUP = 2
        val RECOMMEND_TEXT = 3
        val RECOMMEND = 4
    }

    fun getItemType(): Int
}