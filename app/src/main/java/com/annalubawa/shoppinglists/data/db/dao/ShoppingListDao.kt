package com.annalubawa.shoppinglists.data.db.dao

import androidx.room.*
import com.annalubawa.shoppinglists.data.db.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addShoppingList(shoppingList: ShoppingListEntity)

    @Delete
    suspend fun deleteShoppingList(shoppingList: ShoppingListEntity)

    @Query("UPDATE shopping_lists SET archived = 1 WHERE id = :id")
    suspend fun archiveShoppingList(id: Int)

    @Query("UPDATE shopping_lists SET archived = 0 WHERE id = :id")
    suspend fun unarchiveShoppingList(id: Int)

    @Query("UPDATE shopping_lists SET boughtItemsCount = boughtItemsCount + 1 WHERE id = :id")
    suspend fun incrementBoughtItems(id: Int)

    @Query("UPDATE shopping_lists SET boughtItemsCount = boughtItemsCount - 1 WHERE id = :id")
    suspend fun decrementBoughtItems(id: Int)

    @Query("UPDATE shopping_lists SET totalItemsCount = totalItemsCount + 1 WHERE id = :id")
    suspend fun incrementTotalItems(id: Int)

    @Query("UPDATE shopping_lists SET totalItemsCount = totalItemsCount - 1 WHERE id = :id")
    suspend fun decrementTotalItems(id: Int)

    @Query("SELECT * FROM shopping_lists WHERE archived = 0 ORDER BY added ASC")
    fun getCurrentShoppingLists() : Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM shopping_lists WHERE archived = 1 ORDER BY added ASC")
    fun getArchivedShoppingLists() : Flow<List<ShoppingListEntity>>

}