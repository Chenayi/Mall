package com.kzj.mall.event

import com.kzj.mall.entity.GoodsDetailEntity

class PacketListEvent(packageList: GoodsDetailEntity.PackageList) {
    var packageList: GoodsDetailEntity.PackageList = packageList
}