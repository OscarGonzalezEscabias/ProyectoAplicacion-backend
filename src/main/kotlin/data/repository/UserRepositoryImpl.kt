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
}