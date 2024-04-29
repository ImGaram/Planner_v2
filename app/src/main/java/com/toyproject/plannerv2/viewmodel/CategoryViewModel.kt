package com.toyproject.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.util.createFireStoreData

class CategoryViewModel: ViewModel() {

    fun createCategory(uid: String, categoryData: CategoryData) {
        val createCategoryRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("category")
            .document(categoryData.createdTime.toString())

        createCategoryRef.createFireStoreData(setValue = categoryData)
    }
}