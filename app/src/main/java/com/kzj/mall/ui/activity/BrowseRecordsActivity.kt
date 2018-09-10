package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.BrowseRecordsAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityBrowseRecordsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerBrowseRecordsComponent
import com.kzj.mall.di.module.BrowseRecordsModule
import com.kzj.mall.entity.BrowseRecordEntity
import com.kzj.mall.mvp.contract.BrowseRecordsContract
import com.kzj.mall.mvp.presenter.BrowseRecordPresenter
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.widget.RootLayout

/**
 * 浏览记录
 */
class BrowseRecordsActivity : BaseActivity<BrowseRecordPresenter, ActivityBrowseRecordsBinding>(), BrowseRecordsContract.View {
    private var deleteMode = false
    private var browseRecordsAdapter: BrowseRecordsAdapter? = null
    private var rootLayout: RootLayout? = null
    private var pageNo = 1

    override fun getLayoutId() = R.layout.activity_browse_records

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerBrowseRecordsComponent.builder()
                .appComponent(appComponent)
                .browseRecordsModule(BrowseRecordsModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        rootLayout = RootLayout.getInstance(this)
        browseRecordsAdapter = BrowseRecordsAdapter(ArrayList())
        browseRecordsAdapter?.setEnableLoadMore(true)
        mBinding?.rvRecord?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvRecord?.adapter = browseRecordsAdapter

        initLinstener()

        mPresenter?.browseRecords(pageNo, false, true)
    }

    private fun initLinstener() {
        mBinding?.allCheck?.setOnClickListener {
            val isAllCheck = isAllCheck()
            if (!isAllCheck) {
                setAllCheck()
            } else {
                cancelAllCheck()
            }
        }

        //删除
        mBinding?.tvDelete?.setOnClickListener {
            val ids = ArrayList<Long>()
            val datas = browseRecordsAdapter?.data
            datas?.let {
                for (i in 0 until it.size) {
                    if (it?.get(i)?.check!!) {
                        ids?.add(it?.get(i)?.likeId!!)
                    }
                }
            }

            ConfirmDialog.newInstance("取消", "删除", "确定要将这" + ids?.size + "件商品删除？")
                    .setOnConfirmClickListener(object :ConfirmDialog.OnConfirmClickListener{
                        override fun onLeftClick() {
                        }

                        override fun onRightClick() {
                            val idArray = LongArray(ids?.size)
                            for (i in 0 until ids?.size) {
                                idArray[i] = ids?.get(i)
                            }
                            mPresenter?.deleteRecords(idArray)
                        }

                    }).show(supportFragmentManager)
        }

        //清空
        rootLayout?.setOnRightOnClickListener1 {
            ConfirmDialog.newInstance("取消", "删除", "确定要清空商品？")
                    .setOnConfirmClickListener(object :ConfirmDialog.OnConfirmClickListener{
                        override fun onLeftClick() {
                        }

                        override fun onRightClick() {
                            val datas = browseRecordsAdapter?.data
                            val idArray = LongArray(datas?.size!!)
                            datas?.let {
                                for (i in 0 until it.size) {
                                    idArray[i] = it?.get(i)?.likeId!!
                                }
                            }
                            mPresenter?.deleteRecords(idArray)
                        }

                    }).show(supportFragmentManager)


        }

        rootLayout?.setOnRightOnClickListener {
            deleteMode = !deleteMode
            if (deleteMode) {
                rootLayout?.showOrHideRightText1(true)
                rootLayout?.setRightText("取消")
                mBinding?.rlDelete?.visibility = View.VISIBLE
            } else {
                rootLayout?.showOrHideRightText1(false)
                rootLayout?.setRightText("编辑")
                mBinding?.rlDelete?.visibility = View.GONE
            }

            browseRecordsAdapter?.deleteMode = this.deleteMode
            browseRecordsAdapter?.notifyDataSetChanged()
        }


        mBinding?.refreshLayout?.setOnRefreshListener {
            pageNo = 1
            mPresenter?.browseRecords(pageNo, false, false)
        }

        browseRecordsAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
            override fun onLoadMoreRequested() {
                pageNo += 1
                mPresenter?.browseRecords(pageNo, true, false)
            }

        }, mBinding?.rvRecord)

        browseRecordsAdapter?.setOnItemChildClickListener { adapter, view, position ->
            if (view?.id == R.id.iv_check) {
                val item = browseRecordsAdapter?.getItem(position)
                item?.check = !item?.check!!
                browseRecordsAdapter?.notifyItemChanged(position)

                mBinding?.allCheck?.isChecked = isAllCheck()
            }
        }
    }

    private fun setAllCheck() {
        for (i in 0 until browseRecordsAdapter?.data?.size!!) {
            if (!browseRecordsAdapter?.getItem(i)?.check!!) {
                browseRecordsAdapter?.getItem(i)?.check = true
            }
        }
        browseRecordsAdapter?.notifyDataSetChanged()
    }

    private fun cancelAllCheck() {
        for (i in 0 until browseRecordsAdapter?.data?.size!!) {
            if (browseRecordsAdapter?.getItem(i)?.check!!) {
                browseRecordsAdapter?.getItem(i)?.check = false
            }
        }
        browseRecordsAdapter?.notifyDataSetChanged()
    }

    private fun isAllCheck(): Boolean {
        for (i in 0 until browseRecordsAdapter?.data?.size!!) {
            if (!browseRecordsAdapter?.getItem(i)?.check!!) {
                return false
            }
        }
        return true
    }

    override fun deleteSuccrss() {
        pageNo = 1
        mPresenter?.browseRecords(pageNo,false,false)
    }

    override fun browseRecords(browseRecordEntity: BrowseRecordEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        val list = browseRecordEntity?.browserecords?.list
        if (list!=null){
            list?.let {
                browseRecordsAdapter?.setNewData(it)
                if (it?.size < C.PAGE_SIZE) {
                    browseRecordsAdapter?.loadMoreEnd()
                }
            }
        }else{
            browseRecordsAdapter?.setNewData(ArrayList())
        }
    }

    override fun loadMoreBrowseRecords(browseRecordEntity: BrowseRecordEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        browseRecordEntity?.browserecords?.list?.let {
            browseRecordsAdapter?.addData(it)
            if (it?.size < C.PAGE_SIZE) {
                browseRecordsAdapter?.loadMoreEnd()
            } else {
                browseRecordsAdapter?.loadMoreComplete()
            }
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }
}