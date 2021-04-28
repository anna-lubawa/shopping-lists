package com.annalubawa.shoppinglists.domain.repository

import com.annalubawa.shoppinglists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow


interface ShoppingListRepository {

    suspend fun addShoppingList(shoppingList: ShoppingList)

    suspend fun deleteShoppingList(shoppingList: ShoppingList)

    suspend fun archiveShoppingList(shoppingListId: Int)

    suspend fun unarchiveShoppingList(shoppingListId: Int)

    suspend fun incrementBoughtItems(shoppingListId: Int)

    suspend fun decrementBoughtItems(shoppingListId: Int)

    suspend fun incrementTotalItems(shoppingListId: Int)

    suspend fun decrementTotalItems(shoppingListId: Int)

    suspend fun getCurrentShoppingLists() : Flow<List<ShoppingList>>

    suspend fun getArchivedShoppingLists() : Flow<List<ShoppingList>>

}