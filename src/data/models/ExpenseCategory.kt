package wfp2.flatlife.data.models

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable

object ExpenseCategories : IntIdTable() {
    val categoryName = text("categoryName")
}

//represents an entry in the database
class ExpenseCategoryEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, ExpenseCategoryEntity>(ExpenseCategories)

    var categoryName by ExpenseCategories.categoryName

    fun toExpenseCategory() = ExpenseCategory(id.value.toLong(), categoryName)
}

data class ExpenseCategory(
    val id: Long,
    val categoryName: String
)



