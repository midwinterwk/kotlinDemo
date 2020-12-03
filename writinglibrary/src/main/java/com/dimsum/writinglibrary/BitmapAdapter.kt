package com.dimsum.writinglibrary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.FileInputStream


class BitmapAdapter(context: Context, list: ArrayList<String>) : RecyclerView.Adapter<BitmapAdapter.MyHolder>() {
    var list: ArrayList<String>? = null
    var inflater: LayoutInflater? = null
    var context: Context? = null

    init {
        this.list = list
        this.inflater = LayoutInflater.from(context)
        this.context = context
    }

    override fun getItemCount(): Int {
        return list?.size ?:0
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val bitmap: Bitmap? = getLoacalBitmap(list?.get(position)!!)
        if (bitmap != null) {
            holder?.imageview?.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(inflater?.inflate(R.layout.item_bitmap, parent, false), context!!)
    }


    class MyHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView!!) {
        var imageview = itemView?.findViewById<ImageView>(R.id.imageView)
    }


    fun getLoacalBitmap(path: String) :Bitmap? {
        try {
            val fis: FileInputStream = FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
            return null
        }
    }

}