package com.example.notepadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.notepadapp.db.MyDbManager

class MainActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickSave(view: View) {
        findViewById<TextView>(R.id.tvTest).text = ""
        myDbManager.openDb()
        myDbManager.insertToDb(findViewById<EditText>(R.id.edTitle).text.toString(), findViewById<EditText>(R.id.edContent).text.toString())
        val dataList = myDbManager.readDbData()
        for (item in dataList) {
            findViewById<TextView>(R.id.tvTest).append("$item\n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.removeAll()
        myDbManager.closeDb()
    }
}