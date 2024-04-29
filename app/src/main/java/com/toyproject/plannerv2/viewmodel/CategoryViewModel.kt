package com.toyproject.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.util.createFireStoreData
import com.toyproject.plannerv2.util.readFireStoreData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel: ViewModel() {
    private val _categories = MutableStateFlow<List<CategoryData>?>(null)
    val categories = _categories.asStateFlow()

    fun createCategory(uid: String, categoryData: CategoryData) {
        val createCategoryRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("category")
            .document(categoryData.createdTime.toString())

        createCategoryRef.createFireStoreData(setValue = categoryData)
    }

    fun getCategory(uid: String, onFailure: (Exception?) -> Unit = {}) {
        val categoryRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("category")

        categoryRef.readFireStoreData(
            onSuccess = {
                val categoryList = mutableListOf<CategoryData>()
                it.forEach { documentSnapshot ->
                    val categoryData = documentSnapshot.toObject(CategoryData::class.java)
                    if (categoryData != null) categoryList.add(categoryData)
                }
                _categories.value = categoryList
            },
            onFailure = {
                onFailure(it)
            }
        )
    }
}