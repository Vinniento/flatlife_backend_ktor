package wfp2.flatlife

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.auth.*
import io.ktor.gson.*
import wfp2.flatlife.routes.taskRoutes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import data.models.Tasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    fun initDB() {
        Database.connect(hikari())
        transaction {

            SchemaUtils.create(Tasks)
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
        taskRoutes()
    }
}

private fun hikari(): HikariDataSource {
    val config = HikariConfig("/hikari.properties")
    
  /*  config.driverClassName = "org.h2.Driver"
    config.jdbcUrl = "jdbc:h2:mem:test2"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.password = "sa"
    config.username = "sa"
    */config.validate()
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