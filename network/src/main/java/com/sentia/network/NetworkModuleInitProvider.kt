package com.sentia.network

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri

class NetworkModuleInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        Network.init(context)
        // initialize whatever you need
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?) = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?) = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) = 0
}