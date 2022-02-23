package com.example.notepadapp.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadapp.EditActivity
import com.example.notepadapp.R

class MyAdapter(listMain: ArrayList<ListItem>, context: Context) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private var listArray = listMain
    private var cont = context

    class MyHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById<TextView>(R.id.tv_title)
        private val contextM = contextV

        fun setData(item: ListItem) {
            tvTitle.text = item.title
            itemView.setOnClickListener {
                val intent = Intent(contextM, EditActivity::class.java).apply {
                    putExtra(MyIntentConstant.I_TITLE_KEY, item.title)
                    putExtra(MyIntentConstant.I_DISC_KEY, item.desc)
                    putExtra(MyIntentConstant.I_URL_KEY, item.url)
                    putExtra(MyIntentConstant.I_ID_KEY, item.id)
                }
                contextM.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item, parent, false), cont)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray[position])
    }

    override fun getItemCount(): Int {
        return listArray.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(listItems: ArrayList<ListItem>) {
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int, dbManager: MyDbManager) {
        dbManager.removeItemFromDb(listArray[position].id.toString())
        listArray.removeAt(position)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(position)
    }
}