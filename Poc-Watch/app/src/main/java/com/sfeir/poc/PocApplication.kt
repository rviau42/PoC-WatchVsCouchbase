package com.sfeir.poc

import android.app.Application
import com.couchbase.lite.Database
import com.sfeir.poc.Business.DatabaseManager

class PocApplication : Application() {
    companion object {
        var database : Database? = null
        private set
    }

    override fun onCreate() {
        super.onCreate()
        database = DatabaseManager.getDatabase(applicationContext)
    }
}