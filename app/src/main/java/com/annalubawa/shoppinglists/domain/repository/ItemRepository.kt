package com.annalubawa.shoppinglists.domain.repository

import com.annalubawa.shoppinglists.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun addItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun buyItem(item: Item)

    suspend fun undoBuyItem(item: Item)

    suspend fun deleteItems(shoppingListId: Int)

    fun getItems(shoppingListId: Int) : Flow<List<Item>>

}