package com.toyproject.plannerv2.util

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

fun DatabaseReference.getFirebaseData(
    onDataChangeLogic: (DataSnapshot) -> Unit,
    onCanceledLogic: (DatabaseError) -> Unit = {}
) {
    this.addListenerForSingleValueEvent(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            onDataChangeLogic(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            onCanceledLogic(error)
        }
    })
}