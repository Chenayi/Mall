package com.kzj.mall.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.MyAskAnswerAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentMyAskAnswerBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.activity.MyAskAnswerDetailActivity
import com.kzj.mall.utils.LocalDatas

class MyAskAnswerFragment : BaseFragment<IPresenter, FragmentMyAskAnswerBinding>() {
    private var mPosition = 0
    private var adapter: MyAskAnswerAdapter? = null

    companion object {
        fun newInstance(position: Int): Fragment {
            val myAskAnswerFragment = MyAskAnswerFragment()
            val b = Bundle()
            b?.putInt("position", position)
            myAskAnswerFragment?.arguments = b
            return myAskAnswerFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPosition = arguments?.getInt("position")!!
    }

    override fun getLayoutId() = R.layout.fragment_my_ask_answer

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        adapter = MyAskAnswerAdapter(LocalDatas.askDatas())
        adapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp, mBinding?.rvAsk?.parent as ViewGroup, false))
        adapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(context, MyAskAnswerDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        mBinding?.rvAsk?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvAsk?.adapter = adapter
    }
}