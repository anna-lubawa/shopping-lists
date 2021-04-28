package com.annalubawa.shoppinglists.data.di

import android.content.Context
import androidx.room.Room
import com.annalubawa.shoppinglists.data.db.AppDatabase
import com.annalubawa.shoppinglists.data.db.dao.ItemDao
import com.annalubawa.shoppinglists.data.db.dao.ShoppingListDao
import com.annalubawa.shoppinglists.data.mapper.ItemMapper
import com.annalubawa.shoppinglists.data.mapper.ShoppingListMapper
import com.annalubawa.shoppinglists.data.repository.ItemRepositoryImpl
import com.annalubawa.shoppinglists.data.repository.ShoppingListRepositoryImpl
import com.annalubawa.shoppinglists.domain.repository.ItemRepository
import com.annalubawa.shoppinglists.domain.repository.ShoppingListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    fun provideShoppingListDao(appDatabase: AppDatabase): ShoppingListDao {
        return appDatabase.shoppingListDao()
    }

    @Provides
    fun provideItemDao(appDatabase: AppDatabase): ItemDao {
        return appDatabase.itemDao()
    }

    @Singleton
    @Provides
    fun provideShoppingListRepository(shoppingListDao: ShoppingListDao, mapper: ShoppingListMapper) : ShoppingListRepository =
        ShoppingListRepositoryImpl(shoppingListDao, mapper)

    @Singleton
    @Provides
    fun provideItemsRepository(itemDao: ItemDao, mapper: ItemMapper) : ItemRepository =
        ItemRepositoryImpl(itemDao, mapper)
}