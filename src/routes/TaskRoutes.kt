package wfp2.flatlife.routes

import com.google.gson.JsonSyntaxException
import data.models.Task
import io.ktor.application.*
import io.ktor.http.HttpStatusCode.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import data.models.Tasks
import wfp2.flatlife.controllers.TaskController
import wfp2.flatlife.data.responses.AddTaskResponse
import wfp2.flatlife.requests.AddTaskRequest
import wfp2.flatlife.requests.DeleteTaskRequest
import java.util.*

val taskController = TaskController()
fun Route.taskRoutes() {
    route("/addTask") {
        post {
            val task = try {
                call.receive<Task>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }

            val taskId = taskController.addTask(task)
            call.respond(OK, AddTaskResponse(true, taskId))

        }
    }

    //not in use
    route("/updateTask") {
        post {
            val task = try {
                call.receive<Task>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }

            val taskId = taskController.updateTask(task)
            call.respond(OK, "ID = $taskId")
        }
    }

    route("/getAllTasks") {
        get {
            //todo get userId oder username/email vorher
            val tasks = taskController.getAllTasksForUser()
            call.respond(OK, tasks)
        }
    }


    route("/deleteAllCompletedTasks") {
        get {
            //todo get userId oder username/email vorher
            val tasks = taskController.deleteAllCompletedTasks()
            call.respond(OK, tasks)
        }
    }

    route("/deleteTask") {
        post {
            val taskToBeDeleted = try {
                call.receive<DeleteTaskRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }
            val taskDeleted = taskController.deleteTask(taskToBeDeleted.taskId.toInt())
            call.respond(OK, "Deleted = $taskDeleted")


        }
    }
}


