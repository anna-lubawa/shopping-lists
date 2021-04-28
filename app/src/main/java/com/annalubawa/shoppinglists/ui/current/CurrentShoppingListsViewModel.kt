package com.annalubawa.shoppinglists.ui.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.annalubawa.shoppinglists.domain.repository.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentShoppingListsViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel() {

    private var _currentShoppingLists = MutableLiveData<List<ShoppingList>>()
    val currentShoppingLists: LiveData<List<ShoppingList>> = _currentShoppingLists

    fun getCurrentShoppingLists()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentShoppingLists().collect { shoppingLists ->
                _currentShoppingLists.postValue(shoppingLists)
            }
        }
    }

    fun addShoppingList(name: String)
    {
        val shoppingList = ShoppingList(0, name, 0, 0,
            false, System.currentTimeMillis())

        viewModelScope.launch(Dispatchers.IO) {
            repository.addShoppingList(shoppingList)
        }
    }

    fun deleteShoppingList(shoppingList: ShoppingList)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteShoppingList(shoppingList)
        }
    }

    fun archiveShoppingList(shoppingList: ShoppingList)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.archiveShoppingList(shoppingList)
        }
    }
}