package domain.repository

import domain.model.User

interface UserRepository {
    suspend fun createUser(user: User): User?
    suspend fun getUserByUsernameOrEmail(usernameOrEmail: String): User?
    suspend fun updateUserToken(userId: Int, token: String): Boolean
    suspend fun getTokenByUsername(username: String): String?
}