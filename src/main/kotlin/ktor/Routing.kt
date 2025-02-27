package ktor

import domain.usecase.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ktor.routing.authRoutes
import ktor.routing.reviewRoutes

fun Application.configureRouting(
    loginUseCase: LoginUseCase,
    registerUseCase: RegisterUseCase,
    getAllReviewsUseCase: GetAllReviewsUseCase,
    addReviewUseCase: AddReviewUseCase,
    editReviewUseCase: EditReviewUseCase,
    deleteReviewUseCase: DeleteReviewUseCase
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        authRoutes(loginUseCase, registerUseCase)
        reviewRoutes(getAllReviewsUseCase, addReviewUseCase, editReviewUseCase, deleteReviewUseCase)

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
