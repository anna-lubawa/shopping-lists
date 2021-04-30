package com.annalubawa.shoppinglists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.annalubawa.shoppinglists.data.db.entity.ItemEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ItemDaoTest : DatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun addItemTest() {
        val item = ItemEntity(0, 1, "name", 2, false)

        mainCoroutineRule.runBlocking {
            appDatabase.itemDao().addItem(item)
            val list = appDatabase.itemDao().getItems(1).take(1).toList()[0]
            val actual = list[0]

            assertEquals(actual.shoppingListId, item.shoppingListId)
            assertEquals(actual.name, item.name)
            assertEquals(actual.quantity, item.quantity)
            assertEquals(actual.bought, item.bought)
        }
    }

    @Test
    fun deleteItemTest() {
        val item = ItemEntity(0, 1, "name", 2, false)

        mainCoroutineRule.runBlocking {
            appDatabase.itemDao().addItem(item)
            val addedItem = appDatabase.itemDao().getItems(1).take(1).toList()[0][0]
            appDatabase.itemDao().deleteItem(addedItem)
            val list = appDatabase.itemDao().getItems(1).take(1).toList()[0]

            assertEquals(list.size, 0)
        }
    }

    @Test
    fun updateItemTest() {
        val item = ItemEntity(0, 1, "name", 2, false)

        mainCoroutineRule.runBlocking {
            appDatabase.itemDao().addItem(item)
            var addedItem = appDatabase.itemDao().getItems(1).take(1).toList()[0][0]
            addedItem.bought = true
            appDatabase.itemDao().updateItem(addedItem)
            val updatedItem = appDatabase.itemDao().getItems(1).take(1).toList()[0][0]

            assertEquals(updatedItem.shoppingListId, addedItem.shoppingListId)
            assertEquals(updatedItem.name, addedItem.name)
            assertEquals(updatedItem.quantity, addedItem.quantity)
            assertEquals(updatedItem.bought, true)
        }
    }

    @Test
    fun deleteItemsTest() {
        val items = listOf(
                ItemEntity(0, 1, "name 1", 2, false),
                ItemEntity(0, 2, "name 2", 3, false)
        )

        mainCoroutineRule.runBlocking {
            appDatabase.itemDao().addItem(items[0])
            appDatabase.itemDao().addItem(items[1])
            appDatabase.itemDao().deleteItems(2)

            val itemsAfterDelete = appDatabase.itemDao().getItems(1).take(1).toList()[0]

            assertEquals(itemsAfterDelete.size, 1)

            val item = itemsAfterDelete[0]

            assertEquals(item.shoppingListId, items[0].shoppingListId)
            assertEquals(item.name, items[0].name)
            assertEquals(item.quantity, items[0].quantity)
            assertEquals(item.bought, items[0].bought)
        }
    }
}