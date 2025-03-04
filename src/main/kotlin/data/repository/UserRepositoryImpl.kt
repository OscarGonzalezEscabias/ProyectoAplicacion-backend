package data.repository

import data.model.UserDao
import data.model.UserTable
import data.persistence.suspendTransaction
import domain.model.User
import domain.repository.UserRepository
import org.jetbrains.exposed.sql.or

class UserRepositoryImpl : UserRepository {
    override suspend fun createUser(user: User): User {
        return suspendTransaction {
            UserDao.new {
                username = user.username
                email = user.email
                password = user.password
                token = user.token
            }.toUser()
        }
    }

    override suspend fun getUserByUsernameOrEmail(usernameOrEmail: String): User? {
        return suspendTransaction {
            UserDao.find {
                (UserTable.username eq usernameOrEmail) or (UserTable.email eq usernameOrEmail)
            }.firstOrNull()?.toUser()
        }
    }

    override suspend fun updateUserToken(userId: Int, token: String): Boolean {
        return suspendTransaction {
            val user = UserDao.findById(userId)
            user?.let {
                it.token = token
                true
            } ?: false
        }
    }

    override suspend fun getTokenByUsername(username: String): String? {
        return suspendTransaction {
            UserDao.find { UserTable.username eq username }
                .firstOrNull()
                ?.token
        }
    }
}