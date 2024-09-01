package com.example.nested_expandable_recyclerview

/**
Created by Masum Mehedi on 8/21/2024.
masumehedissl@gmail.com
 **/
data class ParentItem(
    val title: String,
    val childItems: List<ChildItem>,
    var isExpanded: Boolean = false
)

data class ChildItem(
    val name: String,
    val subChildItems: List<SubChildItem>,
    var isExpanded: Boolean = false
)

data class SubChildItem(
    val name: String
)
