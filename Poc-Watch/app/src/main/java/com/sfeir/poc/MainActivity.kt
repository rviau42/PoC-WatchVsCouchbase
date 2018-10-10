package com.sfeir.poc

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.couchbase.lite.internal.support.Log
import com.sfeir.poc.Business.PocDAO

class MainActivity : WearableActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Enables Always-on
        setAmbientEnabled()

        val document = PocDAO(applicationContext).document
        document?.let { document ->
            PocApplication.database?.addDocumentChangeListener(document.id) { change ->
                // TODO: All changes in the document will be catch here ! Do the job now
                Log.i(TAG, change.documentID)
            }
        }

    }

}
