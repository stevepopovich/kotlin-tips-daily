package com.popovich.kotlintipsdaily.firebaserealtimedatabase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRealtimeDatabase {
    companion object {
        private val database = Firebase.database
        private val tipsDatabase: DatabaseReference

        init {
            database.setPersistenceEnabled(true)
            tipsDatabase = database.getReference("tips")
            tipsDatabase.keepSynced(true)
        }

        fun getTips() {
            val tipsListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val thing = dataSnapshot.value as Map<String, TipFirebaseModel>
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }
            tipsDatabase.addValueEventListener(tipsListener)
        }
    }
}
