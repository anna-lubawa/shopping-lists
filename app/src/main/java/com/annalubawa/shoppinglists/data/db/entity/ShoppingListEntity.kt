package com.annalubawa.shoppinglists.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shopping_lists")
data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val boughtItemsCount: Int,
    val totalItemsCount: Int,
    val archived: Boolean,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val added: Long
)
