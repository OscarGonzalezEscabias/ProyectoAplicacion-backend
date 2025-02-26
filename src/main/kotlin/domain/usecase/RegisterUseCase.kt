package domain.usecase

import domain.model.User
import domain.repository.UserRepository
import domain.security.PasswordHashInterface

class RegisterUseCase(
    private val userRepository: UserRepository,
    private val passwordHash: PasswordHashInterface
) {
    suspend operator fun invoke(username: String, email: String, password: String): User? {
        val hashedPassword = passwordHash.hash(password)
        val user = User(
            id = 0,
            username = username,
            email = email,
            password = hashedPassword
        )
        return userRepository.createUser(user)
    }
}