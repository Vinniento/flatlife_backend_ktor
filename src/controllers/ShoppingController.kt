package wfp2.flatlife.controllers

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import wfp2.flatlife.data.models.ShoppingItem
import wfp2.flatlife.data.models.ShoppingItemEntity
import wfp2.flatlife.data.models.ShoppingItems
import kotlin.math.absoluteValue

class ShoppingController {

    fun getAllItemsForUser(): List<ShoppingItem> = transaction {
        ShoppingItemEntity.all().map(ShoppingItemEntity::toShoppingItem)
    }

    fun deleteItem(itemID: Int) =
        transaction {
            ShoppingItemEntity[itemID].delete()
            itemID
        }

    fun deleteAllBoughtItems() = transaction {
        ShoppingItems.deleteWhere { ShoppingItems.isBought eq true }.absoluteValue
    }

    fun updateItem(item: ShoppingItem) = transaction {
        ShoppingItems.update({ ShoppingItems.id eq item.id.toInt() }) {
            it[name] = item.name
            it[isBought] = item.isBought
            it[createdAt] = item.createdAt
        }
        item.id
    }

    fun addItem(item: ShoppingItem) = transaction {
        if (ShoppingItemEntity.findById(item.id.toInt()) == null) {
            ShoppingItemEntity.new {
                this.name = item.name
                this.isBought = item.isBought
                this.createdAt = item.createdAt
            }.id.value.toLong()
        } else {
            updateItem(item)
        }
    }
}


