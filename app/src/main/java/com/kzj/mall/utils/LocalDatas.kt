package com.kzj.mall.utils

import android.text.TextUtils
import com.kzj.mall.R
import com.kzj.mall.entity.*
import com.kzj.mall.entity.address.AddressEntity
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import com.kzj.mall.entity.ask.AskAnswerEntity
import com.kzj.mall.entity.ask.IAsk
import com.kzj.mall.entity.cart.*
import com.kzj.mall.entity.home.*
import com.kzj.mall.entity.order.OrderDetailEntity
import com.kzj.mall.entity.order.OrderEntity

class LocalDatas {
    companion object {

        /**
         * 首页广告图
         */
        fun homeBannerData(): HomeHeaderBannerEntity {
            val homeHeaderBannerEntity = HomeHeaderBannerEntity()
            val banners = ArrayList<HomeHeaderBannerEntity.Adds>()

            val banner1 = HomeHeaderBannerEntity().Adds()
            banner1.adCode = "http://m.qpic.cn/psb?/V141YaNB4X7WnB/H9J8aQmQKC7q4kRnjGVLJ0ooN5djpe3*y.RCe3pMZfQ!/b/dDYBAAAAAAAA&bo=9APCAQAAAAADBxY!&rf=viewer_4"
            banner1.goodsInfoId = "-1"

            val banner2 = HomeHeaderBannerEntity().Adds()
            banner2.adCode = "http://m.qpic.cn/psb?/V141YaNB4X7WnB/xaFF8TJn93N3WNp55Z7QBGFY3XpNgiY0OTXj8x4Z57E!/b/dFMBAAAAAAAA&bo=9APCAQAAAAADBxY!&rf=viewer_4"
            banner2.goodsInfoId = "29954"

            val banner3 = HomeHeaderBannerEntity().Adds()
            banner3.adCode = "http://m.qpic.cn/psb?/V141YaNB4X7WnB/D352a*Ar9VXBQx.eMiBNtizgne2VY53dQG37*3NNmLc!/b/dFQBAAAAAAAA&bo=9APCAQAAAAADBxY!&rf=viewer_4"
            banner3.goodsInfoId = "17124"

            val banner4 = HomeHeaderBannerEntity().Adds()
            banner4.adCode = "http://m.qpic.cn/psb?/V141YaNB4X7WnB/9nsNOgXywbEKTJUZRwGeIj2dkn8QiR94cMiv54o8KxY!/b/dDIBAAAAAAAA&bo=9APCAQAAAAADBxY!&rf=viewer_4"
            banner4.goodsInfoId = "21536"

            banners.add(banner1)
            banners.add(banner2)
            banners.add(banner3)
            banners.add(banner4)

            homeHeaderBannerEntity.adss = banners

            return homeHeaderBannerEntity
        }

        /**
         * 男科广告图
         */
        fun manBannerData(): HomeHeaderBannerEntity {
            val homeHeaderBannerEntity = HomeHeaderBannerEntity()
            val banners = ArrayList<HomeHeaderBannerEntity.Adds>()

            val banner1 = HomeHeaderBannerEntity().Adds()
            banner1.adCode = "http://m.qpic.cn/psb?/V141YaNB4X7WnB/8D7nfNwJqI4r7glm3K1gpLiF*lLvpgZn4dC0KE2ks.0!/b/dDQBAAAAAAAA&bo=tgPCAQAAAAADF0Q!&rf=viewer_4"
            banner1.goodsInfoId = "26765"

            val banner2 = HomeHeaderBannerEntity().Adds()
            banner2.adCode = "http://m.qpic.cn/psb?/V141YaNB4X7WnB/AGFPuXppAU6JvgFe1d46VrPkG4LIpm7rMiPFPHWxgM4!/b/dDEBAAAAAAAA&bo=tgPCAQAAAAADJ3Q!&rf=viewer_4"
            banner2.goodsInfoId = "35603"

            val banner3 = HomeHeaderBannerEntity().Adds()
            banner3.adCode = "http://m.qpic.cn/psb?/V141YaNB4X7WnB/5OKL*mPvKboArvy1KaG9eS2pu8BmOqhRwe*BTrlj5NI!/b/dFMBAAAAAAAA&bo=tgPCAQAAAAADF0Q!&rf=viewer_4"
            banner3.goodsInfoId = "27400"

            banners.add(banner1)
            banners.add(banner2)
            banners.add(banner3)

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

            val banners = ArrayList<HomeAdvBannerEntity.Banners>()

            val banner1 = HomeAdvBannerEntity.Banners()
            banner1.adv = R.mipmap.h1
            banner1.goodsInfoId = "45468"

            val banner2 = HomeAdvBannerEntity.Banners()
            banner2.adv = R.mipmap.h2
            banner2.goodsInfoId = "27144"

            banners.add(banner1)
            banners.add(banner2)

            homeAdvBannerData.banners = banners

            return homeAdvBannerData
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
         * 男科穿插广告
         */
        fun andrologyAdvBannerData(): AndrologyAdvBannerEntity {
            val andrologyAdvBannerData = AndrologyAdvBannerEntity()
            val banners = ArrayList<AndrologyAdvBannerEntity.Banners>()

            val b1 = AndrologyAdvBannerEntity.Banners()
            b1.imgRes = R.mipmap.b1
            b1.goodsInfoId = "37438"
            val b2 = AndrologyAdvBannerEntity.Banners()
            b2.imgRes = R.mipmap.b2
            b2.goodsInfoId = "2703"
            val b3 = AndrologyAdvBannerEntity.Banners()
            b3.imgRes = R.mipmap.b3
            b3.goodsInfoId = "35146"

            banners.add(b1)
            banners.add(b2)
            banners.add(b3)

            andrologyAdvBannerData.banners = banners

            return andrologyAdvBannerData
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
                groups.add(GoodsGroupEntity.Group())
            }
            goodsGroupEntity.groups = groups
            return goodsGroupEntity
        }

        /**
         * 说明书
         */
        fun explainDatas(goodsName: String?, goodsNo: String?, goodsManufacturer: String?, goodsBooks: GoodsDetailEntity.GoodsBooks?): MutableList<GoodsDetailEntity.Explain> {
            val explainDatas = ArrayList<GoodsDetailEntity.Explain>()

            goodsName?.let {
                val d = GoodsDetailEntity.Explain()
                d.title = "药品名称"
                d?.content = it
                explainDatas.add(d)
            }

            goodsNo?.let {
                val d = GoodsDetailEntity.Explain()
                d.title = "商品编码"
                d?.content = it
                explainDatas.add(d)
            }

            goodsManufacturer?.let {
                val d = GoodsDetailEntity.Explain()
                d.title = "生产厂家"
                d?.content = it
                explainDatas.add(d)
            }


            goodsBooks?.let {

                //成分
                if (!TextUtils.isEmpty(it?.component)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "成分"
                    d?.content = it?.component
                    explainDatas.add(d)
                }

                //药品性状
                if (!TextUtils.isEmpty(it?.properties)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "药品性状"
                    d?.content = it?.properties
                    explainDatas.add(d)
                }

                //药理毒理
                if (!TextUtils.isEmpty(it?.toxicology)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "药理毒理"
                    d?.content = it?.toxicology
                    explainDatas.add(d)
                }

                //药代动力学
                if (!TextUtils.isEmpty(it?.pharmacokinetics)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "药代动力学"
                    d?.content = it?.pharmacokinetics
                    explainDatas.add(d)
                }

                //适 应 症
                if (!TextUtils.isEmpty(it?.indication)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "适 应 症"
                    d?.content = it?.indication
                    explainDatas.add(d)
                }

                //用法用量
                if (!TextUtils.isEmpty(it?.usage_dosage)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "用法用量"
                    d?.content = it?.usage_dosage
                    explainDatas.add(d)
                }

                //不良反应
                if (!TextUtils.isEmpty(it?.untoward_effect)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "不良反应"
                    d?.content = it?.untoward_effect
                    explainDatas.add(d)
                }

                //禁 忌 症
                if (!TextUtils.isEmpty(it?.contraindication)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "禁 忌 症"
                    d?.content = it?.contraindication
                    explainDatas.add(d)
                }


                //注意事项
                if (!TextUtils.isEmpty(it?.announcements)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "注意事项"
                    d?.content = it?.announcements
                    explainDatas.add(d)
                }

                //药物相互作用
                if (!TextUtils.isEmpty(it?.drug_interaction)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "药物相互作用"
                    d?.content = it?.drug_interaction
                    explainDatas.add(d)
                }


                // 贮　　藏
                if (!TextUtils.isEmpty(it?.depot)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "贮　　藏"
                    d?.content = it?.depot
                    explainDatas.add(d)
                }

                //包　　装
                if (!TextUtils.isEmpty(it?.packaging)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "包　　装"
                    d?.content = it?.packaging
                    explainDatas.add(d)
                }

                //有 效 期
                if (!TextUtils.isEmpty(it?.period_of_validity)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "有 效 期"
                    d?.content = it?.period_of_validity
                    explainDatas.add(d)
                }


                //孕妇及哺乳期妇女用药
                if (!TextUtils.isEmpty(it?.pregnant_lactating)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "孕妇及哺乳期妇女用药"
                    d?.content = it?.pregnant_lactating
                    explainDatas.add(d)
                }

                //儿童用药
                if (!TextUtils.isEmpty(it?.children)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "儿童用药"
                    d?.content = it?.children
                    explainDatas.add(d)
                }

                //药物过量
                if (!TextUtils.isEmpty(it?.drug_overdose)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "药物过量"
                    d?.content = it?.drug_overdose
                    explainDatas.add(d)
                }

                //适宜人群
                if (!TextUtils.isEmpty(it?.apply_crowd)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "药物过量"
                    d?.content = it?.apply_crowd
                    explainDatas.add(d)
                }

                //推荐人群
                if (!TextUtils.isEmpty(it?.recommended_groups)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "推荐人群"
                    d?.content = it?.recommended_groups
                    explainDatas.add(d)
                }

                //不适宜人群
                if (!TextUtils.isEmpty(it?.not_apply_crowd)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "不适宜人群"
                    d?.content = it?.not_apply_crowd
                    explainDatas.add(d)
                }

                //功效成份
                if (!TextUtils.isEmpty(it?.effect_component)){
                    val d = GoodsDetailEntity.Explain()
                    d.title = "功效成份"
                    d?.content = it?.effect_component
                    explainDatas.add(d)
                }
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
//        fun orderDetails(): MutableList<OrderDetailEntity.OrderGoods> {
//            val orderDetails = ArrayList<OrderDetailEntity.OrderGoods>()
//
//            val data1 = OrderDetailEntity.OrderGoods()
//            val goods1 = ArrayList<OrderDetailEntity.Goods>()
//            goods1.add(OrderDetailEntity.Goods())
//            data1.type = OrderDetailEntity.TYPE_SINGLE
//            data1.goods = goods1
//
//            val data2 = OrderDetailEntity.OrderGoods()
//            val goods2 = ArrayList<OrderDetailEntity.Goods>()
//            goods2.add(OrderDetailEntity.Goods())
//            data2.type = OrderDetailEntity.TYPE_COURSE
//            data2.goods = goods2
//
//            val data3 = OrderDetailEntity.OrderGoods()
//            val goods3 = ArrayList<OrderDetailEntity.Goods>()
//            goods3.add(OrderDetailEntity.Goods())
//            goods3.add(OrderDetailEntity.Goods())
//            data3.type = OrderDetailEntity.TYPE_GROUP
//            data3.goods = goods3
//
//
//            orderDetails.add(data1)
//            orderDetails.add(data2)
//            orderDetails.add(data3)
//            return orderDetails
//        }

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
        fun searches(): MutableList<SearchEntity> {
            val searchs = ArrayList<SearchEntity>()
            for (i in 0 until 9) {
                searchs.add(SearchEntity())
            }
            return searchs
        }

        fun moreSearches(): MutableList<SearchEntity> {
            val searchs = ArrayList<SearchEntity>()
            for (i in 0 until 9) {
                searchs.add(SearchEntity())
            }
            return searchs
        }
    }
}