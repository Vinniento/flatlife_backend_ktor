package wfp2.flatlife.controllers

import data.models.Task
import data.models.TaskEntity
import data.models.Tasks
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import wfp2.flatlife.requests.AddTaskRequest
import kotlin.math.absoluteValue

class TaskController {

    fun getAllTasksForUser(): List<Task> = transaction {
        TaskEntity.all().map(TaskEntity::toTask)
        //TODO TaskEntity.all().filter { username in it.owners }.map(TaskEntity::toTask)
    }

    fun deleteTask(taskID: Int) =
        transaction {
            TaskEntity[taskID].delete()

        }

    fun deleteAllCompletedTasks() = transaction {
        Tasks.deleteWhere { Tasks.isComplete eq true }.absoluteValue
    }

    fun updateTask(task: Task) = transaction {
        Tasks.update({ Tasks.id eq task.id.toInt() }) {
            it[name] = task.name
            it[isComplete] = task.isComplete
            it[isImportant] = task.isImportant
            it[createdAt] = task.createdAt
        }
        task.id
    }

    fun addTask(task: Task) = transaction {
        if (TaskEntity.findById(task.id.toInt()) == null) {
            TaskEntity.new {
                this.name = task.name
                this.isComplete = task.isComplete
                this.isImportant = task.isImportant
                this.createdAt = task.createdAt
            }.id.value.toLong()
        } else {
            updateTask(task)
        }
    }
}


