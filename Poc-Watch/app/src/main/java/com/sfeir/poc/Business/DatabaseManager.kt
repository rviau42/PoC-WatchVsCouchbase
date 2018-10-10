package com.sfeir.poc.Business

import android.content.Context
import android.util.Log
import com.couchbase.lite.*
import java.net.URI


class DatabaseManager {
    companion object {
        private val TAG  = DatabaseManager::class.java.name

        fun getDatabase(context: Context) : Database {
            // Get the database (and create it if it doesn’t exist).
            val config = DatabaseConfiguration(context)
            val database = Database("localdb", config)
            Database.setLogLevel(LogDomain.REPLICATOR, LogLevel.VERBOSE)
            Database.setLogLevel(LogDomain.QUERY, LogLevel.VERBOSE)


            // Create replicators to push and pull changes to and from the cloud.
            val targetEndpoint = URLEndpoint(URI("ws://10.0.2.2:4984/syncwatch"))
            val replConfig = ReplicatorConfiguration(database, targetEndpoint)
            replConfig.replicatorType = ReplicatorConfiguration.ReplicatorType.PUSH_AND_PULL
            replConfig.isContinuous = true

            // Create replicator.
            val replicator = Replicator(replConfig)

            // Listen to replicator change events.
            replicator.addChangeListener { change ->
                if (change.status.error != null)
                    Log.i(TAG, "Error code ::  " + change.status.error.code)
            }

            // Start replication.
            replicator.start()
            return database
        }
    }
}