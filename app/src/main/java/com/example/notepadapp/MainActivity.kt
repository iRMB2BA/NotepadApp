package com.example.notepadapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadapp.db.MyAdapter
import com.example.notepadapp.db.MyDbManager

class MainActivity : AppCompatActivity() {

    val myDbManager = MyDbManager(this)
    val myAdapter = MyAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickAddButton(view: View) {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("CutPasteId")
    fun init(){
        findViewById<RecyclerView>(R.id.rcView).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rcView).adapter = myAdapter
    }

    fun fillAdapter(){
        myAdapter.updateAdapter(myDbManager.readDbData())
    }


}