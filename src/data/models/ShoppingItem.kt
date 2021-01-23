package wfp2.flatlife.data.models

import org.jetbrains.exposed.dao.IntIdTable

object ShoppingItem : IntIdTable() {
    val itemid = long("item_id").autoIncrement()



}