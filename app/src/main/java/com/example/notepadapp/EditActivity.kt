package com.example.notepadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout

class EditActivity : AppCompatActivity() {
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
}