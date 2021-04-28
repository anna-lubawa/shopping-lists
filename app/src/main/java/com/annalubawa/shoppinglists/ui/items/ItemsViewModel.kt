package com.annalubawa.shoppinglists.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annalubawa.shoppinglists.domain.model.Item
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.annalubawa.shoppinglists.domain.repository.ItemRepository
import com.annalubawa.shoppinglists.domain.repository.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemsRepository: ItemRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    var shoppingListId: Int? = null
    var shoppingListName: String? = null
    var archived: Boolean? = null

    private var _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    fun getItems() {
        shoppingListId?.let { id ->
            viewModelScope.launch(Dispatchers.IO) {
                itemsRepository.getItems(id).collect { items ->
                    _items.postValue(items)
                }
            }
        }
    }

    fun addItem(name: String, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.addItem(Item(0, shoppingListId!!, name, quantity, false))
            shoppingListRepository.incrementTotalItems(shoppingListId!!)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.deleteItem(item)
            shoppingListRepository.decrementTotalItems(shoppingListId!!)
        }
    }

    fun buyItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.buyItem(item)
            shoppingListRepository.incrementBoughtItems(shoppingListId!!)
        }
    }

    fun undoBuyItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.undoBuyItem(item)
            shoppingListRepository.decrementBoughtItems(shoppingListId!!)
        }
    }

    fun archiveShoppingList()
    {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.archiveShoppingList(shoppingListId!!)
        }
    }

    fun unarchiveShoppingList()
    {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.unarchiveShoppingList(shoppingListId!!)
        }
    }

}