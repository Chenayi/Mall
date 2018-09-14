package com.kzj.mall.entity.ask

class AskAnswerEntity {
    val interlucation_list: InterlucationList? = null

    class InterlucationList {
        var list: MutableList<List>? = null
    }

    class List {
        var cat: Cat? = null
        var qContent: String? = null
        var qAddTime: Long? = null
        var qStatus: String? = null
    }

    class Cat {
        var catName: String? = null
    }
}