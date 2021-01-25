package wfp2.flatlife.data.models

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable

object Users : IntIdTable() {
    val userName = text("username")
    val password = text("password")
}

//represents an entry in the database
class UserEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, UserEntity>(Users)

    var userName by Users.userName
    var password by Users.password

    fun toUser() = User(id.value.toLong(), userName, password)
}

data class User(
    val id: Long,
    val userName: String,
    val password: String
)



