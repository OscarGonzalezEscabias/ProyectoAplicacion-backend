package ktor.routing

import domain.model.Review
import domain.usecase.AddReviewUseCase
import domain.usecase.DeleteReviewUseCase
import domain.usecase.EditReviewUseCase
import domain.usecase.GetAllReviewsUseCase
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reviewRoutes(getAllReviewsUseCase: GetAllReviewsUseCase, addReviewUseCase: AddReviewUseCase, editReviewUseCase: EditReviewUseCase, deleteReviewUseCase: DeleteReviewUseCase) {
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

        patch("/edit/{id}") {
            val id = call.parameters["id"]
            val review = call.receive<Review>()
            id?.let {
                val request = editReviewUseCase(id.toInt(), review)
                if (!request) {
                    call.respond(HttpStatusCode.NotFound, "Review not found")
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }?:run {
                call.respond(HttpStatusCode.NoContent, "ID not found")
            }
        }

        delete("/del/{id}") {
            val id = call.parameters["id"]
            id?.let {
                val request = deleteReviewUseCase(id.toInt())
                if (!request) {
                    call.respond(HttpStatusCode.NotFound, "Review not found")
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }?:run {
                call.respond(HttpStatusCode.NoContent, "ID not found")
            }
        }
    }
}