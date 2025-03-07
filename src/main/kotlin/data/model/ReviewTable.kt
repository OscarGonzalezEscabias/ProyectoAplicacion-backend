package data.model

import org.jetbrains.exposed.dao.id.IntIdTable

object ReviewTable : IntIdTable("reviews") {
    val title = varchar("title", 100).uniqueIndex()
    val description = varchar("description", 255).uniqueIndex()
    val image = varchar("image", 255).nullable()
}