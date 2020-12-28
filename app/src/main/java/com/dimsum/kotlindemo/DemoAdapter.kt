package com.dimsum.kotlindemo

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DemoAdapter(var list: ArrayList<String>) :
    RecyclerView.Adapter<DemoAdapter.ViewHolder>() {

    private var itemClickListener: IDemoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(TextView(parent?.context))


    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView?.textSize = 30f
        holder.textView?.setText(list.get(position))
        holder.itemView.setOnClickListener{
            itemClickListener!!.onItemClickListener(position)
        }
    }

    class ViewHolder(var textView: TextView): RecyclerView.ViewHolder(textView)

    // 提供set方法
    fun setOnDemoItemClickListener(itemClickListener: IDemoItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //自定义接口
    interface IDemoItemClickListener {
        fun onItemClickListener(position: Int)
    }

}
