package com.annalubawa.shoppinglists.data.repository

import com.annalubawa.shoppinglists.data.db.dao.ItemDao
import com.annalubawa.shoppinglists.data.mapper.ItemMapper
import com.annalubawa.shoppinglists.domain.model.Item
import com.annalubawa.shoppinglists.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val mapper: ItemMapper
) : ItemRepository {

    override suspend fun addItem(item: Item) {
        itemDao.addItem(mapper.toItemEntity(item))
    }

    override suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(mapper.toItemEntity(item))
    }

    override suspend fun buyItem(item: Item) {
        item.bought = true
        itemDao.updateItem(mapper.toItemEntity(item))
    }

    override fun getItems(shoppingListId: Int): Flow<List<Item>> {
        return itemDao.getItems(shoppingListId).map {  mapper.toItems(it) }
            .catch { emit(emptyList()) }
            .flowOn(Dispatchers.IO)
    }

}
