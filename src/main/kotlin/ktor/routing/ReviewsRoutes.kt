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
import java.io.File
import java.util.*

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
                        val uploadDir = "res/drawable"
                        File(uploadDir).mkdirs()

                        val imageName = if (request.image != null) {
                            val savedImageName = saveImageFromBase64(request.image, uploadDir)
                            "${savedImageName}"
                        } else {
                            null
                        }

                        val review = addReviewUseCase(request.title, request.description, imageName)
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
                        val request = call.receive<Review>()
                        val uploadDir = "res/drawable"

                        id?.let {
                            val existingReview = getAllReviewsUseCase().find { it.id == id.toInt() }
                            if (existingReview != null) {
                                deleteImage(existingReview.image, uploadDir)

                                val imageName = if (request.image != null) {
                                    saveImageFromBase64(request.image, uploadDir)
                                } else {
                                    null
                                }

                                val updatedReview = request.copy(image = imageName)
                                val success = editReviewUseCase(id.toInt(), updatedReview)
                                if (!success) {
                                    call.respond(HttpStatusCode.NotFound, "Review not found")
                                } else {
                                    call.respond(HttpStatusCode.NoContent)
                                }
                            } else {
                                call.respond(HttpStatusCode.NotFound, "Review not found")
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
                    val storedToken = userRepository.getTokenByUsername(username)
                    val providedToken = call.request.headers["Authorization"]?.removePrefix("Bearer ")

                    if (storedToken == providedToken) {
                        val id = call.parameters["id"]
                        val uploadDir = "res/drawable"

                        id?.let {
                            val existingReview = getAllReviewsUseCase().find { it.id == id.toInt() }
                            if (existingReview != null) {
                                deleteImage(existingReview.image, uploadDir)

                                val success = deleteReviewUseCase(id.toInt())
                                if (!success) {
                                    call.respond(HttpStatusCode.NotFound, "Review not found")
                                } else {
                                    call.respond(HttpStatusCode.NoContent)
                                }
                            } else {
                                call.respond(HttpStatusCode.NotFound, "Review not found")
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

fun saveImageFromBase64(base64Image: String, uploadDir: String): String {
    val imageBytes = Base64.getDecoder().decode(base64Image)
    val imageName = "a" + UUID.randomUUID().toString().replace("-", "") + ".jpg"
    val imageFile = File(uploadDir, imageName)
    imageFile.writeBytes(imageBytes)
    return imageName
}

fun deleteImage(imageName: String?, uploadDir: String) {
    if (imageName != null) {
        val imageFile = File(uploadDir, imageName)
        if (imageFile.exists()) {
            imageFile.delete()
        }
    }
}