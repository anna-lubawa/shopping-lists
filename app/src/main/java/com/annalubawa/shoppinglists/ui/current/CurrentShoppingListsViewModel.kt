package com.annalubawa.shoppinglists.ui.current

import androidx.lifecycle.*
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.annalubawa.shoppinglists.domain.repository.ItemRepository
import com.annalubawa.shoppinglists.domain.repository.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentShoppingListsViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {

    val currentShoppingLists: LiveData<List<ShoppingList>> = liveData {
        emitSource(shoppingListRepository.getCurrentShoppingLists().asLiveData())
    }

    fun addShoppingList(name: String)
    {
        val shoppingList = ShoppingList(0, name, 0, 0,
            false, System.currentTimeMillis())

        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.addShoppingList(shoppingList)
        }
    }

    fun deleteShoppingList(shoppingList: ShoppingList)
    {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.deleteShoppingList(shoppingList)
            itemRepository.deleteItems(shoppingList.id)
        }
    }

}