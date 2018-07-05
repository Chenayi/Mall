package com.kzj.mall.ui.dialog

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.animation.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.DataHelper
import com.kzj.mall.entity.home.HomeTabEntity
import razerdp.basepopup.BasePopupWindow


class HomeTabClassifyPop constructor(val context: Activity) : BasePopupWindow(context) {
    private var rv: RecyclerView? = null
    private var mAdapter: MyAdapter? = null
    private var onTabChooseLinstener: OnTabChooseLinstener? = null

    init {
        rv = findViewById(R.id.rv_tab) as RecyclerView
        rv?.layoutManager = GridLayoutManager(context.applicationContext, 4)
        mAdapter = MyAdapter()
        rv?.adapter = mAdapter
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            onTabChooseLinstener?.onTabChoose(position, mAdapter?.data?.get(position))
            dismiss()
        }

        findViewById(R.id.iv_close).setOnClickListener {
            dismiss()
        }
    }


    override fun getClickToDismissView(): View {
        return popupWindowView
    }

    override fun initShowAnimation(): Animation {
        val set = AnimationSet(false)
        val shakeAnimaw = TranslateAnimation(0f, 0f, -300f, 0f)
        shakeAnimaw.duration = 200
        set.addAnimation(defaultAlphaAnimation)
        set.addAnimation(shakeAnimaw)
        return set
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.pop_home_tab_classify)
    }

    override fun initAnimaView(): View {
        return findViewById(R.id.ll_content) as LinearLayout
    }

    fun setOnTabChooseLinstener(l: OnTabChooseLinstener) {
        this.onTabChooseLinstener = l
    }

    inner class MyAdapter : BaseAdapter<HomeTabEntity, BaseViewHolder>(R.layout.item_home_tab, DataHelper.homeTabDatas()) {
        override fun convert(helper: BaseViewHolder?, item: HomeTabEntity?) {
            val llItem = helper?.getView<LinearLayout>(R.id.ll_item)
            var itemWidth = ScreenUtils.getScreenWidth() / 4f
            val params: RelativeLayout.LayoutParams = llItem?.layoutParams as RelativeLayout.LayoutParams
            params.width = itemWidth.toInt()
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT
            llItem.layoutParams = params
            llItem.gravity = Gravity.CENTER

            helper?.setText(R.id.tv_name, item?.name)
        }
    }

    interface OnTabChooseLinstener {
        fun onTabChoose(p: Int?, homeTabEntity: HomeTabEntity?)
    }
}