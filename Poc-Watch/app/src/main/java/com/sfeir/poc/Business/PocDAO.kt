package com.sfeir.poc.Business

import android.content.Context
import android.provider.Settings
import com.couchbase.lite.Database
import com.couchbase.lite.MutableDocument
import com.sfeir.poc.PocApplication

class PocDAO(context: Context) {
    companion object {
        private val SHARED_PREF_ID_KEY = "POC_SHARED_PREF_ID_KEY"
    }

    lateinit var db :Database
    lateinit private var _context: Context

    init {
        _context = context
        PocApplication.database?.let { database ->
            this.db = database
        }
    }

    private var _document : MutableDocument? = null

    val document : MutableDocument?
    get() {
        if (_document == null) {
            val id = Settings.Secure.getString(_context.contentResolver, Settings.Secure.ANDROID_ID)
            _document = this.db.getDocument(id)?.toMutable()
            if (_document == null) {
                _document  = MutableDocument(id)
                        .setFloat("version", 0.1f)
                        .setString("deviceId", id)

                this.db.save(_document)

            }
        }
        return _document
    }

}