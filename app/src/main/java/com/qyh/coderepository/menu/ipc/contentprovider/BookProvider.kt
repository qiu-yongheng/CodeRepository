package com.qyh.coderepository.menu.ipc.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  19:15
 *
 * @desc ${TODO}
 *
 */

class BookProvider : ContentProvider(){
    companion object {
        val TAG = "BookProvider"
    }
    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        Log.d(TAG, "insert")
        return null
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        Log.d(TAG, "query, current thread: ${Thread.currentThread().name}")
        return null
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate")
        return false
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "update")
        return 0
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "delete")
        return 0
    }

    override fun getType(uri: Uri?): String? {
        Log.d(TAG, "getType")
        return null
    }
}