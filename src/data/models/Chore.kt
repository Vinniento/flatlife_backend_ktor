package fh.wfp2.flatlife.data.room.entities


import org.jetbrains.exposed.dao.IntIdTable

object Chore : IntIdTable() {
    val choreId = long("choreId").autoIncrement()
    val name = text("name")
    val effort = integer("effort")
    val dueBy = long("due_by")
    val isComplete = bool("is_complete")
    val assignedTo = text("assgigned_to")
}
