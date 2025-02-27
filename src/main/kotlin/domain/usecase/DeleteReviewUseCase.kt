package domain.usecase

import domain.repository.ReviewRepository

class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return reviewRepository.deleteReview(id)
    }
}