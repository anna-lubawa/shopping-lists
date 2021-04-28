package com.annalubawa.shoppinglists.domain.model

import java.util.*

data class ShoppingList(
    var id: Int,
    var name: String,
    var boughtItemsCount: Int,
    var totalItemsCount: Int,
    var archived: Boolean,
    var added: Long
)