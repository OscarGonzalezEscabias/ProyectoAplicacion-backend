package ktor

import data.repository.ReviewRepositoryImpl
import data.repository.UserRepositoryImpl
import data.security.PasswordHash
import domain.repository.ReviewRepository
import domain.repository.UserRepository
import domain.security.PasswordHashInterface
import domain.usecase.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureDatabase()

    val userRepository: UserRepository = UserRepositoryImpl()
    val passwordHash: PasswordHashInterface = PasswordHash
    val reviewRepository: ReviewRepository = ReviewRepositoryImpl()
    val getAllReviewsUseCase = GetAllReviewsUseCase(reviewRepository)
    val addReviewUseCase = AddReviewUseCase(reviewRepository)
    val editReviewUseCase = EditReviewUseCase(reviewRepository)
    val deleteReviewUseCase = DeleteReviewUseCase(reviewRepository)
    val loginUseCase = LoginUseCase(userRepository, passwordHash)
    val registerUseCase = RegisterUseCase(userRepository, passwordHash)

    configureRouting(loginUseCase, registerUseCase, getAllReviewsUseCase, addReviewUseCase, editReviewUseCase, deleteReviewUseCase, userRepository)
}