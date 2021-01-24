package wfp2.flatlife

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import data.models.Tasks
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import wfp2.flatlife.data.models.Chores
import wfp2.flatlife.data.models.FinanceActivities
import wfp2.flatlife.data.models.ShoppingItems
import wfp2.flatlife.routes.apiRoute

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    fun initDB() {
        Database.connect(hikari())
        transaction {

            SchemaUtils.create(Tasks)
            SchemaUtils.create(Chores)
            SchemaUtils.create(ShoppingItems)
            SchemaUtils.create(FinanceActivities)
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    initDB()

    install(Authentication) {
        //configureAuth()
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Routing) {
        apiRoute()
    }
}

private fun hikari(): HikariDataSource {
    val config = HikariConfig("/hikari.properties")
    config.validate()
    return HikariDataSource(config)
}

private fun Authentication.Configuration.configureAuth() {
    basic {
        realm = "Note Server"
        validate { creds ->
            val email = creds.name
            val password = creds.password
            //if (checkPasswordForEmail(email, password)) {
            UserIdPrincipal(email)
            //} else null
        }
    }
}
