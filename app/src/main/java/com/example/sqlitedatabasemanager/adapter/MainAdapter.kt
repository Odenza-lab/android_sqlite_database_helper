package com.example.sqlitedatabasemanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedatabasemanager.R
import com.example.sqlitedatabasemanager.models.MainDataModel

class MainAdapter(
    list: List<MainDataModel>,
    private val context: Context
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var list: List<MainDataModel> = arrayListOf()

    init {
        this.list = list
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val kanji: TextView = itemView.findViewById(R.id.kanji_textview)
        private val english: TextView = itemView.findViewById(R.id.english_textview)

        fun bind(position: Int) {
            val item = list[position]
            kanji.text = item.kanji
            english.text = item.english
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_raw, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}