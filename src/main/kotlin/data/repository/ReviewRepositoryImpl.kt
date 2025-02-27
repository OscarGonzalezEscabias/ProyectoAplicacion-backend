package data.repository

import data.model.ReviewDao
import data.model.ReviewTable
import data.persistence.suspendTransaction
import domain.model.Review
import domain.repository.ReviewRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class ReviewRepositoryImpl : ReviewRepository {
    override suspend fun getAllReviews(): List<Review> {
        return suspendTransaction {
            ReviewDao.all().map { it.toReview() }
        }
    }

    override suspend fun addReview(review: Review): Review {
        return suspendTransaction {
            ReviewDao.new {
                title = review.title
                description = review.description
                image = review.image
            }.toReview()
        }
    }

    override suspend fun editReview(id: Int, review: Review): Boolean {
        return suspendTransaction {
            val rev = ReviewTable.update({ReviewTable.id eq id}) {
                it[title]= review.title
                it[description] = review.description
                it[image] = review.image
            }
            rev > 0
        }
    }

    override suspend fun deleteReview(id: Int): Boolean {
        return suspendTransaction {
            val deletedReview = ReviewTable.deleteWhere{ReviewTable.id eq id}
            deletedReview == 1
        }
    }
}