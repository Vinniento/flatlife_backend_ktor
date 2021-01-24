package wfp2.flatlife.controllers

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import wfp2.flatlife.data.models.Chore
import wfp2.flatlife.data.models.ChoreEntity
import wfp2.flatlife.data.models.Chores

class ChoreController {

    fun getAllItemsForUser(): List<Chore> = transaction {
        ChoreEntity.all().map(ChoreEntity::toChore)
        //TODO TaskEntity.all().filter { username in it.owners }.map(TaskEntity::toTask)
    }

    fun deleteItem(itemID: Int) =
        transaction {
            ChoreEntity[itemID].delete()
            itemID
        }

    fun updateItem(item: Chore) = transaction {
        Chores.update({ Chores.id eq item.id.toInt() }) {

            it[name] = item.name
            it[effort] = item.effort
            it[dueBy] = item.dueBy
            it[isComplete] = item.isComplete
            it[assignedTo] = item.assignedTo
        }
        item.id
    }

    fun addItem(item: Chore) = transaction {
        if (ChoreEntity.findById(item.id.toInt()) == null) {
            ChoreEntity.new {
                this.name = item.name
                this.effort = item.effort
                this.dueBy = item.dueBy
                this.isComplete = item.isComplete
                this.assignedto = item.assignedTo
            }.id.value.toLong()
        } else {
            updateItem(item)
        }
    }
}


