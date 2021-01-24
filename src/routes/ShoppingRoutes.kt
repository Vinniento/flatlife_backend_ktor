package wfp2.flatlife.routes

import com.google.gson.JsonSyntaxException
import data.requests.DeleteItemRequest
import io.ktor.application.*
import io.ktor.http.HttpStatusCode.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import wfp2.flatlife.controllers.ShoppingController
import wfp2.flatlife.data.models.ShoppingItem
import wfp2.flatlife.data.responses.AddItemResponse
import java.util.*

val shoppingController = ShoppingController()
fun Route.shoppingRoutes() {
    route("/addItem") {
        post {
            val item = try {
                call.receive<ShoppingItem>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }

            val itemID = shoppingController.addItem(item)
            call.respond(OK, AddItemResponse(true, itemID))

        }
    }

    //not in use
    route("/updateItem") {
        post {
            val item = try {
                call.receive<ShoppingItem>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }

            val itemID = shoppingController.updateItem(item)
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


    route("/deleteAllBoughtItems") {
        get {
            //todo get userId oder username/email vorher
            val items = shoppingController.deleteAllBoughtItems()
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
            val itemDeleted = shoppingController.deleteItem(itemToBeDeleted.itemID.toInt())
            call.respond(OK, "Deleted = $itemDeleted")

        }
    }
}