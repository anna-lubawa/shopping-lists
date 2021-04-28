package com.annalubawa.shoppinglists.data.mapper

import com.annalubawa.shoppinglists.data.db.entity.ItemEntity
import com.annalubawa.shoppinglists.domain.model.Item
import javax.inject.Inject

class ItemMapper @Inject constructor() {

    fun toItemEntity(item: Item) = ItemEntity(
        item.id,
        item.shoppingListId,
        item.name,
        item.quantity,
        item.bought
    )

    fun toItem(itemEntity: ItemEntity) = Item(
        itemEntity.id,
        itemEntity.shoppingListId,
        itemEntity.name,
        itemEntity.quantity,
        itemEntity.bought
    )

    fun toItemEntities(items: List<Item>) : List<ItemEntity> {
        return items.map { toItemEntity(it) }
    }

    fun toItems(itemEntities: List<ItemEntity>) : List<Item> {
        return itemEntities.map { toItem(it) }
    }

}