package com.kzj.mall.entity

import com.kzj.mall.R
import com.kzj.mall.entity.home.*

class DataHelper {
    companion object {

        /**
         * 每日闪购数据
         */
        fun homeFlashData(): HomeFlashSaleEntity {
            val homeFlashSaleEntity = HomeFlashSaleEntity()
            homeFlashSaleEntity.flashSaleListData = flashSaleListDatas()
            return homeFlashSaleEntity
        }

        /**
         * 每日闪购列表
         */
        fun flashSaleListDatas(): MutableList<HomeFlashSaleEntity.FlashSaleListData> {
            var datas = ArrayList<HomeFlashSaleEntity.FlashSaleListData>()
            for (i in 0 until 5) {
                datas.add(HomeFlashSaleEntity().FlashSaleListData())
            }
            return datas
        }

        /**
         * 穿插广告
         */
        fun homeAdvBannerData(): HomeAdvBannerEntity {
            var homeAdvBannerData = HomeAdvBannerEntity()
            homeAdvBannerData.banners = homeAdvBannerListDatas()
            return homeAdvBannerData
        }

        /**
         * 穿插广告列表
         */
        fun homeAdvBannerListDatas(): MutableList<HomeAdvBannerEntity.Banners> {
            var datas = ArrayList<HomeAdvBannerEntity.Banners>()
            for (i in 0 until 5) {
                datas.add(HomeAdvBannerEntity().Banners())
            }
            return datas
        }


        /**
         * 情趣用品
         */
        fun homeSexToy(): SexToyEntity {
            var sexToy = SexToyEntity()
            sexToy.sexToys = homeSexToys()
            return sexToy
        }

        /**
         * 情趣用品列表
         */
        fun homeSexToys(): MutableList<SexToyEntity.SexToys> {
            var sexToys = ArrayList<SexToyEntity.SexToys>()
            for (i in 0 until 5) {
                sexToys.add(SexToyEntity().SexToys())
            }
            return sexToys
        }

        /**
         * 为您推荐
         */
        fun homeRecommendDatas(): MutableList<HomeRecommendEntity> {
            val list = ArrayList<HomeRecommendEntity>()
            for (i in 0..8) {
                val homeRecommendEntity = HomeRecommendEntity()
                homeRecommendEntity.isBackgroundCorners = true
                if (i == 0) {
                    homeRecommendEntity.isShowRecommendText = true
                } else {
                    homeRecommendEntity.isShowRecommendText = false
                }
                list.add(homeRecommendEntity)
            }
            return list
        }

        /**
         * 男科穿插广告
         */
        fun andrologyAdvBannerData(): AndrologyAdvBannerEntity {
            val andrologyAdvBannerData = AndrologyAdvBannerEntity()
            andrologyAdvBannerData.banners = andrologyAdvBannerDatas()
            return andrologyAdvBannerData
        }

        /**
         * 男科穿插广告
         */
        fun andrologyAdvBannerDatas(): MutableList<AndrologyAdvBannerEntity.Banners> {
            val andrologyAdvBannerDatas = ArrayList<AndrologyAdvBannerEntity.Banners>()
            for (i in 0 until 5) {
                andrologyAdvBannerDatas?.add(AndrologyAdvBannerEntity().Banners())
            }
            return andrologyAdvBannerDatas
        }


        /**
         * 男科 专场
         */
        fun andrologySpecialFieldData(): AndrologySpecialFieldEntity {
            val andrologySpecialFieldData = AndrologySpecialFieldEntity()
            andrologySpecialFieldData.specialFields = andrologySpecialFieldDatas()
            return andrologySpecialFieldData
        }

        /**
         * 男科 专场 列表
         */
        fun andrologySpecialFieldDatas(): MutableList<AndrologySpecialFieldEntity.SpecialFields> {
            var andrologySpecialFieldDatas = ArrayList<AndrologySpecialFieldEntity.SpecialFields>()
            for (i in 0 until 8) {
                andrologySpecialFieldDatas.add(AndrologySpecialFieldEntity().SpecialFields())
            }
            return andrologySpecialFieldDatas
        }

        /**
         * 分类左边
         */
        fun classifyLeftDatas(): MutableList<ClassifyLeftEntity> {
            var classifyLeftDatas = ArrayList<ClassifyLeftEntity>()

            for (i in 0 until 10) {
                when (i) {
                    0 -> {
                        val classifyLeftEntity = ClassifyLeftEntity("推荐分类")
                        classifyLeftEntity.isChoose = true
                        classifyLeftDatas.add(classifyLeftEntity)
                    }
                    1 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("按症状找药"))
                    }
                    2 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("按科室找药"))
                    }
                    3 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("家庭常备药"))
                    }
                    4 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("慢性病用药"))
                    }
                    5 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("男科药馆"))
                    }
                    6 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("成人计生"))
                    }
                    7 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("保健滋补"))
                    }
                    8 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("母婴用品"))
                    }
                    9 -> {
                        classifyLeftDatas.add(ClassifyLeftEntity("医疗器械"))
                    }
                }
            }

            return classifyLeftDatas
        }


        /**
         * 分类列表数据
         */
        fun classifyDatas(): MutableList<ClassifyRightSectionEntity> {
            var classifyDatas = ArrayList<ClassifyRightSectionEntity>()
            classifyDatas.add(ClassifyRightSectionEntity(true, "性用品"))
            classifyDatas.add(ClassifyRightSectionEntity(classifyContentDatas()))

            classifyDatas.add(ClassifyRightSectionEntity(true, "性用品"))
            classifyDatas.add(ClassifyRightSectionEntity(classifyContentDatas()))
            return classifyDatas
        }

        /**
         *  分类内容数据
         */
        fun classifyContentDatas(): MutableList<ClassifyRightEntity> {
            var classifyContentDatas = ArrayList<ClassifyRightEntity>()
            for (i in 0 until 6) {
                classifyContentDatas.add(ClassifyRightEntity())
            }
            return classifyContentDatas
        }

        fun homeTabDatas(): MutableList<HomeTabEntity> {
            var homeTabDatas = ArrayList<HomeTabEntity>()
            for (i in 0 until 5) {
                var homeTabData = HomeTabEntity()
                when (i) {
                    0 -> {
                        homeTabData.icon = R.color.gray_default
                        homeTabData.name = "首页"
                    }
                    1 -> {
                        homeTabData.icon = R.color.gray_default
                        homeTabData.name = "男科"
                    }
                    2 -> {
                        homeTabData.icon = R.color.gray_default
                        homeTabData.name = "早泄"
                    }
                    3 -> {
                        homeTabData.icon = R.color.gray_default
                        homeTabData.name = "温阳补肾"
                    }
                    4 -> {
                        homeTabData.icon = R.color.gray_default
                        homeTabData.name = "脱发少发"
                    }
                }
                homeTabDatas.add(homeTabData)
            }
            return homeTabDatas
        }

    }
}