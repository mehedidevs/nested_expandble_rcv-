package com.example.nested_expandable_recyclerview

/**
Created by Masum Mehedi on 8/21/2024.
masumehedissl@gmail.com
 **/
interface OnParentItemClickListener {
    fun onParentItemClick(parentItem: ParentItem)
}

interface OnChildItemClickListener {
    fun onChildItemClick(childItem: ChildItem)
}

interface OnSubChildItemClickListener {
    fun onSubChildItemClick(subChildItem: SubChildItem)
}
