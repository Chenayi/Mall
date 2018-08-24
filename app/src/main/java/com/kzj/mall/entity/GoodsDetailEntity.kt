package com.kzj.mall.entity

import java.io.Serializable

class GoodsDetailEntity : Serializable {
    var gn: Gn? = null
    var gin: Gin? = null
    var promotionalAd: PromotionalAd? = null
    var is_follow: String? = null
    var openSpec: MutableList<OpenSpec>? = null
    var packageList: MutableList<PackageList>? = null
    var combinationList: MutableList<CombinationList>? = null

    /**
     * 产品信息
     */
    class Gn {
        var goodsImgs: MutableList<GoodsImgs>? = null
        var goodsApprovalNo: GoodsApprovalNo? = null
        var goodsPrice: String? = null
        var goodsName: String? = null
        var goodsMarketPrice: String? = null
        var goodsSole: String? = null
    }

    /**
     * 商品信息
     */
    class Gin {
        var goods_info_subtitle: String? = null
    }

    /**
     * 商品图片
     */
    class GoodsImgs {
        var imageArtworkName: String? = null
    }

    /**
     * 规格
     */
    class OpenSpec {
        var goodsInfoId: String? = null
        var goodsId: String? = null
        var goodsSpec: String? = null
    }

    /**
     * 疗程
     */
    class PackageList {
        var combination_name: String? = null
        var combination_type: Int? = null
        var package_count: Int? = null
        var goods_info_id:String?=null
    }

    /**
     * 组合套餐
     */
    class CombinationList {
        var combination_name: String? = null
        var combination_type: Int? = null
        var package_count: Int? = null
        var goods_info_id:String?=null
    }

    class PromotionalAd {
        var wap_promotional_title: String? = null
    }

    class GoodsApprovalNo {
        var approvalNo: String? = null
        var approvalNoId: String? = null
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