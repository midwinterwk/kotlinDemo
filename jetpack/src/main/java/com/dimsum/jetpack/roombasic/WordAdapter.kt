package com.dimsum.jetpack.roombasic

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimsum.jetpack.R

class WordAdapter(useCardView: Boolean): RecyclerView.Adapter<WordAdapter.WordViewHolder>() {
    private var allWords: List<Word> = ArrayList()
    private var useCardView: Boolean = false

    init {
        this.useCardView = useCardView
    }

    fun setAllWords(allWords: List<Word>) {
        this.allWords = allWords
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvEnglish: TextView
        var tvNum: TextView
        var tvChinese: TextView
        init {
            tvNum = itemView.findViewById(R.id.tv_num)
            tvEnglish = itemView.findViewById<TextView>(R.id.tv_english)
            tvChinese = itemView.findViewById<TextView>(R.id.tv_chinese)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val cell = if (useCardView) { R.layout.cell_card } else { R.layout.cell_normal}
        LayoutInflater.from(parent.context).inflate(cell, parent, false).let {
            return WordViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return allWords.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        allWords.get(position).let {
            holder.tvNum.text = (position + 1).toString()
            holder.tvEnglish.text = it.word
            holder.tvChinese.text = it.chineseMeaning
        }
        holder.itemView.setOnClickListener { v ->
            val uri: Uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=${holder.tvEnglish.text}")
            Intent(Intent.ACTION_VIEW).apply {
                setData(uri)
                holder.itemView.context.startActivity(this)
            }
        }
    }
}