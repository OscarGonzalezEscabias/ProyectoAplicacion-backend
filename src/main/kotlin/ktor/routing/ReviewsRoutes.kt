package ktor.routing

import domain.model.Review
import domain.usecase.AddReviewUseCase
import domain.usecase.GetAllReviewsUseCase
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reviewRoutes(getAllReviewsUseCase: GetAllReviewsUseCase, addReviewUseCase: AddReviewUseCase) {
    route("/reviews") {
        get {
            val reviews = getAllReviewsUseCase()
            call.respond(reviews)
        }

        post("/add") {
            val request = call.receive<Review>()
            val review = addReviewUseCase(request.title, request.description, request.image)
            if (review != null) {
                call.respond(HttpStatusCode.Created, "Review created successfully")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Review created failed")
            }
        }
    }
}