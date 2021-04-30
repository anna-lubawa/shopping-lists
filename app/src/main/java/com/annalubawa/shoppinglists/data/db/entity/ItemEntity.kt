package com.annalubawa.shoppinglists.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val shoppingListId: Int,
    val name: String,
    val quantity: Int,
    var bought: Boolean
)
