package com.annalubawa.shoppinglists.domain.repository

import com.annalubawa.shoppinglists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow


interface ShoppingListRepository {

    suspend fun addShoppingList(shoppingList: ShoppingList)

    suspend fun deleteShoppingList(shoppingList: ShoppingList)

    suspend fun archiveShoppingList(shoppingList: ShoppingList)

    suspend fun getCurrentShoppingLists() : Flow<List<ShoppingList>>

    suspend fun getArchivedShoppingLists() : Flow<List<ShoppingList>>

}