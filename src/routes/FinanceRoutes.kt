package wfp2.flatlife.routes

import com.google.gson.JsonSyntaxException
import io.ktor.application.*
import io.ktor.http.HttpStatusCode.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import org.jetbrains.exposed.sql.*
import wfp2.flatlife.controllers.FinanceController
import wfp2.flatlife.data.models.FinanceActivity
import wfp2.flatlife.data.responses.AddItemResponse
import data.requests.DeleteItemRequest
import java.util.*

val financeController = FinanceController()
fun Route.financeRoutes() {
    route("/addItem") {
        post {
            val item = try {
                call.receive<FinanceActivity>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }

            val itemID = financeController.addItem(item)
            call.respond(OK, AddItemResponse(true, itemID))

        }
    }

    //not in use
    route("/updateItem") {
        post {
            val item = try {
                call.receive<FinanceActivity>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }

            val itemID = financeController.updateItem(item)
            call.respond(OK, "ID = $itemID")
        }
    }

    route("/getAllItems") {
        get {
            //todo get userId oder username/email vorher
            val items = shoppingController.getAllItemsForUser()
            call.respond(OK, items)
        }
    }

    route("/deleteItem") {
        post {
            val itemToBeDeleted = try {
                call.receive<DeleteItemRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }
            val itemDeleted = financeController.deleteItem(itemToBeDeleted.itemID.toInt())
            call.respond(OK, "Deleted = $itemDeleted")


        }
    }
}


