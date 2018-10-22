package com.kzj.mall.ui.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGoodsDetailBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.utils.LocalDatas
import me.yokeyword.fragmentation.SupportFragment


class GoodsDetailBottomFragment : BaseFragment<IPresenter, FragmentGoodsDetailBinding>(), View.OnClickListener {
    private var barHeight = 0
    private val mFragments = arrayOfNulls<SupportFragment>(4)
    val FIRST = 0
    val SECOND = 1
    private var curFragment = FIRST

    private var goodsDetailEntity: GoodsDetailEntity? = null

    companion object {
        fun newInstance(barHeight: Int, goodsDetailEntity: GoodsDetailEntity?): GoodsDetailBottomFragment {
            val goodsDetailBottomFragment = GoodsDetailBottomFragment()
            var b = Bundle()
            b.putInt("barHeight", barHeight)
            b.putSerializable("goodsDetailEntity", goodsDetailEntity)
            goodsDetailBottomFragment.arguments = b
            return goodsDetailBottomFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_goods_detail
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        arguments?.getInt("barHeight")?.let {
            barHeight = it
        }
        arguments?.getSerializable("goodsDetailEntity")?.let { it ->
            goodsDetailEntity = it as GoodsDetailEntity
        }
        mBinding?.llDetailRoot?.setPadding(0, barHeight, 0, 0)
        return view
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }


    override fun initData() {

        val firstFragment = findChildFragment(GoodsDetailDescribeFragment::class.java)
        if (firstFragment == null) {
            mFragments[FIRST] = GoodsDetailDescribeFragment.newInstance(goodsDetailEntity?.gin?.goods_mobile_desc);
            val explainDatas = LocalDatas.explainDatas(goodsDetailEntity?.gn?.goodsName,
                    goodsDetailEntity?.gn?.goodsNo,
                    goodsDetailEntity?.gn?.goodsManufacturer,
                    goodsDetailEntity?.gn?.goodsBooks)
            mFragments[SECOND] = GoodsDetailExplainFragment.newInstance(explainDatas);

            loadMultipleRootFragment(R.id.fl_goods_detail, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]);
        } else {
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findFragment(GoodsDetailExplainFragment::class.java)
        }



        mBinding?.rlDescribe?.setOnClickListener(this)
        mBinding?.rlExplain?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        //图文描述
            R.id.rl_describe -> {
                mBinding?.rlExplain?.setBackgroundResource(R.drawable.right_green_stroke_corners_9999)
                mBinding?.tvExplain?.setTextColor(Color.parseColor("#2E3033"))
                mBinding?.rlDescribe?.setBackgroundResource(R.drawable.left_green_corners_9999)
                mBinding?.tvDescribe?.setTextColor(Color.WHITE)
                if (curFragment != FIRST) {
                    showHideFragment(mFragments[FIRST], mFragments[SECOND])
                    curFragment = FIRST
                }
            }
        //说明书
            R.id.rl_explain -> {
                mBinding?.rlDescribe?.setBackgroundResource(R.drawable.left_green_stroke_corners_9999)
                mBinding?.tvDescribe?.setTextColor(Color.parseColor("#2E3033"))
                mBinding?.tvExplain?.setTextColor(Color.WHITE)
                mBinding?.rlExplain?.setBackgroundResource(R.drawable.right_green_corners_9999)
                if (curFragment != SECOND) {
                    showHideFragment(mFragments[SECOND], mFragments[FIRST])
                    curFragment = SECOND
                }
            }
        }
    }
}