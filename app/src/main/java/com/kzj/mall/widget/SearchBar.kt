package com.kzj.mall.widget

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.blankj.utilcode.util.KeyboardUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.SearchBarBinding

class SearchBar : BaseRelativeLayout<SearchBarBinding>, View.OnClickListener {
    private var onSearchListener: OnSearchListener? = null
    private var onBackClickListener: OnBackClickListener? = null
    private var onModeChangeListener: OnModeChangeListener? = null

    private var text: String? = null
    private var isOpenSearch = true
    private var isFirst = true

    private var mode = MODE_LIST

    companion object {
        val MODE_LIST = 0
        val MODE_GRID = 1
    }

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

        mBinding?.etSearch?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == IME_ACTION_SEARCH) {
                    val etText = etText()
                    var text: String?
                    if (!TextUtils.isEmpty(etText)) {
                        text = etText()
                    } else {
                        text = hintText()
                    }
                    startSearch(text)
                    onSearchListener?.onSearch(text)
                    hideKeyboard()
                }
                return false
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
                var text: String?
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
                if (mode == MODE_LIST) {
                    mode = MODE_GRID
                } else if (mode == MODE_GRID) {
                    mode = MODE_LIST
                }
                onModeChangeListener?.onModeChange(mode)
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
        mBinding?.etSearch?.clearFocus()
        mBinding?.etSearch?.visibility = View.GONE
        mBinding?.tvSearchContent?.visibility = View.VISIBLE
        mBinding?.tvSearchContent?.setText(text)
        isOpenSearch = false
    }

    fun isOpenSearch() = isOpenSearch
    fun isFirst() = isFirst

    fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(mBinding?.etSearch)
    }

    fun startSearch(text: String?) {
        this.text = text
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

    fun setOnModeChangeListener(l: OnModeChangeListener) {
        onModeChangeListener = l
    }

    interface OnSearchListener {
        fun onSearch(text: String?)
    }

    interface OnBackClickListener {
        fun onBackClick()
    }

    interface OnModeChangeListener {
        fun onModeChange(mode: Int)
    }
}