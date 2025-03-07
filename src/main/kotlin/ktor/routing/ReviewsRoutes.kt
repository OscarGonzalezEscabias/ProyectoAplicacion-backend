package ktor.routing

import domain.model.Review
import domain.repository.UserRepository
import domain.usecase.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reviewRoutes(
    getAllReviewsUseCase: GetAllReviewsUseCase,
    addReviewUseCase: AddReviewUseCase,
    editReviewUseCase: EditReviewUseCase,
    deleteReviewUseCase: DeleteReviewUseCase,
    userRepository: UserRepository
) {
    route("/reviews") {
        authenticate("jwt-auth") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()

                if (username != null) {
                    val storedToken = userRepository.getTokenByUsername(username)
                    val providedToken = call.request.headers["Authorization"]?.removePrefix("Bearer ")

                    if (storedToken == providedToken) {
                        val reviews = getAllReviewsUseCase()
                        call.respond(reviews)
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                }
            }

            post("/add") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()

                if (username != null) {
                    val storedToken = userRepository.getTokenByUsername(username)
                    val providedToken = call.request.headers["Authorization"]?.removePrefix("Bearer ")

                    if (storedToken == providedToken) {
                        val request = call.receive<Review>()
                        val review = addReviewUseCase(request.title, request.description, request.image)
                        if (review != null) {
                            call.respond(HttpStatusCode.Created, "Review created successfully")
                        } else {
                            call.respond(HttpStatusCode.BadRequest, "Review creation failed")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                }
            }

            patch("/edit/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()

                if (username != null) {
                    val storedToken = userRepository.getTokenByUsername(username)
                    val providedToken = call.request.headers["Authorization"]?.removePrefix("Bearer ")

                    if (storedToken == providedToken) {
                        val id = call.parameters["id"]
                        val review = call.receive<Review>()
                        id?.let {
                            val request = editReviewUseCase(id.toInt(), review)
                            if (!request) {
                                call.respond(HttpStatusCode.NotFound, "Review not found")
                            } else {
                                call.respond(HttpStatusCode.NoContent)
                            }
                        } ?: run {
                            call.respond(HttpStatusCode.BadRequest, "ID not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                }
            }

            delete("/del/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()

                if (username != null) {
                    // Verificar que el token coincida con el almacenado en la base de datos
                    val storedToken = userRepository.getTokenByUsername(username)
                    val providedToken = call.request.headers["Authorization"]?.removePrefix("Bearer ")

                    if (storedToken == providedToken) {
                        val id = call.parameters["id"]
                        id?.let {
                            val request = deleteReviewUseCase(id.toInt())
                            if (!request) {
                                call.respond(HttpStatusCode.NotFound, "Review not found")
                            } else {
                                call.respond(HttpStatusCode.NoContent)
                            }
                        } ?: run {
                            call.respond(HttpStatusCode.BadRequest, "ID not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                }
            }
        }
    }
}