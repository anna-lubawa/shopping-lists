package com.annalubawa.shoppinglists.data.mapper

import com.annalubawa.shoppinglists.data.db.entity.ShoppingListEntity
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import javax.inject.Inject

class ShoppingListMapper @Inject constructor() {

    fun toShoppingListEntity(shoppingList: ShoppingList) = ShoppingListEntity(
            shoppingList.id,
            shoppingList.name,
            shoppingList.boughtItemsCount,
            shoppingList.totalItemsCount,
            shoppingList.archived,
            shoppingList.added
    )

    fun toShoppingList(shoppingListEntity: ShoppingListEntity) = ShoppingList(
        shoppingListEntity.id,
        shoppingListEntity.name,
        shoppingListEntity.boughtItemsCount,
        shoppingListEntity.totalItemsCount,
        shoppingListEntity.archived,
        shoppingListEntity.added
    )

    fun toShoppingListEntities(shoppingLists: List<ShoppingList>) : List<ShoppingListEntity> {
        return shoppingLists.map{toShoppingListEntity(it)}
    }

    fun toShoppingLists(shoppingListEntities: List<ShoppingListEntity>) : List<ShoppingList> {
        return shoppingListEntities.map{toShoppingList(it)}
    }

}