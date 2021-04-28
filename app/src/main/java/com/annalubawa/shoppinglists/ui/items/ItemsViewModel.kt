package com.annalubawa.shoppinglists.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annalubawa.shoppinglists.domain.model.Item
import com.annalubawa.shoppinglists.domain.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    var shoppingListId: Int? = null
    var archived: Boolean? = null

    private var _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    fun getItems() {
        shoppingListId?.let { id ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.getItems(id).collect { items ->
                    _items.postValue(items)
                }
            }
        }
    }

    fun addItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item)
        }
    }

    fun buyItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.buyItem(item)
        }
    }

}