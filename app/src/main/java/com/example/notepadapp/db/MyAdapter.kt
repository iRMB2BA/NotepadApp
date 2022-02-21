package com.example.notepadapp.db

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadapp.R

class MyAdapter(listMain: ArrayList<String>): RecyclerView.Adapter<MyAdapter.MyHolder>() {

    var listArray = listMain

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        fun setData(title:String) {
            tvTitle.text = title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray[position])
    }

    override fun getItemCount(): Int {
        return listArray.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(listItems:ArrayList<String>) {
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }
}