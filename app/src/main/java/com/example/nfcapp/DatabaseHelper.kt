package com.example.nfcapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun saveMessage(message: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_MESSAGE, message)
        db.insert(TABLE_NAME, null, values)
//        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "nfcDatabase.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "nfcTags"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MESSAGE = "message"
        private const val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MESSAGE + " TEXT)"
    }
}
