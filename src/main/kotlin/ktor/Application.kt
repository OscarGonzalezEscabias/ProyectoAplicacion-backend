package ktor

import data.repository.UserRepositoryImpl
import data.security.PasswordHash
import domain.repository.UserRepository
import domain.security.PasswordHashInterface
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()

    configureDatabase()

    val userRepository: UserRepository = UserRepositoryImpl()
    val passwordHash: PasswordHashInterface = PasswordHash
    val loginUseCase = LoginUseCase(userRepository, passwordHash)
    val registerUseCase = RegisterUseCase(userRepository, passwordHash)

    configureRouting(loginUseCase, registerUseCase)
}