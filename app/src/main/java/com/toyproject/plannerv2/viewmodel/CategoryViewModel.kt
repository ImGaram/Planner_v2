package com.toyproject.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.util.createFireStoreData
import com.toyproject.plannerv2.util.deleteFireStoreData
import com.toyproject.plannerv2.util.readFireStoreData
import com.toyproject.plannerv2.util.updateFieldFireStoreData
import com.toyproject.plannerv2.util.updateFireStoreData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel: ViewModel() {
    private val _categories = MutableStateFlow<List<CategoryData>>(emptyList())
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

    fun modifyCategory(
        uid: String,
        modifyValue: CategoryData,
        onSuccess: () -> Unit = {},
        onFailure: (Exception?) -> Unit = {}
    ) {
        val modifyRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("category")
            .document(modifyValue.createdTime.toString())

        modifyRef.updateFireStoreData(
            updateValue = mapOf(
                "categoryTitle" to modifyValue.categoryTitle,
                "categoryColorHex" to modifyValue.categoryColorHex
            ),
            onSuccess = onSuccess,
            onFailure = onFailure
        )

        val updatePlanCategoryRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        updatePlanCategoryRef.orderBy("categories.${modifyValue.id}").readFireStoreData(
            onSuccess = {
                it.forEach { documentSnapshot ->
                    documentSnapshot.reference.updateFieldFireStoreData(
                        updateField = "categories.${modifyValue.id}",
                        updateValue = mapOf(
                            "title" to modifyValue.categoryTitle,
                            "color" to modifyValue.categoryColorHex
                        )
                    )
                }
            }
        )
    }

    /**
     * [deleteId] : category data에서 삭제될 id(createdTime)
     * [deletePlanTargetId] : plan data에서 삭제될 category의 id
     */
    fun deleteCategory(
        uid: String,
        deleteId: String,
        deletePlanTargetId: String,
        onSuccess: () -> Unit = {},
        onFailure: (Exception?) -> Unit = {}
    ) {
        val deleteRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("category")
            .document(deleteId)

        deleteRef.deleteFireStoreData(
            onSuccess = onSuccess,
            onFailure = onFailure
        )

        // 삭제된 카테고리를 plan data에도 삭제시켜야 함.
        val deletePlanCategoryRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        deletePlanCategoryRef.orderBy("categories.$deletePlanTargetId").readFireStoreData(
            onSuccess = {
                it.forEach { documentSnapshot ->
                    documentSnapshot.reference.updateFieldFireStoreData(
                        updateField = "categories.${deletePlanTargetId}",
                        updateValue = FieldValue.delete()
                    )
                }
            }
        )
    }

    /**
     * plan data의 카테고리 설정에서 카테고리를 변경했을 때 호출
     */
    fun updateCategory(
        uid: String,
        targetPlanDocId: String,
        categoryValue: Map<String, Map<String, Any>>,
        onUpdateSuccess: () -> Unit
    ) {
        val updateCategoryRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")
            .document(targetPlanDocId)

        updateCategoryRef.updateFieldFireStoreData(
            updateField = "categories",
            updateValue = categoryValue,
            onSuccess = onUpdateSuccess
        )
    }
}