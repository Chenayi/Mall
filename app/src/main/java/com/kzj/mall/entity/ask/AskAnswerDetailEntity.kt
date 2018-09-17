package com.kzj.mall.entity.ask

class AskAnswerDetailEntity : IAsk {
    var type = 0

    override fun type() = type

    var interlocution: Interlocution? = null

    class Interlocution {
        var userSex: String? = null
        var userAge: String? = null
        var qAddTime: Long? = null
        var qName: String? = null

        var cat: Cat? = null

        var qAnswer: String? = null

        var qAnswerTime: Long? = null
    }

    class Cat {
        var catName: String? = null
    }
}