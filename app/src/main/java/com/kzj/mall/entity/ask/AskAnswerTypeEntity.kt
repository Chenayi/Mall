package com.kzj.mall.entity.ask

import java.io.Serializable

class AskAnswerTypeEntity:Serializable {
    var catList: MutableList<CatList>? = null

    class CatList:Serializable {
        var catId: String? = null
        var catName: String? = null
        var check = false
    }
}