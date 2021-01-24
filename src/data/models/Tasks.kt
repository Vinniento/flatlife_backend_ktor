package data.models

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable

object Tasks : IntIdTable() {
    val name = text("name")
    val isComplete = bool("isComplete")
    val isImportant = bool("isImportant")
    val createdAt = long("createdAt")

}

//represents an entry in the database
class TaskEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, TaskEntity>(Tasks)
    var name by Tasks.name
    var isComplete by Tasks.isComplete
    var isImportant by Tasks.isImportant
    var createdAt by Tasks.createdAt

    fun toTask() = Task(id.value.toLong(), name, isComplete, isImportant, createdAt)
}

data class Task(
    val id: Long,
    val name: String,
    val isComplete: Boolean,
    val isImportant: Boolean,
    val createdAt: Long
)



