package com.kzj.mall.ui.activity

import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityBannerBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.transformer.ScaleInTransformer
import com.kzj.mall.widget.RootLayout
import com.tmall.ultraviewpager.UltraViewPager

class BannerActivity : BaseActivity<IPresenter, ActivityBannerBinding>() {
    private var position = 0


    override fun getLayoutId() = R.layout.activity_banner
    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.white)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun initData() {
        position = intent?.getIntExtra("position", 0)!!
        val datas = datas()

        RootLayout.getInstance(this)
                .setTitle(datas.get(position).title)

        mBinding?.indicator?.setNoSelRes(R.drawable.indicator_gray)
        mBinding?.indicator?.setIndicatorsSize(datas?.size!!)
        mBinding?.ultraViewpager?.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        val adapter = UltraPagerAdapter(datas)
        mBinding?.ultraViewpager?.viewPager?.pageMargin = SizeUtils.dp2px(-15f)
        mBinding?.ultraViewpager?.viewPager?.offscreenPageLimit = datas.size
        mBinding?.ultraViewpager?.setAdapter(adapter);
        mBinding?.ultraViewpager?.setPageTransformer(true, ScaleInTransformer());
        mBinding?.ultraViewpager?.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                RootLayout.getInstance(this@BannerActivity)
                        .setTitle(datas.get(position).title)
                mBinding?.indicator?.setSelectIndex(position)
            }

        })

        mBinding?.ultraViewpager?.setCurrentItem(position, false)
    }


    inner class UltraPagerAdapter constructor(val advDatas: MutableList<Data>?) : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return advDatas?.size!!
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_banner_image, null, false)
            val imageView = view?.findViewById<ImageView>(R.id.iv_image)
            imageView?.setOnClickListener {
                jumpGoodsDetail(advDatas?.get(position)?.goodsInfoId)
            }
            GlideApp.with(this@BannerActivity)
                    .load(advDatas?.get(position)?.imageRes)
                    .placeholder(R.color.gray_default)
                    .into(imageView!!)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container?.removeView(`object` as View?)
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }

    private fun datas(): MutableList<Data> {
        val datas = ArrayList<Data>()

        for (i in 0 until 8) {
            val data = Data()
            when (i) {
                0 -> {
                    data.goodsInfoId = "35603"
                    data.title = "真男人强强联合"
                    data.imageRes = R.mipmap.yp_big_1
                }
                1 -> {
                    data.goodsInfoId = "26765"
                    data.title = "真男人强强联合"
                    data.imageRes = R.mipmap.yp_big_2
                }
                2 -> {
                    data.goodsInfoId = "12952"
                    data.title = "美丽女人这样吃"
                    data.imageRes = R.mipmap.yp_big_3
                }
                3 -> {
                    data.goodsInfoId = "24459"
                    data.title = "美丽女人这样吃"
                    data.imageRes = R.mipmap.yp_big_4
                }
                4 -> {
                    data.goodsInfoId = "36388"
                    data.title = "预防乙肝，健康相随"
                    data.imageRes =R.mipmap.yp_big_5
                }
                5 -> {
                    data.goodsInfoId = "29833"
                    data.title = "预防乙肝，健康相随"
                    data.imageRes =R.mipmap.yp_big_6
                }
                6 -> {
                    data.goodsInfoId = "44312"
                    data.title = "性福生活，魅力四射"
                    data.imageRes = R.mipmap.yp_big_7
                }
                7 -> {
                    data.goodsInfoId = "41259"
                    data.title = "性福生活，魅力四射"
                    data.imageRes = R.mipmap.yp_big_8
                }
            }
            datas.add(data)
        }

        return datas
    }

    class Data {
        var title: String? = null
        var imageRes: Int? = null
        var goodsInfoId: String? = null
    }


    /**
     * 跳转商品详情
     */
    private fun jumpGoodsDetail(goodsInfoId: String?) {
        val intent = Intent(this, GoodsDetailActivity::class.java)
        intent?.putExtra(C.GOODS_INFO_ID, goodsInfoId)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}