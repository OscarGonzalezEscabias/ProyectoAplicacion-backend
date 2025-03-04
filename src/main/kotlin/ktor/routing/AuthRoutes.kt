package ktor.routing

import domain.security.JwtConfig
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.authRoutes(loginUseCase: LoginUseCase, registerUseCase: RegisterUseCase) {
    route("/auth") {
        post("/login") {
            val request = call.receive<LoginRequest>()
            val token = loginUseCase(request.usernameOrEmail, request.password)
            if (token != null) {
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }

        post("/register") {
            val request = call.receive<RegisterRequest>()
            val user = registerUseCase(request.username, request.email, request.password)
            if (user != null) {
                call.respond(HttpStatusCode.Created, "User registered successfully")
            } else {
                call.respond(HttpStatusCode.BadRequest, "User registration failed")
            }
        }
    }
}

@Serializable
data class LoginRequest(val usernameOrEmail: String, val password: String)
@Serializable
data class RegisterRequest(val username: String, val email: String, val password: String)