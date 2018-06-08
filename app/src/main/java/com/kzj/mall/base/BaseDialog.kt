package com.kzj.mall.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kzj.mall.R
import android.view.WindowManager
import android.view.Gravity
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils


abstract class BaseDialog<P : IPresenter, D : ViewDataBinding> : DialogFragment() {
    protected var binding: D? = null

    /**
     * 灰度深浅
     */
    private var dimAmount = 0.5f

    /**
     * 是否底部显示
     */
    private var showBottom: Boolean = false

    /**
     * 是否点击外部取消
     */
    private var outCancel = true

    /**
     * 是否返回取消
     */
    private var backCancel = true

    /**
     * 左右边距
     */
    private var margin: Int = 0

    /**
     * 宽度
     */
    private var width: Int = 0

    /**
     * 高度
     */
    private var height: Int = 0

    /**
     * 动画
     */
    private var animStyle: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        binding = DataBindingUtil.bind(view)
        initData()
        return view
    }

    override fun onStart() {
        super.onStart()
        initParams();
    }

    /**
     * 初始化参数
     */
    private fun initParams() {
        val window = dialog.window
        if (window != null) {
            val lp = window.attributes
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount
            //是否在底部显示
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM
                if (animStyle == 0) {
                    animStyle = R.style.DefaultDialogAnimation
                }
            }

            //设置dialog宽度
            if (width == 0) {
                lp.width = ScreenUtils.getScreenWidth() - 2 * SizeUtils.dp2px(margin.toFloat())
            } else if (width == -1) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                lp.width = SizeUtils.dp2px(width.toFloat())
            }

            //设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                lp.height = SizeUtils.dp2px(height.toFloat())
            }

            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle)
            window.attributes = lp
        }
        isCancelable = outCancel
    }

    abstract fun getLayoutId(): Int

    abstract fun initData();

    /**
     * 左右边距
     */
    fun setMargin(margin: Int) {
        this.margin = margin
    }

    /**
     * 是否从底部出现
     */
    fun setShowBottom(showBottom: Boolean) {
        this.showBottom = showBottom
    }

    /**
     * 是否点击外部取消
     */
    fun setOutCancel(outCancel: Boolean) {
        this.outCancel = outCancel
    }

    /**
     * 是否返回取消
     */
    fun setBackCancel(backCancel: Boolean) {
        this.backCancel = backCancel
    }

    /**
     * 动画效果
     */
    fun setAnimStyle(animStyle: Int) {
        this.animStyle = animStyle
    }

    /**
     *  显示
     */
    fun show(manager: FragmentManager) {
        val ft = manager.beginTransaction()
        if (this.isAdded) {
            ft.remove(this).commit()
        }
        ft.add(this, System.currentTimeMillis().toString())
        ft.commitAllowingStateLoss()
    }
}