package wfp2.flatlife.routes

import io.ktor.auth.*
import io.ktor.routing.*

fun Route.apiRoute() {
    route("/tasks") {
            taskRoutes()

    }
    route("/shopping") {
        shoppingRoutes()
    }
    route("/finance") {
        financeRoutes()
    }
    route("/chores") {
        choreRoutes()
    }

}
fun Route.apiAuthRoutes(){
    route("/authentication") {
        authRoutes()
    }
}
