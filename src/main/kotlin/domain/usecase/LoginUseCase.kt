package domain.usecase

import domain.repository.UserRepository
import domain.security.PasswordHashInterface

class LoginUseCase(
    private val userRepository: UserRepository,
    private val passwordHash: PasswordHashInterface
) {
    suspend operator fun invoke(usernameOrEmail: String, password: String): Boolean {
        val user = userRepository.getUserByUsernameOrEmail(usernameOrEmail)
        return user?.let {
            passwordHash.verify(password, it.password)
        } ?: false
    }
}