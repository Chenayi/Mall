package com.kzj.mall.utils

import com.kzj.mall.R
import com.kzj.mall.entity.*
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import com.kzj.mall.entity.ask.AskAnswerEntity
import com.kzj.mall.entity.ask.IAsk
import com.kzj.mall.entity.cart.*
import com.kzj.mall.entity.home.*

class LocalDatas {
    companion object {

        fun homeBannerData(): HomeHeaderBannerEntity {
            val homeHeaderBannerEntity = HomeHeaderBannerEntity()
            val banners = ArrayList<HomeHeaderBannerEntity.Adds>()

            val banner1 = HomeHeaderBannerEntity().Adds()
            banner1.adCode = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861413&di=c9f7439a5a5d4c57435e5eb7f2772817&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a0d4582d8320a84a0e282be8a02e.jpg"

            val banner2 = HomeHeaderBannerEntity().Adds()
            banner2.adCode = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530523369994&di=60f87ef08f23f8dab2b36d5ed57f5dcd&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ac39597adf9da8012193a352df31.jpg"

            val banner3 = HomeHeaderBannerEntity().Adds()
            banner3.adCode = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861412&di=c51db760c9ecc789cdae3b334715aef6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0161c95690b86032f87574beaa54c2.jpg"

            val banner4 = HomeHeaderBannerEntity().Adds()
            banner4.adCode = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533288558977&di=2dc323862d5e267a01c67863d579e511&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F01%2F61%2Fdfcde6abbc5c5941a18da393901c1263.jpg"


            banners.add(banner1)
            banners.add(banner2)
            banners.add(banner3)
            banners.add(banner4)

            homeHeaderBannerEntity.adss = banners

            return homeHeaderBannerEntity
        }

        /**
         * 每日闪购数据
         */
        fun homeFlashData(): HomeFlashSaleEntity {
            val homeFlashSaleEntity = HomeFlashSaleEntity()
            homeFlashSaleEntity.dailyBuy = flashSaleListDatas()
            return homeFlashSaleEntity
        }

        /**
         * 每日闪购列表
         */
        fun flashSaleListDatas(): MutableList<HomeFlashSaleEntity.DailyBuy> {
            var datas = ArrayList<HomeFlashSaleEntity.DailyBuy>()
            for (i in 0 until 5) {
                datas.add(HomeFlashSaleEntity().DailyBuy())
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
            for (i in 0 until 3) {
                val banner = HomeAdvBannerEntity.Banners()
                banner?.adv = R.mipmap.adv_default
                datas.add(banner)
            }
            return datas
        }


        /**
         * 情趣用品
         */
        fun homeSexToy(): SexToyEntity {
            var sexToy = SexToyEntity()
            sexToy.qingqu = homeSexToys()
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
        fun homeRecommendDatas(): MutableList<HomeRecommendEntity.Data> {
            val list = ArrayList<HomeRecommendEntity.Data>()
            for (i in 0..8) {
                val homeRecommendEntity = HomeRecommendEntity.Data()
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
            for (i in 0 until 3) {
                val banners = AndrologyAdvBannerEntity.Banners()
                banners.imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861413&di=c9f7439a5a5d4c57435e5eb7f2772817&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a0d4582d8320a84a0e282be8a02e.jpg"
                andrologyAdvBannerDatas?.add(banners)
            }
            return andrologyAdvBannerDatas
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

        /**
         * 首页tab
         */
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


        /**
         * 商品详情 组合
         */
        fun goodsDetailGroups(): MutableList<GoodsDetailEntity.Group> {
            val goodsDetailGroups = ArrayList<GoodsDetailEntity.Group>()
            for (i in 0 until 4) {
                goodsDetailGroups.add(GoodsDetailEntity().Group())
            }
            return goodsDetailGroups
        }


        /**
         * 购物车数据
         */
        fun cartDatas(): MutableList<ICart> {
            val datas = ArrayList<ICart>()
            datas.add(CartSingleEntity())
            datas.add(cartGroupDatas())
            datas.add(CartSingleEntity())
            return datas
        }

        /**
         * 购物车为您推荐
         */
        fun cartRecommendDatas(): MutableList<CartRecommendEntity> {
            val list = ArrayList<CartRecommendEntity>()
            for (i in 0..8) {
                val cartRecommendEntity = CartRecommendEntity()
                cartRecommendEntity.isBackgroundCorners = true
                if (i == 0) {
                    cartRecommendEntity.isShowRecommendText = true
                } else {
                    cartRecommendEntity.isShowRecommendText = false
                }
                list.add(cartRecommendEntity)
            }
            return list
        }


        /**
         * 购物车组合数据
         */
        fun cartGroupDatas(): CartGroupEntity {
            val cartGroupEntity = CartGroupEntity()
            val groups = ArrayList<CartGroupEntity.Group>()
            for (i in 0 until 2) {
                groups.add(CartGroupEntity.Group())
            }
            cartGroupEntity.groups = groups
            return cartGroupEntity
        }

        /**
         * 订单商品
         */
        fun orderGoods(): MutableList<String> {
            val goods = ArrayList<String>()
            for (i in 0 until 4) {
                goods.add("")
            }
            return goods
        }

        /**
         * 地址列表
         */
        fun addresses(): MutableList<AddressEntity> {
            var addresses = ArrayList<AddressEntity>()

            for (i in 0 until 8) {
                addresses.add(AddressEntity())
            }

            return addresses
        }

        /**
         * 商品清单数据
         */
        fun goodsListDatas(): MutableList<ICart> {
            val datas = ArrayList<ICart>()
            datas.add(GoodsSingleEntity())
            datas.add(GoodsSingleEntity())
            datas.add(goodsListGroupDatas())
            return datas
        }


        /**
         * 商品清单组合数据
         */
        fun goodsListGroupDatas(): GoodsGroupEntity {
            val goodsGroupEntity = GoodsGroupEntity()
            val groups = ArrayList<GoodsGroupEntity.Group>()
            for (i in 0 until 2) {
                groups.add(GoodsGroupEntity().Group())
            }
            goodsGroupEntity.groups = groups
            return goodsGroupEntity
        }

        /**
         * 说明书
         */
        fun explainDatas(): MutableList<GoodsDetailEntity.Explain> {
            val explainDatas = ArrayList<GoodsDetailEntity.Explain>()
            for (i in 0 until 9) {
                val explain = GoodsDetailEntity().Explain()
                when (i) {
                    0 -> {
                        explain.title = "药品名称"
                        explain.content = "金戈 枸橼酸西地那非片"
                    }
                    1 -> {
                        explain.title = "商品编码"
                        explain.content = "10803050"
                    }
                    2 -> {
                        explain.title = "生产厂家"
                        explain.content = "广州白云山制药股份有限公司广州白云山制药总厂"
                    }
                    3 -> {
                        explain.title = "适应症/功能主治"
                        explain.content = "金戈 枸橼酸西地那非适用于治疗勃起功能障碍"
                    }
                    4 -> {
                        explain.title = "成分"
                        explain.content = "枸橼酸西地那非片"
                    }
                    5 -> {
                        explain.title = "药品性状"
                        explain.content = "本品为薄膜衣片，除去包衣后显白色至类白色。"
                    }
                    6 -> {
                        explain.title = "药理毒理"
                        explain.content = "本品是治疗勃起功能障碍的口服药物。它是西地那非的枸橼酸盐，一种环磷酸鸟苷（cGMP）特异的5 型磷酸二酯酶（PDE5）的选择性抑制剂。"
                    }
                    else -> {
                        explain.title = "药品性状"
                        explain.content = "本品为薄膜衣片，除去包衣后显白色至类白色。"
                    }
                }
                explainDatas.add(explain)
            }
            return explainDatas
        }


        /**
         * 问答
         */
        fun askDatas(): MutableList<AskAnswerEntity> {
            val askDatas = ArrayList<AskAnswerEntity>()
            for (i in 0 until 10) {
                askDatas.add(AskAnswerEntity())
            }
            return askDatas
        }

        /**
         * 问答详情
         */
        fun askDetails(): MutableList<AskAnswerDetailEntity> {
            val askDetails = ArrayList<AskAnswerDetailEntity>()

            val askAnswerDetailEntity1 = AskAnswerDetailEntity()
            askAnswerDetailEntity1.type = IAsk.TYPE_ASK

            val askAnswerDetailEntity2 = AskAnswerDetailEntity()
            askAnswerDetailEntity2.type = IAsk.TYPE_ASK

            askDetails.add(askAnswerDetailEntity1)
            askDetails.add(askAnswerDetailEntity2)

            return askDetails
        }

        /**
         * 订单
         */
        fun orderDatas(): MutableList<OrderEntity> {
            val orderDatas = ArrayList<OrderEntity>()
            for (i in 0 until 10) {
                val orderEntity = OrderEntity()
                orderDatas.add(orderEntity)
            }
            return orderDatas
        }

        /**
         * 订单详情
         */
        fun orderDetails(): MutableList<OrderDetailEntity.OrderGoods> {
            val orderDetails = ArrayList<OrderDetailEntity.OrderGoods>()

            val data1 = OrderDetailEntity.OrderGoods()
            val goods1 = ArrayList<OrderDetailEntity.Goods>()
            goods1.add(OrderDetailEntity.Goods())
            data1.type = OrderDetailEntity.TYPE_SINGLE
            data1.goods = goods1

            val data2 = OrderDetailEntity.OrderGoods()
            val goods2 = ArrayList<OrderDetailEntity.Goods>()
            goods2.add(OrderDetailEntity.Goods())
            data2.type = OrderDetailEntity.TYPE_COURSE
            data2.goods = goods2

            val data3 = OrderDetailEntity.OrderGoods()
            val goods3 = ArrayList<OrderDetailEntity.Goods>()
            goods3.add(OrderDetailEntity.Goods())
            goods3.add(OrderDetailEntity.Goods())
            data3.type = OrderDetailEntity.TYPE_GROUP
            data3.goods = goods3


            orderDetails.add(data1)
            orderDetails.add(data2)
            orderDetails.add(data3)
            return orderDetails
        }

        /**
         * 浏览记录
         */
        fun browseRecords(): MutableList<BrowseRecordEntity> {
            val browseRecords = ArrayList<BrowseRecordEntity>()
            for (i in 0 until 10) {
                browseRecords.add(BrowseRecordEntity())
            }
            return browseRecords
        }


        /**
         * 搜索
         */
        fun searches():MutableList<SearchEntity>{
            val searchs = ArrayList<SearchEntity>()
            for (i in 0 until 9){
                searchs.add(SearchEntity())
            }
            return searchs
        }

        fun moreSearches():MutableList<SearchEntity>{
            val searchs = ArrayList<SearchEntity>()
            for (i in 0 until 9){
                searchs.add(SearchEntity())
            }
            return searchs
        }
    }
}