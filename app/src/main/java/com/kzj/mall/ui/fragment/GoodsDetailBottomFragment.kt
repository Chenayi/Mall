package com.kzj.mall.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailBinding
import com.kzj.mall.utils.LocalDatas
import me.yokeyword.fragmentation.SupportFragment


class GoodsDetailBottomFragment : BaseFragment<IPresenter, FragmentGoodsDetailBinding>(), View.OnClickListener {
    private var barHeight = 0
    private val mFragments = arrayOfNulls<SupportFragment>(4)
    val FIRST = 0
    val SECOND = 1
    private var curFragment = FIRST

    companion object {
        fun newInstance(barHeight: Int): GoodsDetailBottomFragment {
            val goodsDetailBottomFragment = GoodsDetailBottomFragment()
            var b = Bundle()
            b.putInt("barHeight", barHeight)
            goodsDetailBottomFragment.arguments = b
            return goodsDetailBottomFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val margin = SizeUtils.dp2px(10f)
        arguments?.getInt("barHeight")?.let {
            barHeight = it + margin
        }
        mBinding?.llDetailRoot?.setPadding(margin, barHeight, margin, margin)
        return view
    }

    override fun initData() {

        val firstFragment = findChildFragment(GoodsDetailDescribeFragment::class.java)
        if (firstFragment == null) {
            mFragments[FIRST] = GoodsDetailDescribeFragment.newInstance();
            mFragments[SECOND] = GoodsDetailExplainFragment.newInstance(LocalDatas.explainDatas());

            loadMultipleRootFragment(R.id.fl_goods_detail, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]);
        }else{
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findFragment(GoodsDetailExplainFragment::class.java)
        }



        mBinding?.rlDescribe?.setOnClickListener(this)
        mBinding?.rlExplain?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_describe -> {
                mBinding?.tvExplain?.setTextColor(Color.parseColor("#2E3033"))
                mBinding?.tvExplain?.paint?.isFakeBoldText = false
                mBinding?.viewExplain?.visibility = View.GONE

                mBinding?.tvDescribe?.setTextColor(Color.parseColor("#48B828"))
                mBinding?.tvDescribe?.paint?.isFakeBoldText = true
                mBinding?.viewDescribe?.visibility = View.VISIBLE

                if (curFragment!= FIRST){
                    showHideFragment(mFragments[FIRST],mFragments[SECOND])
                    curFragment = FIRST
                }
            }
            R.id.rl_explain -> {
                mBinding?.tvDescribe?.setTextColor(Color.parseColor("#2E3033"))
                mBinding?.tvDescribe?.paint?.isFakeBoldText = false
                mBinding?.viewDescribe?.visibility = View.GONE

                mBinding?.tvExplain?.setTextColor(Color.parseColor("#48B828"))
                mBinding?.tvExplain?.paint?.isFakeBoldText = true
                mBinding?.viewExplain?.visibility = View.VISIBLE

                if (curFragment!= SECOND){
                    showHideFragment(mFragments[SECOND],mFragments[FIRST])
                    curFragment = SECOND
                }
            }
        }
    }
}