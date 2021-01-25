package wfp2.flatlife.routes

import com.google.gson.JsonSyntaxException
import io.ktor.application.*
import io.ktor.http.HttpStatusCode.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import wfp2.flatlife.controllers.checkIfUsernameExists
import wfp2.flatlife.controllers.checkUserNameForPassword
import wfp2.flatlife.controllers.register
import wfp2.flatlife.data.models.User
import wfp2.flatlife.data.responses.SimpleResponse
import java.util.*

fun Route.authRoutes() {
    route("/login") {
        post {
            val user = try {
                call.receive<User>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }

            if (!checkIfUsernameExists(user.userName)) {
                call.respond(OK, SimpleResponse(false, "Username doesn't exist"))
                return@post
            }

            if (checkUserNameForPassword(user.userName, user.password)) {
                call.respond(OK, SimpleResponse(true, "Login successful"))
            } else {
                call.respond(OK, SimpleResponse(false, "Username and password don't match"))
            }
        }
    }

    route("/register") {
        post {
            val user = try {
                call.receive<User>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            } catch (c: JsonSyntaxException) {
                call.respond(BadRequest)
                return@post
            }
            if (!checkIfUsernameExists(user.userName)) {
                val userId = register(user)
                call.respond(OK, SimpleResponse(true, "Registration successful :D"))
            } else
                call.respond(OK, SimpleResponse(false, "Username is already taken, sorry ;-)"))
        }
    }
}





