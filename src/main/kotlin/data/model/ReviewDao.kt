package data.model

import domain.model.Review
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ReviewDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ReviewDao>(ReviewTable)

    var title by ReviewTable.title
    var description by ReviewTable.description
    var image by ReviewTable.image

    fun toReview(): Review {
        return Review(
            id = id.value,
            title = title,
            description = description,
            image = image
        )
    }
}