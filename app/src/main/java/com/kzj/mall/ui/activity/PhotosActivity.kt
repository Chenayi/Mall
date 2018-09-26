package com.kzj.mall.ui.activity

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chrisbanes.photoview.PhotoView
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityPhotosBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.transformer.ScaleInTransformer
import com.tmall.ultraviewpager.UltraViewPager

class PhotosActivity:BaseActivity<IPresenter,ActivityPhotosBinding>() {

    private var advDatas:ArrayList<String>?=null
    private var position = 0

    override fun getLayoutId() = R.layout.activity_photos

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        immersionBarColor = R.color.black
        super.initImmersionBar()
    }

    override fun initData() {

        advDatas = intent?.getStringArrayListExtra("advDatas")
        position = intent?.getIntExtra("position",0)!!

        mBinding?.indicator?.setIndicatorsSize(advDatas?.size!!)
        mBinding?.ultraViewpager?.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        val adapter = UltraPagerAdapter(advDatas)
        mBinding?.ultraViewpager?.viewPager?.offscreenPageLimit = advDatas?.size!!
        mBinding?.ultraViewpager?.setAdapter(adapter);
        mBinding?.ultraViewpager?.setPageTransformer(true, ScaleInTransformer());
        mBinding?.ultraViewpager?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBinding?.indicator?.setSelectIndex(position)
            }

        })

        mBinding?.ultraViewpager?.setCurrentItem(position, false)
    }

    inner class UltraPagerAdapter constructor(val advDatas: MutableList<String>?) : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return advDatas?.size!!
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, null, false)
            val imageView = view?.findViewById<PhotoView>(R.id.iv_image)
           imageView?.setOnPhotoTapListener { view, x, y ->
               finish()
           }
//            imageView?.setOnClickListener {
//                finish()
//            }
            GlideApp.with(this@PhotosActivity)
                    .load(advDatas?.get(position))
                    .fitCenter()
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
}