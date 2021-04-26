package com.annalubawa.shoppinglists.domain.model

data class ShoppingList(
    var name: String,
    val boughtItemsCount: Int,
    val totalItemsCount: Int,
    val items: List<Item>,
    val archived: Boolean
)