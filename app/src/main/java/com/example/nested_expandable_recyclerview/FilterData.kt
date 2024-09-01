package com.example.nested_expandable_recyclerview

/**
Created by Masum Mehedi on 8/27/2024.
masumehedissl@gmail.com
 **/
data class FilterResponse(
    val filters: Filters
)

data class Filters(
    val layout: List<String>,
    val availability: List<String>,
    val price: Price,
    val productType: List<String>,
    val brand: List<String>,
    val color: List<String>,
    val size: List<String>,
    val tags: List<String>
)

data class Price(
    val min: Int,
    val max: Int
)
