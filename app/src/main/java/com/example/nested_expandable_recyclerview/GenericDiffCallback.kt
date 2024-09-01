package com.example.nested_expandable_recyclerview

import androidx.recyclerview.widget.DiffUtil

/**
Created by Masum Mehedi on 8/21/2024.
masumehedissl@gmail.com
 **/
class GenericDiffCallback<T>(
    private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
      override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return areContentsTheSame(oldItem, newItem)
    }

}
