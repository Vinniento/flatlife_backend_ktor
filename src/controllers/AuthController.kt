package wfp2.flatlife.controllers

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import wfp2.flatlife.data.models.User
import wfp2.flatlife.data.models.UserEntity
import wfp2.flatlife.data.models.Users


suspend fun checkUserNameForPassword(username: String, password: String): Boolean = transaction {
    Users.select { (Users.password eq password) and (Users.userName eq username) }.count() > 0
}

suspend fun register(user: User) = transaction {

    UserEntity.new {
        this.userName = user.userName
        this.password = user.password
    }.id.value.toLong()
}

suspend fun checkIfUsernameExists(userName: String): Boolean = transaction {
    Users.select {
        Users.userName eq userName
    }.count() > 0
}


