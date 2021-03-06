package com.example.notepadapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title: String, content: String, url: String) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_IMAGE_URL, url)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun updateItem(title: String, content: String, url: String, id: Int) {
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_IMAGE_URL, url)
        }
        db?.update(MyDbNameClass.TABLE_NAME, values, selection, null)
    }

    fun removeItemFromDb(id: String) {
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }

    fun readDbData(searchText: String): ArrayList<ListItem> {
        val dataList = ArrayList<ListItem>()
        val selection = "${MyDbNameClass.COLUMN_NAME_TITLE} like ?"
        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME,
            null,
            selection,
            arrayOf("%$searchText%"),
            null,
            null,
            null
        )

        while (cursor?.moveToNext()!!) {
            val dataTitle =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_TITLE))
            val dataContent =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_CONTENT))
            val dataUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_IMAGE_URL))
            val dataId =
                cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val item = ListItem()
            item.title = dataTitle
            item.desc = dataContent
            item.url = dataUrl
            item.id = dataId
            dataList.add(item)
        }
        cursor.close()
        return dataList
    }

    fun closeDb() {
        myDbHelper.close()
    }

}