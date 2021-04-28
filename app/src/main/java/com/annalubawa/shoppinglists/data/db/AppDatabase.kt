package com.annalubawa.shoppinglists.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.annalubawa.shoppinglists.data.db.dao.ItemDao
import com.annalubawa.shoppinglists.data.db.dao.ShoppingListDao
import com.annalubawa.shoppinglists.data.db.entity.ItemEntity
import com.annalubawa.shoppinglists.data.db.entity.ShoppingListEntity

@Database(entities = [ShoppingListEntity::class, ItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun itemDao(): ItemDao
}