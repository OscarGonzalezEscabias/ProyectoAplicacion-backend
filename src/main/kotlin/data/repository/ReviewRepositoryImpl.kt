package data.repository

import data.model.ReviewDao
import data.persistence.suspendTransaction
import domain.model.Review
import domain.repository.ReviewRepository

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
}