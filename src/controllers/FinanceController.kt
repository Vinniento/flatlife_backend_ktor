package wfp2.flatlife.controllers

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import wfp2.flatlife.data.models.FinanceActivities
import wfp2.flatlife.data.models.FinanceActivity
import wfp2.flatlife.data.models.FinanceActivityEntity

class FinanceController {

    fun getAllItemsForUser(): List<FinanceActivity> = transaction {
        FinanceActivityEntity.all().map(FinanceActivityEntity::toFinanceActivity)
        //TODO TaskEntity.all().filter { username in it.owners }.map(TaskEntity::toTask)
    }

    fun deleteItem(itemID: Int) =
        transaction {
            FinanceActivityEntity[itemID].delete()
            itemID
        }

    fun updateItem(item: FinanceActivity) = transaction {
        FinanceActivities.update({ FinanceActivities.id eq item.id.toInt() }) {
            it[description] = item.description
            it[categoryName] = item.categoryName
            it[price] = item.price
            it[createdAt] = item.createdAt
        }
        item.id
    }

    fun addItem(item: FinanceActivity) = transaction {
        if (FinanceActivityEntity.findById(item.id.toInt()) == null) {
            FinanceActivityEntity.new {
                this.description = item.description
                this.description = item.categoryName
                this.price = item.price
                this.createdAt = item.createdAt
            }.id.value.toLong()
        } else {
            updateItem(item)
        }
    }
}


