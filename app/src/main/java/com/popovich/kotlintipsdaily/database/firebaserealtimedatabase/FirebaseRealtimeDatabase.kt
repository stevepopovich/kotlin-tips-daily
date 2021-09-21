package com.popovich.kotlintipsdaily.database.firebaserealtimedatabase

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.popovich.kotlintipsdaily.database.TipSyncService

class FirebaseRealtimeDatabase {
    companion object {
        private val database = Firebase.database
        private val tipsDatabase: DatabaseReference

        init {
            database.setPersistenceEnabled(true)
            tipsDatabase = database.getReference("tips")
            tipsDatabase.keepSynced(true)
        }

        fun setupTipsListener(context: Context) {
            val tipsListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    TipSyncService.syncTips(
                        dataSnapshot.value as Map<String, HashMap<String, String>>,
                        context
                    )
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // TODO
                }
            }

            tipsDatabase.addValueEventListener(tipsListener)
        }
    }
}
