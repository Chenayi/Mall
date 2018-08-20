package com.kzj.mall.ui.dialog

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.kzj.mall.R
import razerdp.basepopup.BasePopupWindow

class PrescriptionPop(val mContext: Context) : BasePopupWindow(mContext), View.OnClickListener {
    private var rlDefault: RelativeLayout? = null
    private var rl1: RelativeLayout? = null
    private var rl2: RelativeLayout? = null
    private var tvDefault: TextView? = null
    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var ivCheckDefault: ImageView? = null
    private var ivCheck1: ImageView? = null
    private var ivCheck2: ImageView? = null
    private var onTypeChooseListener: OnTypeChooseListener? = null
    private var curChoose = NULL

    companion object {
        val NULL = "null"
        val _1 = "1"
        val _2 = "2"
    }


    override fun getClickToDismissView() = popupWindowView

    init {
        rlDefault = findViewById(R.id.rl_default) as RelativeLayout?
        rlDefault?.setOnClickListener(this)
        rl1 = findViewById(R.id.rl_1) as RelativeLayout?
        rl1?.setOnClickListener(this)
        rl2 = findViewById(R.id.rl_2) as RelativeLayout?
        rl2?.setOnClickListener(this)

        tvDefault = findViewById(R.id.tv_default) as TextView?
        tv1 = findViewById(R.id.tv_1) as TextView?
        tv2 = findViewById(R.id.tv_2) as TextView?

        ivCheckDefault = findViewById(R.id.iv_check_default) as ImageView?
        ivCheck1 = findViewById(R.id.iv_check_1) as ImageView?
        ivCheck2 = findViewById(R.id.iv_check_2) as ImageView?
    }

    override fun initShowAnimation(): Animation {
        val set = AnimationSet(false)
        val shakeAnimaw = TranslateAnimation(0f, 0f, -300f, 0f)
        shakeAnimaw.duration = 100
        set.addAnimation(shakeAnimaw)
        return set
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.pop_search_sort)
    }

    override fun initAnimaView(): View {
        return findViewById(R.id.ll_container) as LinearLayout
    }

    fun setOnTypeChooseListener(l: OnTypeChooseListener) {
        onTypeChooseListener = l
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_default -> {
                if (!curChoose.equals(NULL)) {

                    setDeafult()

                    onTypeChooseListener?.onTypeChoose(null)

                    dismiss()
                }
            }
            R.id.rl_1 -> {
                if (!curChoose.equals(_1)) {
                    rlDefault?.setBackgroundColor(Color.WHITE)
                    rl2?.setBackgroundColor(Color.WHITE)
                    rl1?.setBackgroundColor(Color.parseColor("#F2FFEE"))

                    tvDefault?.setTextColor(Color.parseColor("#6A6E75"))
                    tv2?.setTextColor(Color.parseColor("#6A6E75"))
                    tv1?.setTextColor(Color.parseColor("#4FB233"))

                    ivCheckDefault?.visibility = View.GONE
                    ivCheck2?.visibility = View.GONE
                    ivCheck1?.visibility = View.VISIBLE

                    onTypeChooseListener?.onTypeChoose(_1)

                    curChoose = _1

                    dismiss()
                }
            }
            R.id.rl_2 -> {
                if (!curChoose.equals(_2)) {
                    rl1?.setBackgroundColor(Color.WHITE)
                    rlDefault?.setBackgroundColor(Color.WHITE)
                    rl2?.setBackgroundColor(Color.parseColor("#F2FFEE"))

                    tvDefault?.setTextColor(Color.parseColor("#6A6E75"))
                    tv1?.setTextColor(Color.parseColor("#6A6E75"))
                    tv2?.setTextColor(Color.parseColor("#4FB233"))

                    ivCheck1?.visibility = View.GONE
                    ivCheckDefault?.visibility = View.GONE
                    ivCheck2?.visibility = View.VISIBLE

                    onTypeChooseListener?.onTypeChoose(_2)

                    curChoose = _2

                    dismiss()
                }
            }
        }
    }

    fun setDeafult(){
        rl1?.setBackgroundColor(Color.WHITE)
        rl2?.setBackgroundColor(Color.WHITE)
        rlDefault?.setBackgroundColor(Color.parseColor("#F2FFEE"))

        tv1?.setTextColor(Color.parseColor("#6A6E75"))
        tv2?.setTextColor(Color.parseColor("#6A6E75"))
        tvDefault?.setTextColor(Color.parseColor("#4FB233"))

        ivCheck1?.visibility = View.GONE
        ivCheck2?.visibility = View.GONE
        ivCheckDefault?.visibility = View.VISIBLE

        curChoose = NULL
    }

    interface OnTypeChooseListener {
        fun onTypeChoose(what: String?)
    }
}