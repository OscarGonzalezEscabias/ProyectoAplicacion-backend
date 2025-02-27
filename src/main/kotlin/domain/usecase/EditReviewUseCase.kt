package domain.usecase

import domain.model.Review
import domain.repository.ReviewRepository

class EditReviewUseCase(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(id: Int, review: Review): Boolean {
        return reviewRepository.editReview(id, review)
    }
}