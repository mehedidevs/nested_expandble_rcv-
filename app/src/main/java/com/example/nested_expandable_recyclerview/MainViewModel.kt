package com.example.nested_expandable_recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
Created by Masum Mehedi on 8/24/2024.
masumehedissl@gmail.com
 **/
class MainViewModel : ViewModel() {

    private val _parentItems = MutableLiveData<List<ParentItem>>()
    val parentItems: LiveData<List<ParentItem>> = _parentItems

    fun fetchParentItems() {
        viewModelScope.launch(Dispatchers.IO) {
            // Simulate data fetching (from database, network, etc.)
            val data = fetchDataFromDatabaseOrNetwork()
            withContext(Dispatchers.Main) {
                _parentItems.value = data
            }
        }
    }

    private suspend fun fetchDataFromDatabaseOrNetwork(): List<ParentItem> {
        // Simulate a background operation
        delay(1000) // Example delay
        return listOf(
            ParentItem(
                "Category 1",
                listOf(
                    ChildItem(
                        "Subcategory 1",
                        listOf(SubChildItem("Item 1.1"), SubChildItem("Item 1.2"))
                    ),
                    ChildItem(
                        "Subcategory 2",
                        listOf(SubChildItem("Item 2.1"), SubChildItem("Item 2.2"))
                    )
                )
            ),
            ParentItem(
                "Category 2",
                listOf(
                    ChildItem(
                        "Subcategory 3",
                        listOf(SubChildItem("Item 3.1"), SubChildItem("Item 3.2"))
                    ),
                    ChildItem(
                        "Subcategory 4",
                        listOf(SubChildItem("Item 4.1"), SubChildItem("Item 4.2"))
                    )
                )
            )
        )
    }
}
