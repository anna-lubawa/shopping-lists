package com.annalubawa.shoppinglists.ui.archived

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
class ArchivedShoppingListsViewModel @Inject constructor(
        private val shoppingListRepository: ShoppingListRepository,
        private val itemRepository: ItemRepository
) : ViewModel() {

    val archivedShoppingLists: LiveData<List<ShoppingList>> = liveData {
        emitSource(shoppingListRepository.getArchivedShoppingLists().asLiveData())
    }

    fun deleteShoppingList(shoppingList: ShoppingList)
    {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.deleteShoppingList(shoppingList)
            itemRepository.deleteItems(shoppingList.id)
        }
    }

}