package com.kzj.mall.ui.fragment

import android.os.Handler
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.base.BaseListFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.HomeEntity

class HomeFragment : BaseListFragment<IPresenter, HomeEntity>() {
    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            return homeFragment
        }
    }

    override fun myHolder(helper: BaseViewHolder?, data: HomeEntity) {
    }

    override fun itemLayout(): Int {
        return R.layout.item_home
    }

    override fun onRefresh() {
        Handler().postDelayed(object : Runnable{
            override fun run() {
                finishRefresh(newDatas())
            }
        },2000)
    }

    override fun onLoadMore() {
        Handler().postDelayed(object : Runnable{
            override fun run() {
                finishLoadMore(moreDatas())
            }
        },2000)
    }

    fun newDatas(): MutableList<HomeEntity> {
        var datas = ArrayList<HomeEntity>()
        for (i in 0 until 10) {
            datas.add(HomeEntity())
        }
        return datas
    }

    fun moreDatas(): MutableList<HomeEntity> {
        var datas = ArrayList<HomeEntity>()
        for (i in 0 until 9) {
            datas.add(HomeEntity())
        }
        return datas
    }
}