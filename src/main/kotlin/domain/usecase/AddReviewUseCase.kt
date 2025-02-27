package domain.usecase

import domain.model.Review
import domain.repository.ReviewRepository

class AddReviewUseCase(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(title: String, description: String, image: String): Review? {
        val review = Review(
            id= 0,
            title = title,
            description = description,
            image = image
        )

        return reviewRepository.addReview(review)
    }
}