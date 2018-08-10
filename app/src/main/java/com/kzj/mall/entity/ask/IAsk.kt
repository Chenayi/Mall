package com.kzj.mall.entity.ask

interface IAsk {
    companion object {
        val TYPE_ASK = 0
        val TYPE_ANSWER = 1
    }


    fun type():Int
}