package wfp2.flatlife.data.models

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable

object ShoppingItems : IntIdTable() {
    val name = text("name")
    val isBought = bool("isBought")
    val createdAt = long("createdAt")

}

//represents an entry in the database
class ShoppingItemEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, ShoppingItemEntity>(ShoppingItems)
    var name by ShoppingItems.name
    var isBought by ShoppingItems.isBought
    var createdAt by ShoppingItems.createdAt

    fun toShoppingItem() = ShoppingItem(id.value.toLong(), name, isBought, createdAt)
}

data class ShoppingItem(
    val id: Long,
    val name: String,
    val isBought: Boolean,
    val createdAt: Long


)



