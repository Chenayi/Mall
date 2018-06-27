package com.kzj.mall.ui.fragment.home

import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.HomeEntity

class HomeChildFragment : BaseHomeChildListFragment<IPresenter>() {

    companion object {
        fun newInstance(): HomeChildFragment {
            val homeChildFragment = HomeChildFragment()
            return homeChildFragment
        }
    }

    override fun initData() {
        super.initData()

        setBannerPadding(0, SizeUtils.dp2px(18f), 0, 0)

        var banners = ArrayList<String>()
        for (i in 0 until 4) {
            banners.add("")
        }
        setBannerData(banners)

        setListDatas(getNormalMultipleEntities())
    }


    fun getNormalMultipleEntities(): MutableList<HomeEntity> {
        val list = ArrayList<HomeEntity>()
        list.add(HomeEntity(HomeEntity.CLASSIFY))
        list.add(HomeEntity(HomeEntity.CHOICE))
        list.add(HomeEntity(HomeEntity.FLASH_SALE))
        list.add(HomeEntity(HomeEntity.CHOICE_GOODS))
        return list
    }
}