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
    class Gn : Serializable {
        var goodsImgs: MutableList<GoodsImgs>? = null
        var goodsImg: String? = null
        var goodsApprovalNo: GoodsApprovalNo? = null
        var goodsPrice: String? = null
        var goodsName: String? = null
        var goodsMarketPrice: String? = null
        var goodsType: String? = null
        //月销量
        var goodsSole: String? = null
        //库存
        var goodsStock: Int? = null
        //商品详情
        var goodsMobileDesc: String? = null
    }

    /**
     * 商品信息
     */
    class Gin : Serializable {
        var goods_info_subtitle: String? = null
        var goods_mobile_desc: String? = null
    }

    /**
     * 商品图片
     */
    class GoodsImgs : Serializable {
        var imageArtworkName: String? = null
    }

    /**
     * 规格
     */
    class OpenSpec : Serializable {
        var goodsInfoId: String? = null
        var goodsId: String? = null
        var goodsSpec: String? = null
    }

    /**
     * 疗程
     */
    class PackageList : IGroup {
        var combination_name: String? = null
        var combination_type: Int? = null
        var package_count: Int = 0
        var goods_info_id: String? = null

        var combination_price: Float = 0.00F
        var combination_unit_price: Float = 0.00F

        override fun getType() = IGroup.TYPE_PACKET_LIST
    }

    /**
     * 组合套餐
     */
    class CombinationList : IGroup {
        var isOpen = false
        var combination_name: String? = null
        var combination_type: Int? = null
        var package_count: Int? = null
        var goods_info_id: String? = null
        var combination_id: String? = null

        var combination_price: Float = 0.00F
        var sumOldPrice: Float = 0.00F
        var sumPrePrice: Float = 0.00F

        var ggList: MutableList<GGList>? = null

        override fun getType() = IGroup.TYPE_COMBINATION_LIST
    }

    class GGList {
        var goods_img: String? = null
        var goodsNum: String? = null
        var goods_stock: Int? = null
        var goods_name: String? = null
        var goods_price: String? = null
        var goods_info_id: String? = null
    }

    class PromotionalAd : Serializable {
        var wap_promotional_title: String? = null
    }

    class GoodsApprovalNo : Serializable {
        var approvalNo: String? = null
        var approvalNoId: String? = null
    }


    private var explains: MutableList<Explain>? = null

    /**
     * 说明
     */
    inner class Explain : Serializable {
        var title: String? = null
        var content: String? = null
    }

    interface IGroup : Serializable {
        companion object {
            //疗程
            val TYPE_PACKET_LIST = 0
            //套餐
            val TYPE_COMBINATION_LIST = 1
        }

        fun getType(): Int
    }
}