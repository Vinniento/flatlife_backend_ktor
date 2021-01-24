package wfp2.flatlife.data.models

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable

object FinanceActivities : IntIdTable() {
    val description = text("description")
    val categoryName = text("categoryName")
    val price = text("price")
    val createdAt = long("createdAt")

}

//represents an entry in the database
class FinanceActivityEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, FinanceActivityEntity>(FinanceActivities)

    var description by FinanceActivities.description
    var categoryName by FinanceActivities.categoryName
    var price by FinanceActivities.price
    var createdAt by FinanceActivities.createdAt

    fun toFinanceActivity() = FinanceActivity(id.value.toLong(), description, categoryName, price, createdAt)
}

data class FinanceActivity(
    val id: Long,
    val description: String,
    val categoryName: String,
    val price: String,
    val createdAt: Long,


    )



