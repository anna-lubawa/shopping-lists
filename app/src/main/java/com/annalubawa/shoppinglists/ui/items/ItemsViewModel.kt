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

    var shouldShowToast = false
    var shoppingListId: Int? = null
    var shoppingListName: String? = null

    private var _archived = MutableLiveData<Boolean>()
    val archived: LiveData<Boolean> = _archived

    private var _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    fun initValues(shoppingListId: Int, shoppingListName: String, archived: Boolean)
    {
        this.shoppingListId = shoppingListId
        this.shoppingListName = shoppingListName

        if(_archived.value == null)
            _archived.value = archived
    }

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
            shoppingListRepository.decrementBoughtItems(shoppingListId!!)
        }
    }

    fun buyItem(item: Item) {
        if(!archived.value!!) {
            viewModelScope.launch(Dispatchers.IO) {
                itemsRepository.buyItem(item)
                shoppingListRepository.incrementBoughtItems(shoppingListId!!)
            }
        }
    }

    fun undoBuyItem(item: Item) {
        if(!archived.value!!) {
            viewModelScope.launch(Dispatchers.IO) {
                itemsRepository.undoBuyItem(item)
                shoppingListRepository.decrementBoughtItems(shoppingListId!!)
            }
        }
    }

    fun archiveOrUnarchiveShoppingList()
    {
        shouldShowToast = true
        if(archived.value!!)
            unarchiveShoppingList()
        else
            archiveShoppingList()
    }

    private fun archiveShoppingList()
    {
        _archived.value = true
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.archiveShoppingList(shoppingListId!!)
        }
    }

    private fun unarchiveShoppingList()
    {
        _archived.value = false
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.unarchiveShoppingList(shoppingListId!!)
        }
    }

}