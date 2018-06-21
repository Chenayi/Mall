package com.kzj.mall.ui.fragment

import com.chad.library.adapter.base.BaseViewHolder
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
        return 0
    }
}