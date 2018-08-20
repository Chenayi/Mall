package com.kzj.mall.entity.home

class HomeAskAnswerEntity : IHomeEntity {
    var askList: MutableList<AskList>? = null
    override fun getItemType(): Int {
        return IHomeEntity.ASK_ANSWER
    }

    class AskList {
        var qName: String? = null
        var userName: String? = null
    }
}