package ktor

import data.repository.ReviewRepositoryImpl
import data.repository.UserRepositoryImpl
import data.security.PasswordHash
import domain.repository.ReviewRepository
import domain.repository.UserRepository
import domain.security.PasswordHashInterface
import domain.usecase.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

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

    routing {
        get("/images/{imageName}") {
            val imageName = call.parameters["imageName"]
            if (imageName != null) {
                val imageFile = File("res/drawable/$imageName")
                if (imageFile.exists()) {
                    call.respondFile(imageFile)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Image not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Image name is missing")
            }
        }
    }

    configureRouting(loginUseCase, registerUseCase, getAllReviewsUseCase, addReviewUseCase, editReviewUseCase, deleteReviewUseCase, userRepository)
}