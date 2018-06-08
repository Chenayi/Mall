package com.kzj.mall.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.ArrayList

class CommomViewPagerAdapter(val fm: FragmentManager, val fragments: MutableList<Fragment>) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return fragments?.get(position)
    }

    override fun getCount(): Int {
        return fragments?.size
    }
}