package com.kzj.mall.entity

import com.kzj.mall.entity.home.HomeFlashSaleEntity

class DataHelper {
    companion object {

        /**
         * 每日闪购数据
         */
        fun flashData(): HomeFlashSaleEntity {
            val homeFlashSaleEntity = HomeFlashSaleEntity()
            homeFlashSaleEntity.flashSaleListData = flashSaleListData()
            return homeFlashSaleEntity
        }

        /**
         * 每日闪购列表
         */
        fun flashSaleListData(): MutableList<HomeFlashSaleEntity.FlashSaleListData> {
            var datas = ArrayList<HomeFlashSaleEntity.FlashSaleListData>()
            for (i in 0 until 5) {
                datas.add(HomeFlashSaleEntity().FlashSaleListData())
            }
            return datas
        }
    }
}