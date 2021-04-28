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

    @Update
    suspend fun updateShoppingList(shoppingList: ShoppingListEntity)

    @Query("SELECT * FROM shopping_lists WHERE archived = 0 ORDER BY added ASC")
    fun getCurrentShoppingLists() : Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM shopping_lists WHERE archived = 1 ORDER BY added ASC")
    fun getArchivedShoppingLists() : Flow<List<ShoppingListEntity>>

}