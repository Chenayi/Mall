package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.adapter.BrowseRecordsAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityBrowseRecordsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.widget.RootLayout

/**
 * 浏览记录
 */
class BrowseRecordsActivity : BaseActivity<IPresenter, ActivityBrowseRecordsBinding>() {
    private var deleteMode = false
    private var browseRecordsAdapter: BrowseRecordsAdapter? = null
    private var rootLayout: RootLayout? = null

    override fun getLayoutId() = R.layout.activity_browse_records

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        rootLayout = RootLayout.getInstance(this)
        browseRecordsAdapter = BrowseRecordsAdapter(LocalDatas.browseRecords())
        mBinding?.rvRecord?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvRecord?.adapter = browseRecordsAdapter

        initLinstener()
    }

    private fun initLinstener() {
        rootLayout?.setOnRightOnClickListener {
            deleteMode = !deleteMode
            if (deleteMode){
                rootLayout?.showOrHideRightText1(true)
                rootLayout?.setRightText("取消")
                mBinding?.rlDelete?.visibility = View.VISIBLE
            }else{
                rootLayout?.showOrHideRightText1(false)
                rootLayout?.setRightText("编辑")
                mBinding?.rlDelete?.visibility = View.GONE
            }
            changeDeleteMode()
        }
    }

    private fun changeDeleteMode(){
        val datas = browseRecordsAdapter?.datas
        for (i in 0 until datas?.size!!){
            val data = datas?.get(i)
            data?.deleteMode = deleteMode
        }
        browseRecordsAdapter?.notifyDataSetChanged()
    }
}