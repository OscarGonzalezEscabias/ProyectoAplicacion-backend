package ktor

import domain.repository.UserRepository
import domain.usecase.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ktor.routing.authRoutes
import ktor.routing.reviewRoutes
import java.io.File

fun Application.configureRouting(
    loginUseCase: LoginUseCase,
    registerUseCase: RegisterUseCase,
    getAllReviewsUseCase: GetAllReviewsUseCase,
    addReviewUseCase: AddReviewUseCase,
    editReviewUseCase: EditReviewUseCase,
    deleteReviewUseCase: DeleteReviewUseCase,
    userRepository: UserRepository
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        authRoutes(loginUseCase, registerUseCase)
        reviewRoutes(getAllReviewsUseCase, addReviewUseCase, editReviewUseCase, deleteReviewUseCase, userRepository)

        staticResources("/static", "static")
    }
}
