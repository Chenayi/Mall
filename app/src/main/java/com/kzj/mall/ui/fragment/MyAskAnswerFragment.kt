package com.kzj.mall.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseListFragment
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerMyAskAnswerComponent
import com.kzj.mall.di.module.MyAskAnswerModule
import com.kzj.mall.entity.ask.AskAnswerEntity
import com.kzj.mall.mvp.contract.MyAskAnswerContract
import com.kzj.mall.mvp.presenter.MyAskAnswerPresenter
import java.text.SimpleDateFormat

class MyAskAnswerFragment : BaseListFragment<MyAskAnswerPresenter, AskAnswerEntity.List>(), MyAskAnswerContract.View {
    private var qStatus = "0"

    companion object {
        fun newInstance(qStatus: String): Fragment {
            val myAskAnswerFragment = MyAskAnswerFragment()
            val b = Bundle()
            b?.putString("qStatus", qStatus)
            myAskAnswerFragment?.arguments = b
            return myAskAnswerFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qStatus = arguments?.getString("qStatus")!!
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerMyAskAnswerComponent.builder()
                .appComponent(appComponent)
                .myAskAnswerModule(MyAskAnswerModule(this))
                .build()
                .inject(this)
    }


    override fun initData() {
        super.initData()
        pageNo = 1
        mPresenter?.myAskAnswer(qStatus, pageNo, C.PAGE_SIZE, false, true)
    }

    override fun myHolder(helper: BaseViewHolder?, data: AskAnswerEntity.List) {
        helper?.setText(R.id.tv_content, data?.qContent)
                ?.setText(R.id.tv_add_time, TimeUtils.millis2String(data?.qAddTime!!, SimpleDateFormat("yyyy-MM-dd")))
                ?.setText(R.id.tv_type_name, data?.cat?.catName)

        val tvRepeatStatus = helper?.getView<TextView>(R.id.tv_repeat_status)
        val qStatus = data?.qStatus
        if (qStatus?.equals("2") == true){
            tvRepeatStatus?.setText("已回复")
            tvRepeatStatus?.setTextColor(Color.parseColor("#C2C6CC"))
        }else{
            tvRepeatStatus?.setText("待回复")
            tvRepeatStatus?.setTextColor(Color.parseColor("#FF4F0AC"))
        }
    }

    override fun itemLayout() = R.layout.item_my_ask_answer
    override fun onRefresh() {
        pageNo = 1
        mPresenter?.myAskAnswer(qStatus, pageNo, C.PAGE_SIZE, false, false)
    }

    override fun onLoadMore() {
        pageNo = 1
        mPresenter?.myAskAnswer(qStatus, pageNo, C.PAGE_SIZE, true, false)
    }

    override fun emptyMsg() = "暂时没有相关问答~"

    override fun onItemClick(view: View, position: Int, data: AskAnswerEntity.List?) {
    }

    override fun showAskAnswer(datas: MutableList<AskAnswerEntity.List>?) {
        datas?.let {
            finishRefresh(it)
        }
    }

    override fun loadMoreAskAnswer(datas: MutableList<AskAnswerEntity.List>?) {
        datas?.let {
            finishLoadMore(it)
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