package domain.usecase

import domain.security.JwtConfig
import domain.repository.UserRepository
import domain.security.PasswordHashInterface

class LoginUseCase(
    private val userRepository: UserRepository,
    private val passwordHash: PasswordHashInterface
) {
    suspend operator fun invoke(usernameOrEmail: String, password: String): String? {
        val user = userRepository.getUserByUsernameOrEmail(usernameOrEmail)
        return user?.let {
            if (passwordHash.verify(password, it.password)) {
                val token = JwtConfig.generateToken(it.username)
                userRepository.updateUserToken(it.id, token)
                token
            } else null
        }
    }
}