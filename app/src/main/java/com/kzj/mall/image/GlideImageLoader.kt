package com.kzj.mall.image

import android.content.Context
import android.widget.ImageView
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.youth.banner.loader.ImageLoader

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        GlideApp
                .with(context!!)
                .load(path)
                .placeholder(R.color.gray_default)
                .into(imageView!!)
    }
}