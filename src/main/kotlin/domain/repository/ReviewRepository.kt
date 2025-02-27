package domain.repository

import domain.model.Review

interface ReviewRepository {
    suspend fun getAllReviews(): List<Review>
    suspend fun addReview(review: Review): Review?
}