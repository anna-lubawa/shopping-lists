package com.annalubawa.shoppinglists.domain.model

data class Item(
    var id: Int,
    var shoppingListId: Int,
    var name: String,
    var quantity: Int,
    var bought: Boolean
)