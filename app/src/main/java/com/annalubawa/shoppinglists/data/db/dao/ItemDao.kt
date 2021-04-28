package com.annalubawa.shoppinglists.data.db.dao

import androidx.room.*
import com.annalubawa.shoppinglists.data.db.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Query("SELECT * FROM items WHERE shoppingListId = :shoppingListId")
    fun getItems(shoppingListId: Int) : Flow<List<ItemEntity>>

}