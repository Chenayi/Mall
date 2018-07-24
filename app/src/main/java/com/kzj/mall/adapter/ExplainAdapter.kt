package com.kzj.mall.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.GoodsDetailEntity

class ExplainAdapter constructor(val context: Context?, val explains: MutableList<GoodsDetailEntity.Explain>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_explain, null, false)
        view.findViewById<TextView>(R.id.tv_title)?.text = explains?.get(position)?.title
        view.findViewById<TextView>(R.id.tv_content)?.text = explains?.get(position)?.content
        view.findViewById<View>(R.id.bottom_line)?.visibility = if(position == explains?.size- 1) View.GONE else View.VISIBLE
        return view
    }

    override fun getItem(position: Int): Any {
        return explains?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return explains?.size
    }

}