package domain.usecase

import domain.model.Review
import domain.repository.ReviewRepository

class GetAllReviewsUseCase(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(): List<Review> {
        return reviewRepository.getAllReviews()
    }
}