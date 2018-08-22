package com.kzj.mall.entity

import java.io.Serializable

class GoodsDetailEntity : Serializable {
    var gn: Gn? = null

    class Gn{
        var goodsImgs: MutableList<GoodsImgs>? = null
    }
    class GoodsImgs {
        var imageArtworkName: String? = null
    }


    private var explains: MutableList<Explain>? = null

    inner class Group {

    }

    /**
     * 说明
     */
    inner class Explain : Serializable {
        var title: String? = null
        var content: String? = null
    }
}