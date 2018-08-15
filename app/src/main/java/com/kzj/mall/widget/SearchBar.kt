package com.kzj.mall.widget

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.KeyboardUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.SearchBarBinding

class SearchBar : BaseRelativeLayout<SearchBarBinding>, View.OnClickListener {
    private var onSearchListener: OnSearchListener? = null
    private var onBackClickListener: OnBackClickListener? = null

    private var text: String? = null
    private var isOpenSearch = true
    private var isFirst = true

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId() = R.layout.search_bar

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        text = hintText()
        mBinding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = etText()
                if (!TextUtils.isEmpty(text)) {
                    mBinding?.ivClear?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClear?.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        mBinding?.ivBack?.setOnClickListener(this)
        mBinding?.ivClear?.setOnClickListener(this)
        mBinding?.tvSearch?.setOnClickListener(this)
        mBinding?.ivListGrid?.setOnClickListener(this)
        mBinding?.tvSearchContent?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                hideKeyboard()
                onBackClickListener?.onBackClick()
            }
            R.id.iv_clear -> {
                mBinding?.etSearch?.setText("")
            }
            R.id.tv_search -> {
                val etText = etText()
                if (!TextUtils.isEmpty(etText)) {
                    text = etText()
                } else {
                    text = hintText()
                }
                startSearch(text)
                onSearchListener?.onSearch(text)
                hideKeyboard()
            }
            R.id.iv_list_grid -> {

            }

            R.id.tv_search_content -> {
                openSearch()
            }
        }
    }

    fun openSearch() {
        mBinding?.tvSearch?.visibility = View.VISIBLE
        mBinding?.ivListGrid?.visibility = View.GONE
        mBinding?.etSearch?.visibility = View.VISIBLE
        mBinding?.tvSearchContent?.visibility = View.GONE
        mBinding?.etSearch?.requestFocus()
        mBinding?.etSearch?.setText(tvText())
        mBinding?.etSearch?.setSelection(tvText()?.length!!)
        KeyboardUtils.showSoftInput(mBinding?.etSearch)
        isOpenSearch = true
    }

    fun closeSearch() {
        mBinding?.tvSearch?.visibility = View.GONE
        mBinding?.ivListGrid?.visibility = View.VISIBLE
        mBinding?.etSearch?.visibility = View.GONE
        mBinding?.tvSearchContent?.visibility = View.VISIBLE
        mBinding?.tvSearchContent?.setText(text)
        isOpenSearch = false
    }

    fun isOpenSearch() = isOpenSearch
    fun isFirst() = isFirst

    fun hideKeyboard(){
        KeyboardUtils.hideSoftInput(mBinding?.etSearch)
    }

    fun startSearch(text: String?) {
        closeSearch()
        mBinding?.tvSearchContent?.setText(text)
        isFirst = false
        hideKeyboard()
    }


    fun hintText() = mBinding?.etSearch?.hint?.toString()?.trim()
    fun etText() = mBinding?.etSearch?.text?.toString()?.trim()
    fun tvText() = mBinding?.tvSearchContent?.text?.toString()?.trim()

    fun setOnSearchListener(l: OnSearchListener) {
        onSearchListener = l
    }

    fun setOnBackClickListener(l: OnBackClickListener) {
        onBackClickListener = l
    }

    interface OnSearchListener {
        fun onSearch(text: String?)
    }

    interface OnBackClickListener {
        fun onBackClick()
    }
}