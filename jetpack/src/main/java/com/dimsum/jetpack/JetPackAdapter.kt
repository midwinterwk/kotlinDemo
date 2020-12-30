package com.dimsum.jetpack

import android.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class JetPackAdapter(names: Array<String>) : RecyclerView.Adapter<JetPackAdapter.Holder>() {
    private var mNames: Array<String>? = null

    init {
        mNames = names
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JetPackAdapter.Holder {
        val view = View.inflate(parent.context, android.R.layout.simple_list_item_1, null)
        view.setOnClickListener(onItemClick)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.text.setText(mNames!!.get(position))
        holder.itemView.setTag(mNames!!.get(position))
    }

    override fun getItemCount(): Int {
        return mNames!!.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.text1)
    }

//    interface OnItemClick {
//        fun setOnItemClick(view: View?, tag: String)
//    }

    private var onItemClick: View.OnClickListener? = null

    fun setOnItemClick(onItemClick: View.OnClickListener?) {
        this.onItemClick = onItemClick
    }

//    override fun onClick(v: View?) {
//        if (onItemClick != null) {
//            onItemClick!!.setOnItemClick(v, (v!!.tag as String))
//        }
//    }

}
