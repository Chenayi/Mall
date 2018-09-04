package com.kzj.mall.event

import com.kzj.mall.entity.address.CityEntity
import com.kzj.mall.entity.address.DistrictEntity

class CheckDEvent(districtBeen: DistrictEntity.DistrictBeen) {
    var districtBeen = districtBeen
}