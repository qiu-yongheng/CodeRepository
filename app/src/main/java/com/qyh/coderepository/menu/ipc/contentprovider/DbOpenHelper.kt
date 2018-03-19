package com.qyh.coderepository.menu.ipc.contentprovider

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author 邱永恒
 *
 * @time 2018/3/18  21:24
 *
 * @desc ${TODO}
 *
 */

class DbOpenHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "book_provider.db"
        public val BOOK_TABLE_NAME = "book"
        public val USER_TABLE_NAME = "user"
        private val DB_VERSION = 1
        private val CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS $BOOK_TABLE_NAME(_id INTEGER PRIMARY KEY,name TEXT)"
        private val CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS $USER_TABLE_NAME(_id INTEGER PRIMARY KEY,name TEXT,sex INT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_BOOK_TABLE)
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}