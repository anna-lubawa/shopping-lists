package com.annalubawa.shoppinglists.data.repository

import com.annalubawa.shoppinglists.data.db.dao.ShoppingListDao
import com.annalubawa.shoppinglists.data.mapper.ShoppingListMapper
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.annalubawa.shoppinglists.domain.repository.ShoppingListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val mapper: ShoppingListMapper
) : ShoppingListRepository {

    override suspend fun addShoppingList(shoppingList: ShoppingList) {
        shoppingListDao.addShoppingList(mapper.toShoppingListEntity(shoppingList))
    }

    override suspend fun deleteShoppingList(shoppingList: ShoppingList) {
        shoppingListDao.deleteShoppingList(mapper.toShoppingListEntity(shoppingList))
    }

    override suspend fun archiveShoppingList(shoppingList: ShoppingList) {
        shoppingList.archived = true
        shoppingListDao.updateShoppingList(mapper.toShoppingListEntity(shoppingList))
    }

    override suspend fun getCurrentShoppingLists(): Flow<List<ShoppingList>> {
        return shoppingListDao.getCurrentShoppingLists().map {  mapper.toShoppingLists(it) }
            .catch { emit(emptyList()) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getArchivedShoppingLists(): Flow<List<ShoppingList>> {
        return shoppingListDao.getArchivedShoppingLists().map {  mapper.toShoppingLists(it) }
            .catch { emit(emptyList()) }
            .flowOn(Dispatchers.IO)
    }
}