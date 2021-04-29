package com.annalubawa.shoppinglists.ui.archived

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.annalubawa.shoppinglists.domain.repository.ItemRepository
import com.annalubawa.shoppinglists.domain.repository.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArchivedShoppingListsViewModel @Inject constructor(
        private val shoppingListRepository: ShoppingListRepository,
        private val itemRepository: ItemRepository
) : ViewModel() {

    private var _archivedShoppingLists = MutableLiveData<List<ShoppingList>>()
    val archivedShoppingLists: LiveData<List<ShoppingList>> = _archivedShoppingLists

    fun getArchivedShoppingLists()
    {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.getArchivedShoppingLists().collect { shoppingLists ->
                _archivedShoppingLists.postValue(shoppingLists)
            }
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