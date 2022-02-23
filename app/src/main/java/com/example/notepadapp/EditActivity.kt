package com.example.notepadapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notepadapp.databinding.ActivityEditBinding
import com.example.notepadapp.db.MyDbManager
import com.example.notepadapp.db.MyIntentConstant

class EditActivity : AppCompatActivity() {

    private val imageRequestCode = 10
    private var tempImageURL = "empty"
    private val myDbManager = MyDbManager(this)
    private lateinit var binding: ActivityEditBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getMyIntents()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickAddImage(view: View) {
        binding.mainImageLayout.visibility = View.VISIBLE
        binding.fbImageSave.visibility = View.GONE
    }

    fun onClickDelitImage(view: View) {
        binding.mainImageLayout.visibility = View.GONE
        binding.fbImageSave.visibility = View.VISIBLE
    }

    fun onClickChooseImage(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode) {
            binding.imageView.setImageURI(data?.data)
            tempImageURL = data?.data.toString()
            contentResolver.takePersistableUriPermission(
                data?.data!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }

    fun onClickSave(view: View) {
        val myTitle = binding.edTitle.text.toString()
        val myContext = binding.edContext.text.toString()

        if (myTitle != "" && myContext != "") {
            myDbManager.insertToDb(myTitle, myContext, tempImageURL)
            finish()
        }
    }

    private fun getMyIntents() {
        val i = intent

        if (i != null) {
            if (i.getStringExtra(MyIntentConstant.I_TITLE_KEY) != null) {
                binding.fbImageSave.visibility = View.GONE
                binding.edTitle.setText(i.getStringExtra(MyIntentConstant.I_TITLE_KEY))
                binding.edContext.setText(i.getStringExtra(MyIntentConstant.I_DISC_KEY))
                if (i.getStringExtra(MyIntentConstant.I_URL_KEY) != "empty") {
                    binding.mainImageLayout.visibility = View.VISIBLE
                    binding.imageView.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstant.I_URL_KEY)))
                    binding.ibDelitButton.visibility = View.GONE
                    binding.ibEditButton.visibility = View.GONE
                }
            }
        }
    }
}