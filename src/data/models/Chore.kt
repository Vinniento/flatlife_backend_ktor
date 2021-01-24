package wfp2.flatlife.data.models

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable

object Chores : IntIdTable() {
    val name = text("name")
    val effort = integer("effort")
    val dueBy = text("dueBy")
    val isComplete = bool("isComplete")
    val assignedTo = text("assignedTo")

}

//represents an entry in the database
class ChoreEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, ChoreEntity>(Chores)

    var name by Chores.name
    var effort by Chores.effort
    var dueBy by Chores.dueBy
    var isComplete by Chores.isComplete
    var assignedto by Chores.assignedTo

    fun toChore() = Chore(id.value.toLong(), name, effort, dueBy, isComplete, assignedto)
}

data class Chore(
    val id: Long,
    val name: String,
    val effort: Int,
    val dueBy: String,
    val isComplete: Boolean,
    val assignedTo: String
)



