package com.example.notepadapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.notepadapp.db.MyDbManager

class EditActivity : AppCompatActivity() {

    val imageRequestCode = 10
    var tempImageURL = "empty"
    val myDbManager = MyDbManager(this)




    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    fun onClickAddImage(view: View) {
        findViewById<ConstraintLayout>(R.id.mainImageLayout).visibility = View.VISIBLE
        findViewById<ImageButton>(R.id.fbImageSave).visibility = View.GONE
    }

    fun onClickDelitImage(view: View) {
        findViewById<ConstraintLayout>(R.id.mainImageLayout).visibility = View.GONE
        findViewById<ImageButton>(R.id.fbImageSave).visibility = View.VISIBLE
    }

    fun onClickChooseImage(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode) {
            findViewById<ImageView>(R.id.imageView).setImageURI(data?.data)
            tempImageURL = data?.data.toString()
        }
    }

    fun onClickSave(view: View) {
        val myTitle = findViewById<EditText>(R.id.edTitle).text.toString()
        val myContext = findViewById<EditText>(R.id.edContext).text.toString()

        if (myTitle != "" && myContext != "") {
            myDbManager.insertToDb(myTitle, myContext, tempImageURL)
        }
    }
}